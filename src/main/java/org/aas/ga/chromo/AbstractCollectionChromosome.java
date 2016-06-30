package org.aas.ga.chromo;

import org.aas.ga.genes.Gene;

import java.util.*;

/**
 * Created by Adam on 6/26/2016.
 */
public abstract class AbstractCollectionChromosome<T extends Collection<Gene>>  implements Chromosome<T> {

    protected T genes;
    protected Gene geneType;
    private Double fitness;

    public AbstractCollectionChromosome(){}

    public AbstractCollectionChromosome(Gene gene)
    {
       this(null,gene);
    }

    public AbstractCollectionChromosome(T genes, Gene type)
    {
        this.geneType = type;
        this.genes = genes;
        fitness = Double.NaN;
    }

    @Override
    public void mutate(double p)
    {
        for (Gene gene : genes)
            gene.mutate(p);
    }

    @Override
    public abstract Collection<Gene> crossover(Chromosome other, int p);

    @Override
    public abstract Chromosome copy();

    @Override
    public abstract Chromosome createRandom( int length);

    @Override
    public int length()
    {
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
    public Collection<Gene> getGenes()
    {
        return genes;
    }

    @Override
    public void setGenes(T genes)
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
