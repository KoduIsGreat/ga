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

    public DefaultChromosome(){

    }
    protected DefaultChromosome(Gene gene)
    {
        fitness = Double.NaN;
        gene_type = gene;
    }

    private DefaultChromosome(ArrayList<Gene> genes, Gene gene_type){
        this.gene_type = gene_type;
        this.genes = genes;
        fitness = Double.NaN;
    }

    @Override
    public Chromosome crossover(Chromosome other, int p) {
        ArrayList<Gene> childDNA = new ArrayList();
        childDNA.addAll(this.genes.subList(0,p));
        childDNA.addAll(other.getGenes().subList(p,other.getGenes().size()));

        return new DefaultChromosome(childDNA,this.gene_type);
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
        Chromosome chromo = new DefaultChromosome(this.genes,this.gene_type);
        chromo.setFitness(this.fitness);
        return chromo;
    }

    @Override
    public Chromosome createRandom(int length) {
        ArrayList<Gene> newGenes= new ArrayList();
        for(int i =0 ; i <length ; i ++)
            newGenes.add(gene_type.createRandom());
        return new DefaultChromosome(newGenes,gene_type);
    }

    @Override
    public int length(){
        return this.genes.size();
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

    @Override
    public String toString(){
        Iterator<Gene> itr = iterator();
        StringBuilder sb = new StringBuilder();
        while(itr.hasNext()){
            sb.append(itr.next().toString());
        }
        return sb.toString();
    }

}