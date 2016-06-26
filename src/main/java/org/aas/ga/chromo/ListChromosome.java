/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.aas.ga.chromo;

import java.util.ArrayList;
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

    public ListChromosome(ArrayList<Gene> genes, Gene gene_type){
        super(genes,gene_type);
    }


}