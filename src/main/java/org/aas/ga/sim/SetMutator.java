package org.aas.ga.sim;

import org.aas.ga.chromo.Chromosome;
import org.aas.ga.genes.AlleleOptions;
import org.aas.ga.genes.Gene;

import java.util.*;

/**
 * Created by Adam on 12/3/2016.
 */
public class SetMutator implements Mutator
{
    private double p;
    private Random seed;
    private AlleleOptions options;
    private Class <? extends Collection>dnaDataStructure;

    public SetMutator(){}

    public SetMutator(double p, AlleleOptions options, Class<? extends Collection> geneDataStructure, Random seed) {
        this.p= p;
        this.seed = seed;
        this.options = options;
        this.dnaDataStructure = geneDataStructure;
    }

    public SetMutator(double p, AlleleOptions options, Class<? extends Collection> geneDataStructure) {
        this.p= p;
        this.seed = new Random();
        this.options = options;
        this.dnaDataStructure = geneDataStructure;
    }
    // re-write using array shuffle per element
    private void reorderChromosome(Chromosome c , double p)
    {
        Gene[] genes = c.getGenes();
        for(int i=0; i< genes.length; i ++)
        {
            if(seed.nextDouble() < p)
            {
                Gene current = genes[i].copy();
                int swapLocation =seed.nextInt(genes.length);
                Gene swap= genes[swapLocation];

                genes[i] = swap;
                genes[swapLocation] = current;
            }
        }

    }
    @Override
    public void mutate(Chromosome chromosome)
    {
        reorderChromosome(chromosome,p);
    }

    @Override
    public void refresh(Chromosome chromosome, double p)
    {
        reorderChromosome(chromosome,p);
    }
    @Override
    public double getP() {
        return p;
    }
    @Override
    public void setP(double p) {
        this.p = p;
    }
    @Override
    public Random getSeed() {
        return seed;
    }
    @Override
    public void setSeed(Random seed) {
        this.seed = seed;
    }
    @Override
    public AlleleOptions getOptions() {
        return options;
    }
    @Override
    public void setOptions(AlleleOptions options) {
        this.options = options;
    }
    @Override
    public Class<? extends Collection> getDnaDataStructure() {
        return dnaDataStructure;
    }
    @Override
    public void setDnaDataStructure(Class<? extends Collection> dnaDataStructure) {
        this.dnaDataStructure = dnaDataStructure;
    }
}
