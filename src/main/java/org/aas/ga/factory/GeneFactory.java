package org.aas.ga.factory;

import org.aas.ga.genes.BaseGene;
import org.aas.ga.genes.Gene;
import org.aas.ga.genes.AlleleOptions;
import org.aas.ga.util.RandomUtil;

import java.util.Collection;
import java.util.Random;

import static org.aas.ga.util.RandomUtil.getRandomGeneticMaterial;

/**
 * Created by Adam on 11/16/2016.
 */
public class GeneFactory {

    AlleleOptions options;
    Random seed;
    public GeneFactory( AlleleOptions options, Random seed)
    {

        this.options = options;
        this.seed = seed;

    }




    public BaseGene create(){
        BaseGene newGene = new BaseGene();
        newGene.setDna(getRandomGeneticMaterial(options,seed));
        return newGene;
    }

}
