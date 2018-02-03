package org.aas.ga.chromo;

import org.aas.ga.genes.Gene;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;



/**
 * Created by Adam on 6/26/2016.
 */
public class BaseChromosome  implements Chromosome {

    Logger LOG = LoggerFactory.getLogger(BaseChromosome.class);
    private Gene[] genes;
    private Double fitness;

    public BaseChromosome(){}

    public BaseChromosome(Gene[] genes)
    {
        this.genes = genes;
        fitness = Double.NaN;
    }


    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BaseChromosome that = (BaseChromosome) o;

        return Arrays.deepEquals(genes,that.genes);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(genes);
    }

    @Override
    public Gene[] crossover(Chromosome other, int p)
    {

        Gene[] otherArray = other.getGenes();
        Gene[] offspring = new Gene[genes.length];
        for(int i =0; i <genes.length; i++)
        {
            if ( i < p)
                offspring[i] = genes[i];
            else
                offspring[i] = otherArray[i];
        }
        return offspring;
    }

    @Override
    public Chromosome copy()
    {
        BaseChromosome chromo = new BaseChromosome(genes);;
        chromo.setFitness(this.fitness);
        return chromo;
    }

    @Override
    public int length()
    {
        return this.genes.length;
    }

    @Override
    public int compareTo(Chromosome o)
    {
        if(this.fitness == o.getFitness())
            return 0;

        return (this.fitness < o.getFitness()) ? -1 : 1;
    }

    @Override
    public Gene[] getGenes()
    {
        return genes;
    }

    @Override
    public void setGenes(Gene[] genes)
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
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        for(int i =0 ; i < genes.length ; i++)
            sb.append(genes[i]);
        return sb.toString();
    }
}
