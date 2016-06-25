package org.aas.ga.chromo;

import org.aas.ga.genes.Gene;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Adam on 6/16/2016.
 */
public class ChromosomeFactory
{
    public static List<Chromosome> createDefaultChromosomes(Gene gene_type, int chromo_len, int n)
    {
        DefaultChromosome chromosome = new DefaultChromosome(chromo_len,gene_type);
        List<Chromosome> population = new ArrayList<>();
        for(int i = population.size() ; i <n ;i++)
            population.add(chromosome.createRandom());
        return population;
    }
}
