package org.aas.ga.factory;

import org.aas.ga.chromo.Chromosome;
import org.aas.ga.genes.Gene;
import org.aas.ga.genes.GeneticMaterialOptions;

import java.util.Collection;
import java.util.Random;

/**
 * Created by Adam on 11/16/2016.
 */
public class ChromosomeFactory<C extends Chromosome> extends AbstractGeneticFactory<C> {
    private GeneFactory geneFactory;
    public ChromosomeFactory(Class<? extends C> clazz, GeneticMaterialOptions options,GeneFactory geneFactory)
    {
        super((Class<C>) clazz,DEFAULT_STORAGE_TYPE,DEFAULT_STORAGE_TYPE,options,new Random(),DEFAULT_SIZE);
        this.geneFactory = geneFactory;
    }
    public ChromosomeFactory(Class<? extends C>clazz, GeneticMaterialOptions options,GeneFactory geneFactory, Random seed){
        super((Class<C>) clazz,DEFAULT_STORAGE_TYPE,DEFAULT_STORAGE_TYPE,options,seed,DEFAULT_SIZE);
        this.geneFactory = geneFactory;
    }
    public ChromosomeFactory(Class<? extends C>clazz, GeneticMaterialOptions options,GeneFactory geneFactory, Random seed, int length) {
        super((Class<C>) clazz, DEFAULT_STORAGE_TYPE,DEFAULT_STORAGE_TYPE, options, seed, length);
        this.geneFactory = geneFactory;
    }
    public ChromosomeFactory(Class<? extends C> clazz, Class<? extends Collection>dataStructure,Class<? extends Collection>ds2, GeneticMaterialOptions options,GeneFactory geneFactory, Random seed, int length){
        super((Class<C>) clazz,dataStructure,ds2,options,seed,length);
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
            genes = (Collection<Gene>) phenotypeDataStructure.newInstance();
        }
        catch (InstantiationException | IllegalAccessException e)
        {
            e.printStackTrace();
        }
        genes.addAll(geneFactory.create(length));
        chromo.setGenes(genes);
        return chromo;
    }
}
