package org.aas.ga.chromo;

import org.aas.ga.genes.Gene;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created by Adam on 6/26/2016.
 */
public class SetChromosome extends AbstractCollectionChromosome<Set<Gene>> {

    public SetChromosome(){}

    public SetChromosome(Gene gene)
    {
        super(gene);
    }

    public SetChromosome(Set<Gene> genes, Gene gene_type)
    {
        super(genes,gene_type);
    }
    @Override
    public Collection<Gene> crossover(Chromosome other, int p) {
        return null;
    }

    @Override
    public Chromosome copy() {
        SetChromosome chromo = new SetChromosome(this.genes,this.geneType){};
        chromo.setFitness(this.getFitness());
        return chromo;
    }

    @Override
    public Chromosome createRandom(Set<Gene> coll, int length)
    {
        Set<Gene> genes = new LinkedHashSet<>();
        for(int i =0 ; i <length ; i ++)
            genes.add(geneType.createRandom());

        return  new SetChromosome(genes,geneType);
    }
}
