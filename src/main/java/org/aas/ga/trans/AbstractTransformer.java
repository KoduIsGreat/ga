/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.aas.ga.trans;




import java.util.Collection;
import org.aas.ga.chromo.Chromosome;
import org.aas.ga.genes.Gene;

/**
 *
 * @author Adam
 * @param <E>
 */
public abstract class AbstractTransformer<E> implements Transformer<E>
{

    @Override
    public Collection<E> transformChromosome(Chromosome chromo, Collection<E> coll)
    {        
//        Collection<Gene> genes = chromo.getGenes();
//        for(Gene gene : genes)
//            coll.add(transformGene(gene));
//        return coll;
        return null;
    }
}
