package org.aas.ga.algo;

import org.aas.ga.chromo.Chromosome;
import org.aas.ga.trans.Transformer;
import org.aas.ga.util.GeneticUtil;

import java.util.*;

/**
 * Created by Adam on 6/12/2016.
 */
public abstract class AbstractGeneticAlgorithm implements GeneticAlgorithm {

    private final double REFRESH_RATE = .8;
    private final int NO_TARGET_SIZE_DEFINED =-1;
    private final Map<Integer,Chromosome> overallFitnessMap = new LinkedHashMap<>();

    private List<Chromosome> population;
    private Transformer transformer;
    private double absFitWeight;
    private double relFitWeight;
    private double p_mutate;
    private double p_crossover;
    private double minRunFit;
    private double maxRunFit;
    private int origPopSize;
    private int numGeneration;
    private Integer quitAfterGen;
    private Integer refreshAfter;
    private boolean doElitism;
    private boolean inverseFitnessRanking;
    private Chromosome overall_fittest;


    public AbstractGeneticAlgorithm(){
    }
    public AbstractGeneticAlgorithm(List<Chromosome> pop){
        this(pop,.5,.5,.5,.5,50000,false,false,2500,1000);

    }
    public AbstractGeneticAlgorithm(List<Chromosome>pop,double absW, double relW){
        this(pop,absW,relW,.5,.5,50000,false,false,2500,1000);
    }

    public AbstractGeneticAlgorithm(List<Chromosome>pop, double absW, double relW, double p_mutate, double p_crossover,int gen){
        this(pop,absW,relW,p_mutate,p_crossover,gen,false,false,2500,1000);
    }

    public AbstractGeneticAlgorithm(List<Chromosome> pop,double absW, double relW,double p_mutate,double p_crossover,int gen, boolean elitist,boolean inverseFitRanking, int quit_after, int refresh_after){
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
        this.minRunFit = Double.MAX_VALUE;
        this.maxRunFit = Double.MIN_VALUE;
    }


    @Override
    public Chromosome getWeakest() {
        return inverseFitnessRanking ? Collections.max(population) : Collections.min(population);
    }

    @Override
    public Chromosome getFittest() {
        return inverseFitnessRanking ? Collections.min(population) : Collections.max(population);
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
        List<Chromosome> survivors = new ArrayList();
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


        for(Chromosome c : population)
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
    public List<Chromosome> reproduce(List<Chromosome> survivors, double pCrossover, int targetSize){
        Random rand = new Random();
        if(targetSize == NO_TARGET_SIZE_DEFINED)
            targetSize = this.origPopSize;


        int numSurvivors = survivors.size();
        List<Chromosome> offspring = new ArrayList<>();
        Map<Chromosome,Double> survivorCDF = GeneticUtil.computeFitnessCDF(survivors, this);
        while(survivors.size()+offspring.size() < targetSize)
        {
            Chromosome c1 = GeneticUtil.weightedChoice(survivorCDF).copy();
            if(rand.nextDouble()<pCrossover)
            {
                Chromosome c2 = survivors.get(rand.nextInt(numSurvivors));
                int crosspoint = rand.nextInt(c2.length())+1;
                Chromosome baby =c1.crossover(c2,crosspoint);
                offspring.add(baby);
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
    public void mutate(double p) {
        for(Chromosome c : population)
            c.mutate(p);
    }

    @Override
    public void refresh() {
        for(Chromosome c : population)
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
        Chromosome generationFittest;

        for(int gen = 1; gen <= numGeneration; gen ++)
        {
            population = reproduce(compete(),p_crossover);
            mutate(p_mutate);
            calculateFitness();

            if(gen == 1)
                overall_fittest = getFittest().copy();

            generationFittest  = getFittest();
            System.out.println("Generation "+gen+" Fittest: "+generationFittest + " Fitness : "+ generationFittest.getFitness());
            if((generationFittest.getFitness() > overall_fittest.getFitness() && !this.inverseFitnessRanking) ||
                    (generationFittest.getFitness()<overall_fittest.getFitness() && this.inverseFitnessRanking))
            {
                overall_fittest = generationFittest;
                System.out.println("new overall fittest found on gen "+gen +": "+overall_fittest);
                overallFitnessMap.put(gen,generationFittest);
                generationsSinceUpset = 0;
            }
            else
            {
                generationsSinceUpset++;
            }
            if(quitAfterGen != null && generationsSinceUpset > quitAfterGen){
                System.out.println("Quitting on gen "+gen + " "+generationsSinceUpset+" number of generations since disturbance");
                break;
            }

            if(refreshAfter != null && generationsSinceUpset> refreshAfter){
                System.out.println("Refreshing on gen "+gen);
                refresh();
                generationsSinceUpset = 0;
            }
            if(shouldTerminate())
                break;

        }
        long end = System.currentTimeMillis();
        double seconds = (double) (end - start)*0.001;
        System.out.println("Run took : "+seconds+" seconds");
    }


    @Override
    public List<Chromosome> getPopulation() {
        return population;
    }
    @Override
    public void setPopulation(List<Chromosome> population) {
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

    public double getMinRunFit() {
        return minRunFit;
    }

    public void setMinRunFit(double minRunFit) {
        this.minRunFit = minRunFit;
    }

    public double getMaxRunFit() {
        return maxRunFit;
    }

    public void setMaxRunFit(double maxRunFit) {
        this.maxRunFit = maxRunFit;
    }

    public int getOrigPopSize() {
        return origPopSize;
    }

    public void setOrigPopSize(int origPopSize) {
        this.origPopSize = origPopSize;
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
