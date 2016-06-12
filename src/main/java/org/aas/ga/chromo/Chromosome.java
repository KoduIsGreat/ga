package org.aas.ga.chromo;

import org.aas.ga.genes.Gene;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Adam on 6/11/2016.
 */
public interface Chromosome extends Comparable<Chromosome>{
    public Chromosome crossover(Chromosome other, int p);
    public void mutate(double p);
    public int length();
    public Chromosome copy();
    public Chromosome createRandom();
    public List<Gene> getGenes();
    public void setGenes(ArrayList<Gene> genes);
    public Double getFitness();
    public void setFitness(Double f);
    public Iterator<Gene> iterator();

}
