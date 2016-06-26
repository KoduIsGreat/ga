package org.aas.ga.chromo;

import org.aas.ga.genes.Gene;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Adam on 6/16/2016.
 */
public class ChromosomeFactory
{
    public static List<Chromosome> createListChromosomes(Gene gene_type, int chromo_len, int n)
    {
        ListChromosome chromosome = new ListChromosome(gene_type);
        List<Chromosome> population = new ArrayList<>();

        for(int i = population.size() ; i <n ;i++) {
            ArrayList<Gene> genes = new ArrayList<>();
            population.add(chromosome.createRandom(genes, chromo_len));
        }
        return population;
    }
}
