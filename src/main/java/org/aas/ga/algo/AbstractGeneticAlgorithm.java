package org.aas.ga.algo;

import org.aas.ga.chromo.AbstractCollectionChromosome;
import org.aas.ga.chromo.Chromosome;
import org.aas.ga.trans.Transformer;

import java.util.*;

/**
 * Created by Adam on 6/12/2016.
 */
public abstract class AbstractGeneticAlgorithm<T extends AbstractCollectionChromosome> implements GeneticAlgorithm<T> {

    private final double REFRESH_RATE = 1;
    private final int NO_TARGET_SIZE_DEFINED =-1;
    private final Map<Integer,T> overallFitnessMap = new LinkedHashMap<>();

    private List<T> population;
    private Transformer transformer;
    private double absFitWeight;
    private double relFitWeight;
    private double p_mutate;
    private double p_crossover;
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

    private T overall_fittest;


    public AbstractGeneticAlgorithm(){
    }
    public AbstractGeneticAlgorithm(List<T> pop){
        this(pop,.5,.5);

    }
    public AbstractGeneticAlgorithm(List<T>pop,double absW, double relW){
        this(pop,absW,relW,.5,.5,50000);
    }

    public AbstractGeneticAlgorithm(List<T>pop, double absW, double relW, double p_mutate, double p_crossover,int gen){
        this(pop,absW,relW,p_mutate,p_crossover,gen,false,false,2500,1000);
    }

    public AbstractGeneticAlgorithm(List<T> pop,double absW, double relW,double p_mutate,double p_crossover,int gen, boolean elitist,boolean inverseFitRanking, int quit_after, int refresh_after){
        if (relW + absW != 1) throw new AssertionError("Absolute and relative weighting Factors must add to 1");
        this.population = pop;
        this.origPopSize = pop.size();
        this.absFitWeight = absW;
        this.relFitWeight = relW;
        this.p_mutate = p_mutate;
        this.p_crossover = p_crossover;
        this.numGeneration = gen;
        this.doElitism = elitist;
        this.quitAfterGen = quit_after;
        this.refreshAfter = refresh_after;
        this.inverseFitnessRanking = inverseFitRanking;
        printGenerationInfo =false;
        maxNumRefreshes=8;
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
    public T getWeakest() {
        return inverseFitnessRanking ? (T)Collections.max(population) : (T) Collections.min(population);
    }

    @Override
    public T getFittest() {
        return inverseFitnessRanking ? (T) Collections.min(population) : (T) Collections.max(population);
    }

    @Override
    public void sort(List<T> chromosomes)
    {
        Collections.sort(chromosomes);
        if(inverseFitnessRanking)
            Collections.reverse(chromosomes);
    }

    @Override
    public List<T> compete()
    {
        Random rand = new Random();
        List<T> survivors = new ArrayList();
        if(doElitism)
        {
            T elite = getFittest();
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


        for(T c : population)
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
        population.stream().forEach(this::evaluateFitness);
    }

    @Override
    public List<T> reproduce(List<T> survivors, double pCrossover, int targetSize){
        Random rand = new Random();
        if(targetSize == NO_TARGET_SIZE_DEFINED)
            targetSize = this.origPopSize;


        int numSurvivors = survivors.size();
        List<T> offspring = new ArrayList<>();
        Map<T,Double> survivorCDF = computeFitnessCDF(survivors);
        while(survivors.size()+offspring.size() < targetSize)
        {
            T c1 = (T) weightedChoice(survivorCDF).copy();
            if(rand.nextDouble()<pCrossover)
            {
                T c2 = survivors.get(rand.nextInt(numSurvivors));
                int crosspoint = rand.nextInt(c2.length());
                c1.setGenes(c1.crossover(c2,crosspoint));
                offspring.add(c1);
            }
        }
        survivors.addAll(offspring);
        return survivors;
    }

    @Override
    public List<T> reproduce(List<T> survivors, double pCrossover)
    {
        return reproduce(survivors,pCrossover,NO_TARGET_SIZE_DEFINED);
    }

    @Override
    public void mutate(double p) {
        for(T c : population)
            c.mutate(p);
    }

    @Override
    public void refresh() {
        for(T c : population)
            c.mutate(REFRESH_RATE);
    }


    @Override
    public boolean shouldTerminate() {
        return false;
    }

    @Override
    public void run()
    {
        overall_fittest = null;
        long start = System.currentTimeMillis();
        int generationsSinceUpset = 0;
        int refreshCount =  0;
        T generationFittest;

        for(int gen = 1; gen <= numGeneration; gen ++)
        {
            population = reproduce(compete(),p_crossover);
            mutate(p_mutate);
            calculateFitness();

            if(gen == 1)
                overall_fittest = (T) getFittest().copy();

            generationFittest  = (T) getFittest().copy();

            if((generationFittest.getFitness() > overall_fittest.getFitness() && !this.inverseFitnessRanking) ||
                    (generationFittest.getFitness()<overall_fittest.getFitness() && this.inverseFitnessRanking))
            {
                overall_fittest = (T)generationFittest.copy();
                System.out.println("New overall fittest found on gen "+gen +": "+overall_fittest + " Fitness: "+overall_fittest.getFitness());
                overallFitnessMap.put(gen,generationFittest);
                generationsSinceUpset = 0;
            }
            else
            {
                if(printGenerationInfo)
                    System.out.println("Generation "+gen+" Fittest: "+generationFittest + " Fitness : "+ generationFittest.getFitness());
                generationsSinceUpset++;
            }
            if(quitAfterGen != null && generationsSinceUpset > quitAfterGen){
                System.out.println("Quitting on gen "+gen + " "+generationsSinceUpset+" number of generations since disturbance");
                break;
            }

            if(refreshAfter != null && generationsSinceUpset> refreshAfter)
            {
                System.out.println("Refreshing on gen "+gen);
                refresh();
                refreshCount++;
                generationsSinceUpset = 0;
            }
            if(shouldTerminate() || refreshCount ==maxNumRefreshes)
                break;

        }
        long end = System.currentTimeMillis();
        double seconds = (double) (end - start)*0.001;
        System.out.println("Run took : "+seconds+" seconds");
    }

    public Map<T,Double> computeFitnessCDF(List<T> survivors)
    {

        Double min = getWeakest().getFitness();
        Double range = Math.abs(getFittest().getFitness() - min) ;
        if(range == 0)
        {
            Map<T,Double> defaultCdf = new LinkedHashMap<>();
            Random rand = new Random();
            for(T chromo : survivors)
            {
                defaultCdf.put(chromo,rand.nextDouble());
            }
            return defaultCdf;
        }
        else
        {
            Map<T,Double> cdf = new LinkedHashMap<>();
            for(T c : survivors)
            {
                double fit = c.getFitness();
                cdf.put(c,((Math.abs(fit-min))/range));
            }
            return cdf;
        }
    }

    public  T weightedChoice(Map<T,Double> survivorCdfMap)
    {
        Random rand = new Random();
        for(T chromo : survivorCdfMap.keySet()){
            if(rand.nextDouble() < survivorCdfMap.get(chromo))
                return chromo;
        }

        return choice(survivorCdfMap.keySet(),rand);
    }

    public T choice(Collection<? extends T> coll, Random rand) {

        if (coll.isEmpty())
            return null;

        int index = rand.nextInt(coll.size());
        if (coll instanceof List)
            return ((List<? extends T>) coll).get(index);
        else
        {
            Iterator<? extends T> iter = coll.iterator();
            for (int i = 0; i < index; i++)
            {
                iter.next();
            }
            return iter.next();
        }
    }

    @Override
    public List<T> getPopulation() {
        return population;
    }
    @Override
    public void setPopulation(List<T> population) {
        this.population = population;
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

    public double getP_mutate() {
        return p_mutate;
    }

    public void setP_mutate(double p_mutate) {
        this.p_mutate = p_mutate;
    }

    public double getP_crossover() {
        return p_crossover;
    }

    public void setP_crossover(double p_crossover) {
        this.p_crossover = p_crossover;
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

    public Chromosome getOverall_fittest() {
        return overall_fittest;
    }

    public Map<Integer, T> getOverallFitnessMap() {
        return overallFitnessMap;
    }
    public Transformer getTransformer() {
        return transformer;
    }

    public void setTransformer(Transformer transformer) {
        this.transformer = transformer;
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
