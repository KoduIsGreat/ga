package org.aas.ga.chromo;

import org.aas.ga.genes.Gene;
import org.aas.ga.util.RandomUtil;

import java.util.*;

import static org.aas.ga.sim.Simulation.options;
import static org.aas.ga.sim.Simulation.seed;

/**
 * Created by Adam on 6/26/2016.
 */
public class BaseChromosome<T extends Collection<Gene>>  implements Chromosome<T> {

    private T genes;
    private Double fitness;
    private Class<? extends Collection>  geneDataStructure;

    public BaseChromosome(){}



    public BaseChromosome(T genes)
    {
        this.genes = genes;
        this.geneDataStructure=genes.getClass();
        fitness = Double.NaN;
    }


    private Collection<Gene> createChildGeneDS()
    {
        try
        {
            return (Collection<Gene>) geneDataStructure.newInstance();
        }
        catch (InstantiationException | IllegalAccessException e)
        {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BaseChromosome<?> that = (BaseChromosome<?>) o;

        return genes.equals(that.genes);
    }

    @Override
    public int hashCode() {
        return genes.hashCode();
    }




    @Override
    public Collection<Gene> crossover(Chromosome other, int p)
    {
        List<Gene> list = new ArrayList(genes);
        Collection<Gene> childDNA = createChildGeneDS();
        childDNA.addAll(list.subList(0, p));
        ArrayList<Gene> otherGenes = new ArrayList<>(other.getGenes());
        childDNA.addAll(otherGenes.subList(p, other.getGenes().size()));
        while(childDNA.size() < this.genes.size())
            childDNA.addAll(genes);

        return childDNA;
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
    public String toString()
    {
        Iterator<Gene> itr = iterator();
        StringBuilder sb = new StringBuilder();
        while(itr.hasNext()){
            sb.append(itr.next().toString());
        }
        return sb.toString();
    }
}
