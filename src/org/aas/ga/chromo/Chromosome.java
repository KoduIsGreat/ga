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
public class Chromosome 
{

    private ArrayList<Gene> genes = new ArrayList<>();

    private Double fitness;
    
    public Chromosome(int gene_len, int chromo_len, Gene geneType)
    {
        fitness = Double.NaN;
        try
        {
            for(int i =0 ; i <chromo_len ; i ++)
                genes.add(determineGene(geneType,gene_len));
        }
        catch (GeneNotSupportedException ex)
        {
            ex.printStackTrace();
        }
    }
    
    private Gene determineGene(Gene geneType, int len) throws GeneNotSupportedException{
        if( geneType instanceof BinaryGene)
        {
            return new BinaryGene(len).createRandom();
        }
        if (geneType instanceof AsciiGene)
        {
            return new AsciiGene(len).createRandom();
        }
        if(geneType instanceof Base10Gene)
        {
            return new Base10Gene(len).createRandom();
        }
        if(geneType instanceof DNAGene)
        {
            return new DNAGene(len).createRandom();
        }
        throw new GeneNotSupportedException("Gene "+geneType.toString() + " is not supported",new Exception());
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



class GeneNotSupportedException extends Exception {
    public GeneNotSupportedException(String message,Throwable cause){
        super(message,cause);
    }
}
