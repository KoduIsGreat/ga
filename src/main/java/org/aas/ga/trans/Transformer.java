package org.aas.ga.trans;

import org.aas.ga.chromo.Chromosome;
import org.aas.ga.genes.Gene;

import java.util.Collection;


/**
 * Created by Adam on 6/12/2016.
 */
public interface Transformer<E> {
    public E transformGene(Gene gene);
    public Collection<E> transformChromosome(Chromosome chromo);
}
