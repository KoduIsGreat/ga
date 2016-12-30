package org.aas.ga.factory;

import org.aas.ga.chromo.Chromosome;
import org.aas.ga.genes.Gene;
import org.aas.ga.genes.AlleleOptions;

import java.util.Collection;
import java.util.Random;

/**
 * Created by Adam on 11/16/2016.
 */
public class ChromosomeFactory<C extends Chromosome> extends AbstractGeneticFactory<C> {
    private GeneFactory<? extends Gene> geneFactory;
    public ChromosomeFactory(Class<? extends C> clazz, AlleleOptions options, GeneFactory<? extends Gene> geneFactory)
    {
        super((Class<C>) clazz,DEFAULT_STORAGE_TYPE,DEFAULT_STORAGE_TYPE,options,new Random(),DEFAULT_SIZE);
        this.geneFactory = geneFactory;
    }
    public ChromosomeFactory(Class<? extends C>clazz, AlleleOptions options, GeneFactory<? extends Gene> geneFactory, Random seed){
        super((Class<C>) clazz,DEFAULT_STORAGE_TYPE,DEFAULT_STORAGE_TYPE,options,seed,DEFAULT_SIZE);
        this.geneFactory = geneFactory;
    }
    public ChromosomeFactory(Class<? extends C>clazz, AlleleOptions options, GeneFactory<? extends Gene> geneFactory, Random seed, int length) {
        super((Class<C>) clazz, DEFAULT_STORAGE_TYPE,DEFAULT_STORAGE_TYPE, options, seed, length);
        this.geneFactory = geneFactory;
    }
    public ChromosomeFactory(Class<? extends C> clazz, Class<? extends Collection>dataStructure, Class<? extends Collection>ds2, AlleleOptions options, GeneFactory<? extends Gene> geneFactory, Random seed, int length){
        super((Class<C>) clazz,dataStructure,ds2,options,seed,length);
        this.geneFactory = geneFactory;
    }

    @Override
    public C create() throws InstantiationException,IllegalAccessException
    {
        C chromo = clazz.newInstance();
        Collection<Gene> genes = (Collection<Gene>) phenotypeDataStructure.newInstance();
        genes.addAll(geneFactory.create(length));
        chromo.setGenes(genes);
        return chromo;
    }
}
