package org.aas.ga.chromo;

import org.aas.ga.genes.Gene;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Adam on 6/11/2016.
 */
public interface Chromosome<T extends Collection<Gene>> extends Comparable<Chromosome>
{
     Collection<Gene> crossover(Chromosome other, int p);
     int length();
     Chromosome copy();
     int compareTo(Chromosome o);
     Collection<Gene> getGenes();
     void setGenes(T genes);
     Double getFitness();
     void setFitness(Double f);
     Iterator<Gene> iterator();
}
