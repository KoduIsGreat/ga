package org.aas.ga.sim;

import org.aas.ga.chromo.Chromosome;
import org.aas.ga.genes.Gene;
import org.aas.ga.genes.AlleleOptions;
import org.aas.ga.util.RandomUtil;

import java.util.*;

/**
 * Created by Adam on 11/16/2016.
 */
public class BaseMutator implements Mutator
{
    double p;
    Random seed;
    private AlleleOptions options;
    private Class <? extends Collection>dnaDataStructure;

    public BaseMutator(double p, AlleleOptions options, Class<? extends Collection> geneDataStructure, Random seed)
    {
        this.p= p;
        this.seed = seed;
        this.options = options;
        this.dnaDataStructure = geneDataStructure;
    }

    public BaseMutator(double p, AlleleOptions options, Class<? extends Collection> geneDataStructure)
    {
        this(p, options,geneDataStructure,new Random());
    }

    private void mutateGene(Gene gene, double p)
    {
        Random rand = new Random();
        if(Simulation.seed != null)
            rand = Simulation.seed;

        Collection<Object> newDna = null;
        try
        {
            newDna = dnaDataStructure.newInstance();
        }
        catch (InstantiationException | IllegalAccessException e)
        {
            e.printStackTrace();
        }
        assert newDna != null;
        for(Object strand : gene.getDna())
        {
            if(rand.nextDouble() < p && !gene.isDominant())
            {
                newDna.add(RandomUtil.getRandomGeneticMaterial(options,seed));
            }
            else
            {
                newDna.add(strand);
            }
        }
        gene.setDna(newDna);
    }

    private void mutateGene(Gene gene)
    {
        mutateGene(gene,p);
    }

    @Override
    public void mutate(Chromosome chromosome)
    {

        Iterator itr = chromosome.iterator();
        while(itr.hasNext())
        {
            Gene gene = (Gene) itr.next();
            mutateGene(gene);
        }
    }

    @Override
    public void refresh(Chromosome chromosome, double p)
    {
        Iterator itr = chromosome.iterator();
        while(itr.hasNext())
        {
            Gene gene = (Gene) itr.next();
            mutateGene(gene,p);
        }
    }
}
