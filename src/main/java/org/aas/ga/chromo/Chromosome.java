/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.aas.ga.chromo;

import java.util.ArrayList;

import org.aas.ga.genes.*;


/**
 *
 * @author Adam
 * 
 */
public class Chromosome<T extends Gene>
{

    private ArrayList<Gene> genes = new ArrayList();

    private Double fitness;
    
    public Chromosome(int gene_len, int chromo_len, T gene)
    {
        fitness = Double.NaN;
        gene.setLength(gene_len);
        for(int i =0 ; i <chromo_len ; i ++)
            genes.add(gene.createRandom());
    }
    

    
    public ArrayList<Gene> getGenes()
    {
        return genes;
    }

    public void setGenes(ArrayList<Gene> genes)
    {
        this.genes = genes;
    }

    public Double getFitness()
    {
        return fitness;
    }

    public void setFitness(Double fitness)
    {
        this.fitness = fitness;
    }
}




