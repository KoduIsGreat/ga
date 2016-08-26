package org.aas.ga.factory;

import org.aas.ga.chromo.Chromosome;
import org.aas.ga.chromo.SetChromosome;
import org.aas.ga.genes.Gene;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by Adam on 8/25/2016.
 */
public class ChromosomeFactory {
    Class<? extends Chromosome> chromosome;

    private GeneFactory geneFactory;
    private static final int DEFAULT_CHROMO_LENGTH = 10;
    private int chromoLength;
    public ChromosomeFactory(){}

    public ChromosomeFactory(Class<? extends Chromosome> clazz,GeneFactory geneFactory)
    {
        chromosome = clazz;
        this.geneFactory = geneFactory;
        this.chromoLength = DEFAULT_CHROMO_LENGTH;
    }

    public ChromosomeFactory(Class<? extends Chromosome> clazz,GeneFactory geneFactory,int chromoLength)
    {
        chromosome = clazz;
        this.geneFactory = geneFactory;
        this.chromoLength = chromoLength;
    }

    private List<Gene> createGenes(){
        ArrayList<Gene> genes = new ArrayList<>();

        while(genes.size()<chromoLength)
            genes.add(geneFactory.createGene());
        return genes;
    }

    public List<Chromosome> populate(int n){
        List<Chromosome> population = new ArrayList<>();
        while(population.size()<n)
            population.add(createChromosome());
        return population;
    }
    public Chromosome createChromosome()
    {
        try
        {
            Chromosome chromo = chromosome.newInstance();
            chromo.setGenes(createGenes());

            return chromo;
        }
        catch (InstantiationException e)
        {
            e.printStackTrace();
            return null;
        }
        catch (IllegalAccessException e)
        {
            e.printStackTrace();
            return null;
        }
    }
    public Class<? extends Chromosome> getChromosome() {
        return chromosome;
    }

    public void setChromosome(Class<? extends Chromosome> chromosome) {
        this.chromosome = chromosome;
    }

    public GeneFactory getGeneFactory() {
        return geneFactory;
    }

    public void setGeneFactory(GeneFactory geneFactory) {
        this.geneFactory = geneFactory;
    }

    public int getChromoLength() {
        return chromoLength;
    }

    public void setChromoLength(int chromoLength) {
        this.chromoLength = chromoLength;
    }
}
