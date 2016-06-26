package org.aas.ga.chromo;

import org.aas.ga.genes.Gene;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Adam on 6/11/2016.
 */
public interface Chromosome<T extends Collection<Gene>> extends Comparable<Chromosome>{
    public Collection<Gene> crossover(Chromosome other, int p);
    public void mutate(double p);
    public int length();
    public Chromosome copy();
    public Chromosome createRandom(T coll,int length);
    int compareTo(Chromosome o);
    public Collection<Gene> getGenes();
    public void setGenes(T genes);
    public Double getFitness();
    public void setFitness(Double f);
    public Iterator<Gene> iterator();

}
