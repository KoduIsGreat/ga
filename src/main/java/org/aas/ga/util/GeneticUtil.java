package org.aas.ga.util;


import org.aas.ga.algo.GeneticAlgorithm;
import org.aas.ga.chromo.Chromosome;
import java.util.*;


/**
 * Created by Adam on 6/12/2016.
 */
public class GeneticUtil
{
    public static Map<Chromosome,Double> computeFitnessCDF(List<Chromosome> survivors, GeneticAlgorithm ga)
    {

        Double min = ga.getWeakest().getFitness();
        Double range = Math.abs(ga.getFittest().getFitness() - min) ;
        if(range == 0)
        {
            Map<Chromosome,Double> defaultCdf = new LinkedHashMap<>();
            Random rand = new Random();
            for(Chromosome chromo : survivors)
            {
                defaultCdf.put(chromo,rand.nextDouble());
            }
            return defaultCdf;
        }
        else
        {
            Map<Chromosome,Double> cdf = new LinkedHashMap<>();
            for(Chromosome c : survivors)
            {
                double fit = c.getFitness();
                cdf.put(c,((Math.abs(fit-min))/range));
            }
            return cdf;
        }
    }

    public static Chromosome weightedChoice(Map<Chromosome,Double> survivorCdfMap)
    {
        Random rand = new Random();
        for(Chromosome chromo : survivorCdfMap.keySet()){
            if(rand.nextDouble() < survivorCdfMap.get(chromo))
                return chromo;
        }

        return choice(survivorCdfMap.keySet(),rand);
    }

    public static Chromosome choice(Collection<? extends Chromosome> coll, Random rand) {
        if (coll.size() == 0) {
            return null; // or throw IAE, if you prefer
        }

        int index = rand.nextInt(coll.size());
        if (coll instanceof List)
        { // optimization
            return ((List<? extends Chromosome>) coll).get(index);
        }
        else
        {
            Iterator<? extends Chromosome> iter = coll.iterator();
            for (int i = 0; i < index; i++)
            {
                iter.next();
            }
            return iter.next();
        }
    }
}
