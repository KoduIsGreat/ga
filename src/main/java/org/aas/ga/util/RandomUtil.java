package org.aas.ga.util;

import org.aas.ga.genes.AlleleOptions;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Adam on 8/29/2016.
 */
public class RandomUtil
{
    public static <T> T  getRandomGeneticMaterial(AlleleOptions options, Random seed)
    {
        ArrayList<T> list  = new ArrayList<>(options.getOptions());
        return list.get(seed.nextInt(list.size()));
    }
}
