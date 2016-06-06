/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.aas.ga.trans;



import java.util.ArrayList;
import java.util.List;
import org.aas.ga.chromo.Chromosome;
import org.aas.ga.genes.Gene;

/**
 *
 * @author Adam
 * @param <E>
 */
public abstract class BaseTransformer<E>
{
    
    public abstract E transformGene(Gene gene);
    
    public List<E> transformChromosome(Chromosome chromo)
    {        
        List<Gene> genes = chromo.getGenes();
        List<E> transformed = new ArrayList<>();
        
        for(Gene gene : genes)
            transformed.add(transformGene(gene));
        return transformed;
    }
}
