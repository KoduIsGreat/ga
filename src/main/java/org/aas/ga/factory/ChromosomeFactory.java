package org.aas.ga.factory;

import org.aas.ga.chromo.Chromosome;
import org.aas.ga.genes.Gene;
import org.aas.ga.genes.GeneticMaterialOptions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

/**
 * Created by Adam on 11/16/2016.
 */
public class ChromosomeFactory<C extends Chromosome> extends AbstractGeneticFactory<C> {
    private GeneFactory geneFactory;
    public ChromosomeFactory(Class<C> clazz, GeneticMaterialOptions options,GeneFactory geneFactory)
    {
        super(clazz,DEFAULT_STORAGE_TYPE,options,new Random(),DEFAULT_SIZE);
        this.geneFactory = geneFactory;
    }
    public ChromosomeFactory(Class<C>clazz, GeneticMaterialOptions options,GeneFactory geneFactory, Random seed){
        super(clazz,DEFAULT_STORAGE_TYPE,options,seed,DEFAULT_SIZE);
        this.geneFactory = geneFactory;
    }
    public ChromosomeFactory(Class<C>clazz, GeneticMaterialOptions options,GeneFactory geneFactory, Random seed, int length) {
        super(clazz, DEFAULT_STORAGE_TYPE, options, seed, length);
        this.geneFactory = geneFactory;
    }
    public ChromosomeFactory(Class<C> clazz, Class<? extends Collection>dataStructure, GeneticMaterialOptions options,GeneFactory geneFactory, Random seed, int length){
        super(clazz,dataStructure,options,seed,length);
        this.geneFactory = geneFactory;
    }

    @Override
    public C create()
    {
        C chromo = null;
        Collection<Gene> genes = null;

        try
        {
            chromo = clazz.newInstance();
            genes = (Collection<Gene>) dataStructure.newInstance();
        }
        catch (InstantiationException | IllegalAccessException e)
        {
            e.printStackTrace();
        }


        while(genes.size()<length)
            genes.add(geneFactory.create());
        chromo.setGenes(genes);
        return chromo;
    }

    public List<C> create(int size)
    {
        List<C> population = new ArrayList<>();
        while (population.size()<size)
            population.add(create());
        return population;
    }
}
