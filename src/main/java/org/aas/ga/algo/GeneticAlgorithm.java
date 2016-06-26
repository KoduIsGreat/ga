package org.aas.ga.algo;

import org.aas.ga.chromo.Chromosome;

import java.util.List;

/**
 * Created by Adam on 6/12/2016.
 */
public interface GeneticAlgorithm extends Runnable
{

     Chromosome getWeakest();
     Chromosome getFittest();

     List<Chromosome> compete();
     List<Chromosome> reproduce(List<Chromosome> survivors, double pCrossover, int targetSize);
     List<Chromosome> reproduce(List<Chromosome> survivors, double pCrossover);
     List<Chromosome> getPopulation();
     void sort(List<Chromosome> chromosomes);
     void calculatePopulationFitness();
     void evaluateFitness(Chromosome chromo);
     void mutate(double p);
     void refresh();
     void setPopulation(List<Chromosome> population);
     boolean shouldTerminate();


}
