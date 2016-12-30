package org.aas.ga.sim;

import org.aas.ga.chromo.Chromosome;
import org.aas.ga.genes.AlleleOptions;
import org.aas.ga.genes.Gene;

import java.util.*;

/**
 * Created by Adam on 12/3/2016.
 */
public class SetMutator extends BaseMutator
{
    public SetMutator(double p, AlleleOptions options, Class<? extends Collection> geneDataStructure, Random seed) {
        super(p, options, geneDataStructure, seed);
    }

    public SetMutator(double p, AlleleOptions options, Class<? extends Collection> geneDataStructure) {
        super(p, options, geneDataStructure);
    }

    private void reorderChromosome(Chromosome c , double p){
        if(seed.nextDouble() < p)
        {
            ArrayList<Gene> genes = new ArrayList<>();
            genes.addAll(c.getGenes());

            Collections.shuffle(genes, seed);
            LinkedHashSet<Gene> newOrder = new LinkedHashSet<>();
            newOrder.addAll(genes);
            c.setGenes(newOrder);
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
}
