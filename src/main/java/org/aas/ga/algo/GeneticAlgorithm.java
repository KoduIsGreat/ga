package org.aas.ga.algo;

import org.aas.ga.chromo.Chromosome;

import java.util.List;

/**
 * Created by Adam on 6/12/2016.
 */
public interface GeneticAlgorithm<T extends Chromosome> extends Runnable
{

     T getWeakest();
     T getFittest();
     List<T> compete();
     List<T> reproduce(List<T> survivors, double pCrossover, int targetSize);
     List<T> reproduce(List<T> survivors, double pCrossover);
     List<T> getPopulation();
     void setPopulation(List<T> population);
     void sort(List<T> chromosomes);
     void calculateFitness();
     void evaluateFitness(Chromosome chromo);
     void mutate(double p);
     void refresh();
     void setDoElitism(boolean doElitism);
     boolean isDoElitism();
     void setInverseFitnessRanking(boolean inverseFitnessRanking);
     boolean isInverseFitnessRanking();
     boolean shouldTerminate();


}
