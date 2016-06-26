/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.aas.ga.trans;



import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;


import org.aas.ga.chromo.Chromosome;
import org.aas.ga.chromo.ListChromosome;
import org.aas.ga.genes.Gene;

/**
 *
 * @author Adam
 * @param <E>
 */
public abstract class AbstractTransformer<T extends Collection,E> implements Transformer<E>
{

    @Override
    public Collection<E> transformChromosome(Chromosome chromo)
    {        
        Collection<Gene> genes = chromo.getGenes();
        Collection<E> transformed;
        if( chromo instanceof ListChromosome)
            transformed = new ArrayList();
        else
            transformed = new LinkedHashSet<>();

        for(Gene gene : genes)
            transformed.add(transformGene(gene));
        return transformed;
    }
}
