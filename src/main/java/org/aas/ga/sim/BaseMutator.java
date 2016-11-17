package org.aas.ga.sim;

import org.aas.ga.chromo.Chromosome;
import org.aas.ga.genes.Gene;
import org.aas.ga.genes.GeneticMaterialOptions;

import java.util.*;

/**
 * Created by Adam on 11/16/2016.
 */
public class BaseMutator<T extends Chromosome> implements Mutator<T>
{
    private double p;
    private Random seed;
    private GeneticMaterialOptions options;

    public BaseMutator(double p,GeneticMaterialOptions options,Random seed)
    {
        this.p= p;
        this.seed = seed;
        this.options = options;
    }

    public BaseMutator(double p,GeneticMaterialOptions options)
    {
        this(p, options,new Random());
    }


    private Object getRandomGeneticMaterial()
    {
        ArrayList<Object> list  = new ArrayList<>(options.getOptions());
        return list.get(seed.nextInt(list.size()));
    }

    private void mutate(Gene gene)
    {
        Collection dna =  gene.getDna();
        Iterator itr = dna.iterator();
        while(itr.hasNext())
        {
            Object strand = itr.next();
            if(seed.nextDouble() < p && !gene.isDominant())
            {
               strand = getRandomGeneticMaterial();
            }
        }
    }

    @Override
    public void mutate(Chromosome chromosome)
    {
        Collection<Gene> genes = chromosome.getGenes();
        for (Gene gene : genes)
            mutate(gene);
    }
}
