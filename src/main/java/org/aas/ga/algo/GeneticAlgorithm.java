package org.aas.ga.algo;

import org.aas.ga.chromo.Chromosome;
import org.aas.ga.err.WeightsException;
import org.aas.ga.sim.Mutator;

import java.util.List;
import java.util.Map;

/**
 * Created by Adam on 6/12/2016.
 */
public interface GeneticAlgorithm
{

     Chromosome getWeakest();
     Chromosome getFittest();
     List<Chromosome> compete();
     List<Chromosome> reproduce(List<Chromosome> survivors, double pCrossover, int targetSize);
     List<Chromosome> reproduce(List<Chromosome> survivors, double pCrossover);
     List<Chromosome> getPopulation();
     void setPopulation(List<Chromosome> population);
     void sort(List<Chromosome> chromosomes);
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
     Map<Integer, Chromosome> getOverallFitnessMap();
     Chromosome getOverall_fittest();
     void run() throws WeightsException;

}
