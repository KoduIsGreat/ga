package org.aas.ga.examples.TravelingSalesman;

import org.aas.ga.algo.AbstractGeneticAlgorithm;
import org.aas.ga.chromo.AbstractCollectionChromosome;
import org.aas.ga.chromo.Chromosome;
import org.aas.ga.chromo.ChromosomeFactory;
import org.aas.ga.chromo.SetChromosome;
import org.aas.ga.genes.Gene;

import java.util.*;

/**
 * Created by Adam on 6/28/2016.
 */
public class TravelingSalesmanGA<T extends AbstractCollectionChromosome> extends AbstractGeneticAlgorithm<T> {

    public TravelingSalesmanGA(List<T> pop){
        super(pop);
    }
    private Double calculateDistance(double x1, double x2, double y1, double y2)
    {
        return Math.sqrt(Math.pow(x2-x1,2)+Math.pow(y2-y1,2));
    }
    @Override
    public void evaluateFitness(Chromosome chromo) {
        Double fitness =0.0;
        List<Gene> genes = new ArrayList<>(chromo.getGenes());

        for(int i=1; i<genes.size(); i ++)
        {
            City c1 = (City)genes.get(i-1).getDna().get(0);
            City c2 = (City)genes.get(i).getDna().get(0);
            fitness+=calculateDistance(c1.xCoordinate,c2.xCoordinate,c1.yCoordinate,c2.yCoordinate);
        }
        chromo.setFitness(fitness);
    }

    public static void main(String[] args)
    {
        Set<City> cities = City.createRandomCities(20);
        List<Chromosome> population = ChromosomeFactory.createSetChromosomes(new CityGene(new ArrayList<>(cities),1),cities.size(),200);
        TravelingSalesmanGA<SetChromosome> ga = new TravelingSalesmanGA(population);
        ga.setInverseFitnessRanking(true);
        ga.setDoElitism(true);
        ga.run();
    }
}
