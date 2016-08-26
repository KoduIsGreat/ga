/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.aas.ga.chromo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


import org.aas.ga.factory.GeneFactory;
import org.aas.ga.genes.*;


/**
 *
 * @author Adam
 * 
 */
public class ListChromosome extends AbstractCollectionChromosome<List<Gene>>
{


    public ListChromosome(){}
    public ListChromosome(List<Gene> genes){
        super(genes);
    }
    @Override
    public Collection<Gene> crossover(Chromosome other,int p)
    {

        List<Gene> list = (List) genes;
        ArrayList<Gene> childDNA = new ArrayList();
        childDNA.addAll(list.subList(0, p));
        ArrayList<Gene> otherGenes = (ArrayList<Gene>) other.getGenes();
        childDNA.addAll(otherGenes.subList(p, other.getGenes().size()));

        return childDNA;
    }

    @Override
    public Chromosome copy()
    {
        ListChromosome chromo = new ListChromosome(this.genes){};
        chromo.setFitness(this.getFitness());
        return chromo;
    }


}