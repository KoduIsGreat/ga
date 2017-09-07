package org.aas.ga.chromo;

import org.aas.ga.genes.Gene;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Adam on 6/11/2016.
 */
public interface Chromosome extends Comparable<Chromosome>
{
     Gene[] crossover(Chromosome other, int p);
     int length();
     Chromosome copy();
     Gene[] getGenes();
     void setGenes(Gene[] genes);
     Double getFitness();
     void setFitness(Double f);
}
