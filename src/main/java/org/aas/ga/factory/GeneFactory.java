package org.aas.ga.factory;

import org.aas.ga.genes.Gene;
import org.aas.ga.genes.AlleleOptions;
import org.aas.ga.util.RandomUtil;

import java.util.Collection;
import java.util.Random;

/**
 * Created by Adam on 11/16/2016.
 */
public class GeneFactory<G extends Gene> extends AbstractGeneticFactory<G> {
    public GeneFactory(Class<? extends G> clazz, AlleleOptions options)
    {
        super((Class<G>) clazz,DEFAULT_STORAGE_TYPE,DEFAULT_STORAGE_TYPE,options,new Random(),DEFAULT_SIZE);

    }
    public GeneFactory(Class<? extends G>clazz, AlleleOptions options, Random seed){
        super((Class<G>)clazz,DEFAULT_STORAGE_TYPE,DEFAULT_STORAGE_TYPE,options,seed,DEFAULT_SIZE);
    }
    public GeneFactory(Class<? extends G>clazz, AlleleOptions options, Random seed, int length) {
        super((Class<G>)clazz, DEFAULT_STORAGE_TYPE,DEFAULT_STORAGE_TYPE, options, seed, length);
    }
    public GeneFactory(Class<? extends G> clazz, Class<? extends Collection>dataStructure, Class<? extends Collection>ds2, AlleleOptions options, Random seed, int length){
        super((Class<G>) clazz,dataStructure,ds2,options,seed,length);
    }



    @Override
    public G create(){
        try
        {
            G newGene = clazz.newInstance();
            newGene.setLength(length);

            Collection dna = phenotypeDataStructure.newInstance();
            while(dna.size() < DEFAULT_SIZE)
                dna.add(RandomUtil.getRandomGeneticMaterial(options,seed));

            newGene.setDna(dna);
            return newGene;
        }
        catch (InstantiationException  | IllegalAccessException  e)
        {
            e.printStackTrace();
            return null;
        }
    }

}
