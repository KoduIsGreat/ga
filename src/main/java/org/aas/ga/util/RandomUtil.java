package org.aas.ga.util;

import org.aas.ga.genes.GeneticMaterialOptions;
import org.aas.ga.sim.Simulation;

import java.util.*;

/**
 * Created by Adam on 8/28/2016.
 */
public class RandomUtil {

    private static final int DEFAULT_GENE_SIZE = 1;

    public static Object getRandomGeneticMaterial(GeneticMaterialOptions options)
    {
        ArrayList<Object> list  = new ArrayList<>(options.getOptions());
        Random rand = new Random();
        if(Simulation.seed != null)
            rand = Simulation.seed;
        return list.get(rand.nextInt(list.size()));
    }

    public static Collection createRandomDNA(Class<? extends Collection> ds,GeneticMaterialOptions options) throws IllegalAccessException, InstantiationException
    {
        Collection dna = ds.newInstance();
        while(dna.size() < DEFAULT_GENE_SIZE)
            dna.add(RandomUtil.getRandomGeneticMaterial(options));

        return dna;
    }

    public static Collection createRandomDNA(GeneticMaterialOptions options) throws InstantiationException, IllegalAccessException
    {
        return createRandomDNA(ArrayList.class,options);
    }
}
