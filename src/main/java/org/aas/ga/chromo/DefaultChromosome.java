/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.aas.ga.chromo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.aas.ga.genes.*;


/**
 *
 * @author Adam
 * 
 */
public class DefaultChromosome implements Chromosome
{

    private ArrayList<Gene> genes = new ArrayList();
    private Gene gene_type;
    private Double fitness;
    private int length;
    protected DefaultChromosome(int chromo_len, Gene gene)
    {
        fitness = Double.NaN;
        gene_type = gene;
        length =chromo_len;
    }

    private DefaultChromosome(ArrayList<Gene> genes, Gene gene_type, int length){
        this.gene_type = gene_type;
        this.genes = genes;
        this.length =length;
        fitness = Double.NaN;
    }

    @Override
    public Chromosome crossover(Chromosome other, int p) {
        ArrayList<Gene> childDNA = new ArrayList();
        childDNA.addAll(this.genes.subList(0,p));
        childDNA.addAll(other.getGenes().subList(p,other.getGenes().size()+1));

        return new DefaultChromosome(childDNA,this.gene_type,this.length);
    }

    @Override
    public void mutate(double p)
    {
        for(Gene gene : genes)
            gene.mutate(p);
    }

    @Override
    public Chromosome copy()
    {
        Chromosome chromo = new DefaultChromosome(this.genes,this.gene_type,this.length);
        chromo.setFitness(this.fitness);
        return chromo;
    }

    @Override
    public Chromosome createRandom() {
        ArrayList<Gene> newGenes= new ArrayList();
        for(int i =0 ; i <this.length ; i ++)
            newGenes.add(gene_type.createRandom());
        return new DefaultChromosome(newGenes,gene_type,this.length);
    }

    @Override
    public int compareTo(Chromosome o)
    {
        if(this.fitness == o.getFitness())
            return 0;

        return (this.fitness < o.getFitness()) ? -1 : 1;
    }

    @Override
    public List<Gene> getGenes()
    {
        return genes;
    }

    @Override
    public void setGenes(ArrayList<Gene> genes)
    {
        this.genes = genes;
    }

    @Override
    public Double getFitness()
    {
        return fitness;
    }

    @Override
    public void setFitness(Double f) {this.fitness =f;}

    @Override
    public Iterator<Gene> iterator()
    {
        return this.genes.iterator();
    }

    public static class ChromosomeFactory
    {
        public static ArrayList<Chromosome> createChromosomes(Gene gene_type,int chromo_len,int n)
        {
            DefaultChromosome chromosome = new DefaultChromosome(chromo_len,gene_type);
            ArrayList<Chromosome> population = new ArrayList<>();
            for(int i = population.size() ; i <n ;i++)
                population.add(chromosome.createRandom());
            return population;
        }
    }
}