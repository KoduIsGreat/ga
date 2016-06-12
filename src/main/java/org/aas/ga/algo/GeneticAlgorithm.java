package org.aas.ga.algo;

import org.aas.ga.chromo.Chromosome;

import java.util.List;

/**
 * Created by Adam on 6/12/2016.
 */
public interface GeneticAlgorithm extends Runnable
{

    public Chromosome getWeakest();
    public Chromosome getFittest();
    public List<Chromosome> sort(List<Chromosome> chromosomes);
    public List<Chromosome> compete();
    public List<Chromosome> reproduce(List<Chromosome> survivors, double pCrossover, int targetSize);
    public List<Chromosome> reproduce(List<Chromosome> survivors, double pCrossover);
    public List<Chromosome> getPopulation();
    public void calculatePopulationFitness();
    public void evaluateFitness(Chromosome chromo);
    public void mutate(double p);
    public void refresh();
    public void setPopulation(List<Chromosome> population);
    public boolean shouldTerminate();


}
