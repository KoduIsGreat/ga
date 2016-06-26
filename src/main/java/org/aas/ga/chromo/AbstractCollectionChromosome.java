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
    private boolean reordering;
    public AbstractCollectionChromosome(){}

    public AbstractCollectionChromosome(Gene gene)
    {
       this(null,gene);
    }

    public AbstractCollectionChromosome(T genes, Gene type){
        this.geneType = type;
        this.genes = genes;

        if(genes instanceof Set)
            this.reordering = true;
        else
            this.reordering = false;

        fitness = Double.NaN;
    }

    @Override
    public void mutate(double p)
    {
        for (Gene gene : genes)
            gene.mutate(p);

    }

    @Override
    public Collection<Gene> crossover(Chromosome other, int p){

        if(this.genes instanceof List)
        {
            List<Gene> list = (List) genes;
            ArrayList<Gene> childDNA = new ArrayList();
            childDNA.addAll(list.subList(0, p));
            ArrayList<Gene> otherGenes = (ArrayList<Gene>) other.getGenes();
            childDNA.addAll(otherGenes.subList(p, other.getGenes().size()));

            return childDNA;
        }
        return null;
    }

    @Override
    public Chromosome copy()
    {
        Chromosome chromo = new AbstractCollectionChromosome(this.genes,this.geneType){};
        chromo.setFitness(this.fitness);
        return chromo;
    }

    @Override
    public Chromosome createRandom(T coll, int length) {
        for(int i =0 ; i <length ; i ++)
            coll.add(geneType.createRandom());
        return new AbstractCollectionChromosome(coll,geneType){};
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
