package org.aas.ga.sim;

import org.aas.ga.chromo.Chromosome;
import org.aas.ga.genes.Gene;
import org.aas.ga.genes.GeneticMaterialOptions;
import org.aas.ga.util.RandomUtil;

import java.util.*;

/**
 * Created by Adam on 11/16/2016.
 */
public class BaseMutator<T extends Chromosome> implements Mutator<T>
{
    private double p;
    private Random seed;
    private GeneticMaterialOptions options;
    private Class <? extends Collection>dnaDataStructure;

    public BaseMutator(double p,GeneticMaterialOptions options,Class<? extends Collection> geneDataStructure,Random seed)
    {
        this.p= p;
        this.seed = seed;
        this.options = options;
        this.dnaDataStructure = geneDataStructure;
    }

    public BaseMutator(double p,GeneticMaterialOptions options,Class<? extends Collection> geneDataStructure)
    {
        this(p, options,geneDataStructure,new Random());
    }

    private void mutateGene(Gene gene, double p)
    {
        Random rand = new Random();
        if(Simulation.seed != null)
            rand = Simulation.seed;

        Collection newDna = null;
        try
        {
            newDna = dnaDataStructure.newInstance();
        }
        catch (InstantiationException | IllegalAccessException e)
        {
            e.printStackTrace();
        }
        for(Object strand : gene.getDna())
        {
            if(rand.nextDouble() < p && !gene.isDominant())
            {
                newDna.add(RandomUtil.getRandomGeneticMaterial(Simulation.options,Simulation.seed));
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
    public void mutate(T chromosome)
    {
        Iterator<Gene> itr = chromosome.iterator();
        while(itr.hasNext())
            mutateGene(itr.next());
    }

    @Override
    public void refresh(T chromosome, double p)
    {
        Iterator<Gene> itr = chromosome.iterator();
        while(itr.hasNext())
            mutateGene(itr.next(),p);
    }
}
