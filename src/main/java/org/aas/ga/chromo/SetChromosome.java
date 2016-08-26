package org.aas.ga.chromo;

import org.aas.ga.factory.GeneFactory;
import org.aas.ga.genes.Gene;

import java.util.*;

/**
 * Created by Adam on 6/26/2016.
 */
public class SetChromosome extends AbstractCollectionChromosome<LinkedHashSet<Gene>> {

    public SetChromosome(){}



    public SetChromosome(LinkedHashSet<Gene> genes)
    {
        super(genes);
    }


    @Override
    public Collection<Gene> crossover(Chromosome other, int p)
    {
        ArrayList<Gene> otherList = new ArrayList<>(other.getGenes());
        ArrayList<Gene> list = new ArrayList<>(genes);
        LinkedHashSet<Gene> childDNA = new LinkedHashSet();
        childDNA.addAll(list.subList(0,p));
        childDNA.addAll(otherList.subList(p,otherList.size()));
        if(!childDNA.containsAll(genes))
            childDNA.addAll(genes);

        genes = new LinkedHashSet<>(childDNA);


        return genes;
    }
    private void repair(List geneticOptions,LinkedHashSet<Gene> genes)
    {
        if(!genes.containsAll(geneticOptions))
            genes.addAll(geneticOptions);

    }
    @Override
    public void mutate(double p)
    {
        ArrayList<Gene> list = new ArrayList<>(genes);

        Random rand = new Random();
        for(Gene gene : list)
        {
            if(rand.nextDouble() < p)
            {
                Collections.swap(list,list.indexOf(gene),rand.nextInt(list.size()-1));
            }
        }

        genes = new LinkedHashSet<>(list);
    }

    @Override
    public Chromosome copy()
    {
        SetChromosome chromo = new SetChromosome(this.genes){};
        chromo.setFitness(this.getFitness());
        return chromo;
    }

}
