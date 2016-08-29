package org.aas.ga.factory;

import org.aas.ga.genes.BaseGene;
import org.aas.ga.genes.Gene;
import org.aas.ga.genes.GeneticMaterialOptions;
import org.aas.ga.util.RandomUtil;

import java.util.*;


/**
 * Created by Adam on 7/26/2016.
 */
public class GeneFactory {


    public static Gene createGene(Class<? extends Gene> gene,GeneticMaterialOptions options, int geneLength)
    {
        return createGene(gene,ArrayList.class,options,geneLength);
    }

    public static Gene createGene(Class<? extends Gene> gene,Class<? extends Collection> clazz, GeneticMaterialOptions options, int geneLength)
    {
        try
        {
            Gene newGene = gene.newInstance();
            newGene.setLength(geneLength);
            newGene.setDna(RandomUtil.createRandomDNA(clazz,options));
            return newGene;
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
}
