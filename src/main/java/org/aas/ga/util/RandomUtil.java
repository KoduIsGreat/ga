package org.aas.ga.util;

import org.aas.ga.genes.GeneticMaterialOptions;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Adam on 8/29/2016.
 */
public class RandomUtil
{
    public static  Object getRandomGeneticMaterial(GeneticMaterialOptions options,Random seed)
    {
        ArrayList<Object> list  = new ArrayList<>(options.getOptions());
        Random rand = new Random();
        if(seed != null)
            rand = seed;
        return list.get(rand.nextInt(list.size()));
    }
}
