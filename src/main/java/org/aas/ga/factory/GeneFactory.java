package org.aas.ga.factory;

import org.aas.ga.genes.Gene;
import org.aas.ga.genes.GeneticMaterialOptions;
import org.aas.ga.util.RandomUtil;

import java.util.Collection;
import java.util.Random;

/**
 * Created by Adam on 11/16/2016.
 */
public class GeneFactory<G extends Gene> extends AbstractGeneticFactory<G> {
    public GeneFactory(Class<G> clazz, GeneticMaterialOptions options)
    {
        super(clazz,DEFAULT_STORAGE_TYPE,options,new Random(),DEFAULT_SIZE);

    }
    public GeneFactory(Class<G>clazz, GeneticMaterialOptions options, Random seed){
        super(clazz,DEFAULT_STORAGE_TYPE,options,seed,DEFAULT_SIZE);
    }
    public GeneFactory(Class<G>clazz, GeneticMaterialOptions options, Random seed, int length) {
        super(clazz, DEFAULT_STORAGE_TYPE, options, seed, length);
    }
    public GeneFactory(Class<G> clazz, Class<? extends Collection>dataStructure, GeneticMaterialOptions options, Random seed, int length){
        super(clazz,dataStructure,options,seed,length);
    }
    @Override
    public G create(){
        try
        {
            G newGene = clazz.newInstance();
            newGene.setLength(length);

            Collection dna = dataStructure.newInstance();
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
