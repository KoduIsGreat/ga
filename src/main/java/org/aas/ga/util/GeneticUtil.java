package org.aas.ga.util;

import org.aas.ga.algo.GeneticAlgorithm;
import org.aas.ga.chromo.Chromosome;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Adam on 6/12/2016.
 */
public class GeneticUtil {
    public List<Double> computeFitnessCDF(List<Chromosome> survivors ,GeneticAlgorithm ga){

        List<Double> fitness = new ArrayList();
        for(Chromosome c : ga.getPopulation())
        {
            ga.evaluateFitness(c);
            fitness.add(c.getFitness());
        }

        ga.sort();
        Double min = ga.getWeakest().getFitness();
        Double range = ga.getFittest().getFitness() - min;
        if(range == 0)
        {
            List<Double> defaultCdf = new ArrayList();
            double n = ga.getPopulation().size();
            for(int i = 1 ; i < n ; i ++)
                defaultCdf.add(i/n);
            return defaultCdf;
        }
        else
        {
            List<Double> cdf = fitness.stream().map(fit -> (fit - min) / range).collect(Collectors.toList());
            return cdf;
        }
    }
}
