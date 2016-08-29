package org.aas.ga.examples.TravelingSalesman;

import org.aas.ga.algo.AbstractGeneticAlgorithm;
import org.aas.ga.chromo.AbstractCollectionChromosome;
import org.aas.ga.chromo.Chromosome;
import org.aas.ga.chromo.SetChromosome;
import org.aas.ga.factory.GeneFactory;
import org.aas.ga.genes.Gene;
import org.aas.ga.sim.Simulation;

import java.util.*;

/**
 * Created by Adam on 6/28/2016.
 */
public class TravelingSalesmanGA<T extends AbstractCollectionChromosome> extends AbstractGeneticAlgorithm<T> {
    public TravelingSalesmanGA(){}
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
            City c1 = (City)genes.get(i-1).getDna().iterator().next();
            City c2 = (City)genes.get(i).getDna().iterator().next();
            fitness+=calculateDistance(c1.xCoordinate,c2.xCoordinate,c1.yCoordinate,c2.yCoordinate);
        }
        chromo.setFitness(fitness);
    }

    public static void main(String[] args)
    {
          Cities cities = new Cities();
          cities.setOptions(City.createRandomCities(20));
          Simulation sim = new Simulation(cities,15151L,new TravelingSalesmanGA<SetChromosome>());
          sim.setChromoLength(cities.getOptions().size());
          sim.populateAsSet();
          sim.getAlgorithm().setDoElitism(true);
          sim.getAlgorithm().setInverseFitnessRanking(true);
          sim.run();
//        GeneFactory geneFactory = new GeneFactory(CityGene.class);
//        geneFactory.setGENETIC_MATERIAL_OPTIONS(City.createRandomCities(20));
//        ChromosomeFactory chromosomeFactory = new ChromosomeFactory(SetChromosome.class,geneFactory,CityGene.getNumOfCities());
//        List<Chromosome>  population = chromosomeFactory.initPopulation(200);
//        TravelingSalesmanGA<SetChromosome> ga = new TravelingSalesmanGA(population);
//        ga.setInverseFitnessRanking(true);
//        ga.setDoElitism(true);
//        ga.run();
    }
}
