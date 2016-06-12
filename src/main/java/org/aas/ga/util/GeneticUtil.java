package org.aas.ga.util;


import org.aas.ga.chromo.Chromosome;
import java.util.*;


/**
 * Created by Adam on 6/12/2016.
 */
public class GeneticUtil
{
    public static Map<Double,Chromosome> computeFitnessCDF(List<Chromosome> survivors )
    {
        Collections.sort(survivors);
        Double min = Collections.min(survivors).getFitness();
        Double range = Collections.max(survivors).getFitness() -min ;
        if(range == 0)
        {
            Map<Double,Chromosome> defaultCdf = new LinkedHashMap<>();
            for(Chromosome c : survivors)
            {
                double fit = c.getFitness();
                defaultCdf.put(((fit-min)/range),c);
            }
            return defaultCdf;
        }
        else
        {
            Map<Double,Chromosome> cdf = new LinkedHashMap<>();
            for(Chromosome c : survivors)
            {
                double fit = c.getFitness();
                cdf.put(((fit-min)/range),c);
            }
            return cdf;
        }
    }

    public static Chromosome weightedChoice(Map<Double,Chromosome> survivorCdfMap)
    {
        Random rand = new Random();
        for(Double cdf : survivorCdfMap.keySet()){
            if(rand.nextDouble() < cdf)
                return survivorCdfMap.get(cdf);
        }

        return survivorCdfMap.get(rand.nextInt(survivorCdfMap.size()));
    }
}
