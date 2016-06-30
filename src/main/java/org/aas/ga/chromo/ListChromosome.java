/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.aas.ga.chromo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


import org.aas.ga.genes.*;


/**
 *
 * @author Adam
 * 
 */
public class ListChromosome extends AbstractCollectionChromosome<List<Gene>>
{


    public ListChromosome(){

    }
    protected ListChromosome(Gene gene)
    {
        super(gene);
    }

    public ListChromosome(List<Gene> genes, Gene gene_type){
        super(genes,false,gene_type);
    }

    public ListChromosome(List<Gene> genes,boolean reordering, Gene gene_type){
        super(genes,reordering,gene_type);
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
        ListChromosome chromo = new ListChromosome(this.genes,this.geneType){};
        chromo.setFitness(this.getFitness());
        return chromo;
    }

    @Override
    public Chromosome createRandom( int length) {
        List<Gene> genes = new ArrayList<>();
        for(int i =0 ; i <length ; i ++)
            genes.add(geneType.createRandom());

        return  new ListChromosome(genes,geneType);
    }

}