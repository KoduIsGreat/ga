package org.aas.ga.algo;

import org.aas.ga.chromo.Chromosome;
import org.aas.ga.err.WeightsException;
import org.aas.ga.sim.BaseMutator;
import org.aas.ga.sim.Mutator;
import org.aas.ga.trans.Transformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Created by Adam on 6/12/2016.
 */
public abstract class AbstractGeneticAlgorithm implements GeneticAlgorithm {

    Logger LOG = LoggerFactory.getLogger(AbstractGeneticAlgorithm.class);
    private final double REFRESH_RATE = 1;
    private final int NO_TARGET_SIZE_DEFINED =-1;
    private final Map<Integer,Chromosome> overallFitnessMap = new LinkedHashMap<>();
    private List<Chromosome> population;
    private Transformer transformer;
    private double absFitWeight;
    private double relFitWeight;
    private double pMutate;
    private double pCrossover;
    private double minRunFit;
    private double maxRunFit;
    private int origPopSize;
    private int numGeneration;
    private int maxNumRefreshes;
    private Integer quitAfterGen;
    private Integer refreshAfter;
    private boolean doElitism;
    private boolean inverseFitnessRanking;
    private boolean printGenerationInfo;
    private Chromosome overall_fittest;

    private Mutator mutator;

    public AbstractGeneticAlgorithm() {
        this(null,null);
    }

    public AbstractGeneticAlgorithm(List<Chromosome> pop,BaseMutator mutator) {
        this(pop,mutator,.5,.5);

    }
    public AbstractGeneticAlgorithm(List<Chromosome>pop, BaseMutator mutator, double absW, double relW){
        this(pop,absW,relW,mutator,.5,50000);
    }

    public AbstractGeneticAlgorithm(List<Chromosome>pop, double absW, double relW, Mutator mutator, double pCrossover, int gen) {
        this(pop,absW,relW, mutator, pCrossover,gen,false,false,2500,1000);
    }

    public AbstractGeneticAlgorithm(List<Chromosome> pop, double absW, double relW, Mutator mutator, double pCrossover, int gen, boolean elitist, boolean inverseFitRanking, int quit_after, int refresh_after) {

        this.population = pop;
        this.origPopSize = pop != null ? pop.size() : 0;
        this.absFitWeight = absW;
        this.relFitWeight = relW;
        this.mutator = mutator;
        this.pCrossover = pCrossover;
        this.numGeneration = gen;
        this.doElitism = elitist;
        this.quitAfterGen = quit_after;
        this.refreshAfter = refresh_after;
        this.inverseFitnessRanking = inverseFitRanking;
        printGenerationInfo =false;
        maxNumRefreshes=100;
        if(inverseFitnessRanking)
        {
            this.minRunFit = Double.MIN_VALUE;
            this.maxRunFit = Double.MAX_VALUE;
        }
        else
        {
            this.minRunFit = Double.MAX_VALUE;
            this.maxRunFit = Double.MIN_VALUE;
        }
    }
    @Override
    public abstract void evaluateFitness(Chromosome chromo);

    @Override
    public Chromosome getWeakest() {
        return inverseFitnessRanking ? Collections.max(population) :  Collections.min(population);
    }

    @Override
    public Chromosome getFittest() {
        return inverseFitnessRanking ?  Collections.min(population) :  Collections.max(population);
    }

    @Override
    public void sort(List<Chromosome> chromosomes)
    {
        Collections.sort(chromosomes);
        if(inverseFitnessRanking)
            Collections.reverse(chromosomes);
    }

    @Override
    public List<Chromosome> compete()
    {
        Random rand = new Random();
        List<Chromosome> survivors = new ArrayList<>();
        if(doElitism)
        {
            Chromosome elite = getFittest();
            population.remove(elite);
            survivors.add(elite);
        }

        double minFit = getWeakest().getFitness();
        double maxFit = getFittest().getFitness();

        if(inverseFitnessRanking ? minFit>this.minRunFit : minFit<this.minRunFit)
            this.minRunFit=minFit;
        if(inverseFitnessRanking ? maxFit<this.maxRunFit : maxFit>this.maxRunFit)
            this.maxRunFit=maxFit;

        double overallFitnessRange = Math.abs(this.maxRunFit -this.minRunFit);
        double relativeFitnessRange = Math.abs(maxFit - minFit);


        for(Chromosome c : population)//n
        {
            double fitness = c.getFitness();
            double p_abs = (overallFitnessRange != 0) ? ((Math.abs(fitness - this.minRunFit))/overallFitnessRange) : 1;
            double p_rel = (relativeFitnessRange != 0)? ((Math.abs(fitness - minFit))/relativeFitnessRange) : 1;

            double p_survival = p_abs*this.absFitWeight + p_rel*this.relFitWeight;

            if(rand.nextDouble() < p_survival)
                survivors.add(c);
        }
        if(survivors.isEmpty())
            return population;

        return survivors;
    }

    @Override
    public void calculateFitness()
    {
        population.forEach(this::evaluateFitness);
    }

    @Override
    public List<Chromosome> reproduce(List<Chromosome> survivors, double pCrossover, int targetSize)
    {
        Random rand = new Random();
        if(targetSize == NO_TARGET_SIZE_DEFINED)
            targetSize = this.origPopSize;


        int numSurvivors = survivors.size();
        List<Chromosome> offspring = new ArrayList<>();
        Map<Chromosome,Double> survivorCDF = computeFitnessCDF(survivors);
        while(survivors.size()+offspring.size() < targetSize)//n'
        {
            Chromosome c1 = weightedChoice(survivorCDF).copy();
            if(rand.nextDouble()<pCrossover)
            {
                Chromosome c2 = survivors.get(rand.nextInt(numSurvivors));
                int crosspoint = rand.nextInt(c2.length());
                c1.setGenes(c1.crossover(c2,crosspoint));
                offspring.add(c1);
            }
        }
        survivors.addAll(offspring);
        return survivors;
    }

    @Override
    public List<Chromosome> reproduce(List<Chromosome> survivors, double pCrossover)
    {
        return reproduce(survivors,pCrossover,NO_TARGET_SIZE_DEFINED);
    }

    @Override
    public void mutate() {
        for(Chromosome c : population)
            mutator.mutate(c);
    }

    @Override
    public void refresh() {
        for(Chromosome c : population)
            mutator.refresh(c,REFRESH_RATE);
    }


    @Override
    public boolean shouldTerminate() {
        return false;
    }

    @Override
    public void run() throws WeightsException
    {
        if (this.relFitWeight + this.absFitWeight != 1)
            throw new WeightsException("Relative Weight : "+relFitWeight+" Absolute Weight "+absFitWeight +" They must equal 1");

        calculateFitness();
        overall_fittest = null;
        long start = System.currentTimeMillis();
        int generationsSinceUpset = 0;
        int refreshCount =  0;
        Chromosome generationFittest;

        for(int gen = 1; gen <= numGeneration; gen ++)
        {
            population = reproduce(compete(), pCrossover);
            mutate();
            calculateFitness();

            if(gen == 1)
                overall_fittest =  getFittest().copy();

            generationFittest  =  getFittest().copy();

            if((generationFittest.getFitness() > overall_fittest.getFitness() && !this.inverseFitnessRanking) ||
                    (generationFittest.getFitness()<overall_fittest.getFitness() && this.inverseFitnessRanking))
            {
                overall_fittest = generationFittest.copy();
                LOG.info("New overall fittest found on gen "+gen +": "+overall_fittest + " Fitness: "+overall_fittest.getFitness());
                overallFitnessMap.put(gen,generationFittest);
                generationsSinceUpset = 0;
                refreshCount = 0;
            }
            else
            {
                if(printGenerationInfo)
                    LOG.info("Generation "+gen+" Fittest: "+generationFittest + " Fitness : "+ generationFittest.getFitness());
                generationsSinceUpset++;
            }
            if(quitAfterGen != null && generationsSinceUpset > quitAfterGen){
                LOG.info("Quitting on gen "+gen + " "+generationsSinceUpset+" number of generations since disturbance");
                break;
            }

            if(refreshAfter != null && generationsSinceUpset> refreshAfter)
            {
                LOG.info("Refreshing on gen "+gen);
                refresh();
                refreshCount++;
                generationsSinceUpset = 0;
            }
            if(shouldTerminate() || refreshCount ==maxNumRefreshes)
                break;

        }
        long end = System.currentTimeMillis();
        double seconds = (double) (end - start)*0.001;
        LOG.info("Run took : "+seconds+" seconds");
    }

    private Map<Chromosome,Double> computeFitnessCDF(List<Chromosome> survivors)
    {

        Double min = getWeakest().getFitness();
        Double range = Math.abs(getFittest().getFitness() - min) ;

        if(range == 0)
        {
            Map<Chromosome,Double> defaultCdf = new LinkedHashMap<>();
            Random rand = new Random();
            for(Chromosome chromo : survivors)//n'
            {
                defaultCdf.put(chromo,rand.nextDouble());
            }
            return defaultCdf;
        }
        else
        {
            Map<Chromosome,Double> cdf = new LinkedHashMap<>();
            for(Chromosome c : survivors)
            {
                double fit = c.getFitness();
                cdf.put(c,((Math.abs(fit-min))/range));
            }
            return cdf;
        }
    }

    private  Chromosome weightedChoice(Map<Chromosome,Double> survivorCdfMap)
    {
        Random rand = new Random();
        for(Chromosome chromo : survivorCdfMap.keySet())
        {//n'
            if(rand.nextDouble() < survivorCdfMap.get(chromo))
                return chromo;
        }

        return choice(survivorCdfMap.keySet(),rand);
    }

    private Chromosome choice(Collection<? extends Chromosome> coll, Random rand) {

        if (coll.isEmpty())
            return null;

        int index = rand.nextInt(coll.size());
        if (coll instanceof List)
            return ((List<? extends Chromosome>) coll).get(index);//
        else
        {
            Iterator<? extends Chromosome> iter = coll.iterator();
            for (int i = 0; i < index; i++)
            {
                iter.next();
            }
            return iter.next();
        }
    }

    @Override
    public List<Chromosome> getPopulation() {
        return population;
    }
    @Override
    public void setPopulation(List<Chromosome> population) {
        this.population = population;
        this.origPopSize = population.size();
    }

    public boolean isDoElitism() {
        return doElitism;
    }

    public void setDoElitism(boolean doElitism) {
        this.doElitism = doElitism;
    }

    public double getAbsFitWeight() {
        return absFitWeight;
    }

    public void setAbsFitWeight(double absFitWeight) {
        this.absFitWeight = absFitWeight;
    }

    public double getRelFitWeight() {
        return relFitWeight;
    }

    public void setRelFitWeight(double relFitWeight) {
        this.relFitWeight = relFitWeight;
    }

    public double getpMutate() {
        return pMutate;
    }

    public void setpMutate(double pMutate) {
        this.pMutate = pMutate;
    }

    public double getpCrossover() {
        return pCrossover;
    }

    public void setpCrossover(double pCrossover) {
        this.pCrossover = pCrossover;
    }


    public int getNumGeneration() {
        return numGeneration;
    }

    public void setNumGeneration(int numGeneration) {
        this.numGeneration = numGeneration;
    }

    public Integer getQuitAfterGen() {
        return quitAfterGen;
    }

    public void setQuitAfterGen(Integer quitAfterGen) {
        this.quitAfterGen = quitAfterGen;
    }

    public Integer getRefreshAfter() {
        return refreshAfter;
    }

    public void setRefreshAfter(Integer refreshAfter) {
        this.refreshAfter = refreshAfter;
    }
    @Override
    public Chromosome getOverall_fittest() {
        return overall_fittest;
    }

    @Override
    public Map<Integer, Chromosome> getOverallFitnessMap() {
        return overallFitnessMap;
    }

    public Transformer getTransformer() {
        return transformer;
    }

    public void setTransformer(Transformer transformer) {
        this.transformer = transformer;
    }

    public Mutator getMutator() {
        return mutator;
    }

    @Override
    public void setMutator(Mutator mutator) {
        this.mutator = mutator;
    }

    public boolean isInverseFitnessRanking() {
        return inverseFitnessRanking;
    }

    public void setInverseFitnessRanking(boolean inverseFitnessRanking) {
        this.inverseFitnessRanking = inverseFitnessRanking;
        if(inverseFitnessRanking)
        {
            this.minRunFit = Double.MIN_VALUE;
            this.maxRunFit = Double.MAX_VALUE;
        }
        else
        {
            this.minRunFit = Double.MAX_VALUE;
            this.maxRunFit = Double.MIN_VALUE;
        }
    }

}
