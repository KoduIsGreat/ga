package org.aas.ga.algo;

import org.aas.ga.chromo.Chromosome;
import org.aas.ga.trans.Transformer;
import org.aas.ga.util.GeneticUtil;

import java.util.*;

/**
 * Created by Adam on 6/12/2016.
 */
public abstract class AbstractGeneticAlgorithm implements GeneticAlgorithm {

    private List<Chromosome> population;
    private Transformer transformer;
    private double absFitWeight;
    private double relFitWeight;
    private double p_mutate;
    private double p_crossover;
    private double minRunFit;
    private double maxRunFit;
    private int origPopSize;
    private int num_generations;
    private Integer gen_to_quit_after ;
    private Integer gen_to_refresh_after;
    private boolean do_elitism;
    private Map<Integer,Chromosome> new_overall_fittness_map = new LinkedHashMap<>();

    private boolean inverseFitnessRanking;

    private Chromosome overall_fittest;
    private final double REFRESH_RATE = .8;
    private final int NO_TARGET_SIZE_DEFINED =-1;

    public AbstractGeneticAlgorithm(){
    }
    public AbstractGeneticAlgorithm(List<Chromosome> pop,double absW, double relW,double p_mutate,double p_crossover,int gen, boolean elitist, int quit_after, int refresh_after){
        this.population = pop;
        this.origPopSize = pop.size();
        this.absFitWeight = absW;
        this.relFitWeight = relW;
        this.p_mutate = p_mutate;
        this.p_crossover = p_crossover;
        this.num_generations = gen;
        this.do_elitism = elitist;
        this.gen_to_quit_after = quit_after;
        this.gen_to_refresh_after = refresh_after;
        this.inverseFitnessRanking = false;
        this.minRunFit = Double.MAX_VALUE;
        this.maxRunFit = Double.MIN_VALUE;
    }
    public AbstractGeneticAlgorithm(List<Chromosome>pop,int gen, double absW, double relW, double p_mutate, double p_crossover){
        this.population = pop;
        this.origPopSize = pop.size();
        this.absFitWeight = absW;
        this.relFitWeight = relW;
        this.p_mutate = p_mutate;
        this.p_crossover = p_crossover;
        this.do_elitism = false;
        this.gen_to_quit_after= null;
        this.gen_to_refresh_after =500;
        this.num_generations = gen;
        this.inverseFitnessRanking = false;
        this.minRunFit = Double.MAX_VALUE;
        this.maxRunFit = Double.MIN_VALUE;
    }

    @Override
    public Chromosome getWeakest() {
       //  return population.get(population.size()-1);
        return inverseFitnessRanking ? Collections.max(population) : Collections.min(population);
    }

    @Override
    public Chromosome getFittest() {
      //  return population.get(0);
        return inverseFitnessRanking ? Collections.min(population) : Collections.max(population);
    }

    @Override
    public List<Chromosome> sort(List<Chromosome> chromosomes)
    {
        Collections.sort(chromosomes);
        if(inverseFitnessRanking)
            Collections.reverse(chromosomes);

        return chromosomes;
    }

    @Override
    public List<Chromosome> compete()
    {
        calculatePopulationFitness();
        Random rand = new Random();
        double minFit = getWeakest().getFitness();
        double maxFit = getFittest().getFitness();

        if(inverseFitnessRanking ? minFit>this.minRunFit : minFit<this.minRunFit)
            this.minRunFit=minFit;
        if(inverseFitnessRanking ? maxFit<this.maxRunFit : maxFit>this.maxRunFit)
            this.maxRunFit=maxFit;

        double overallFitnessRange = Math.abs(this.maxRunFit -this.minRunFit);
        double relativeFitnessRange = Math.abs(maxFit - minFit);
        List<Chromosome> survivors = new ArrayList();

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
    public void calculatePopulationFitness()
    {
        population.stream().filter(c -> Double.isNaN(c.getFitness())).forEach(this::evaluateFitness);
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
                int crosspoint = rand.nextInt(c2.length());
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

        for(int gen = 1; gen <=num_generations; gen ++)
        {
            List<Chromosome> survivors = compete();
            population = reproduce(survivors,p_crossover);
            mutate(p_mutate);

            if(gen == 1)
                overall_fittest = getFittest();

            generationFittest  = getFittest();
            System.out.println("Generation "+gen+" Fittest: "+generationFittest + " Fitness : "+ generationFittest.getFitness());
            if((generationFittest.getFitness() > overall_fittest.getFitness() && !this.inverseFitnessRanking) ||
                    (generationFittest.getFitness()<overall_fittest.getFitness() && this.inverseFitnessRanking))
            {
                overall_fittest = generationFittest;
                System.out.println("new overall fittest found on gen "+gen +": "+overall_fittest);
                new_overall_fittness_map.put(gen,generationFittest);
                generationsSinceUpset = 0;
            }
            else
            {
                generationsSinceUpset++;
            }
            if(gen_to_quit_after != null && generationsSinceUpset >gen_to_quit_after ){
                System.out.println("Quitting on gen "+gen + " "+generationsSinceUpset+" number of generations since disturbance");
                break;
            }

            if(gen_to_refresh_after != null && generationsSinceUpset> gen_to_refresh_after){
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
