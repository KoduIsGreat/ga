package org.aas.ga.algo;

import org.aas.ga.chromo.Chromosome;
import org.aas.ga.sim.Mutator;

import java.util.List;
import java.util.Map;

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
     void mutate();
     void refresh();
     void setDoElitism(boolean doElitism);
     void setMutator(Mutator mutator);
     boolean isDoElitism();
     void setInverseFitnessRanking(boolean inverseFitnessRanking);
     boolean isInverseFitnessRanking();
     boolean shouldTerminate();
     Map<Integer, T> getOverallFitnessMap();
     T getOverall_fittest();


}
