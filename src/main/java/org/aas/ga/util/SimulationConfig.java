package org.aas.ga.util;

import org.aas.ga.chromo.Chromosome;
import org.aas.ga.chromo.ListChromosome;
import org.aas.ga.chromo.SetChromosome;
import org.aas.ga.factory.GeneFactory;
import org.aas.ga.genes.Gene;
import org.aas.ga.sim.Simulation;

import java.util.*;

/**
 * Created by Adam on 8/28/2016.
 */
public class SimulationConfig {

    public static List<Chromosome> initPopulation(Class<? extends Chromosome> chromosome, Class<? extends Collection> ds, Class<? extends Gene> gene, int genel, int chromol, int n) {
        List<Chromosome> population = new ArrayList<>();
        while(population.size()<n)
        {
            Chromosome chromo = null;
            Collection genes = null;
            try
            {
                chromo = chromosome.newInstance();
                genes = ds.newInstance();
            }
            catch (InstantiationException | IllegalAccessException e)
            {
                e.printStackTrace();
            }


            while(genes.size()<chromol)
                genes.add(GeneFactory.createGene(gene, Simulation.options,genel));
            chromo.setGenes(genes);
            population.add(chromo);
        }
        return population;
    }

    public static  List<Chromosome> initPopulationAsList( Class<? extends Gene> gene,int genel,int chromol ,int n){
        return initPopulation(ListChromosome.class,ArrayList.class,gene,genel,chromol,n);
    }

    public static List<Chromosome> initPopulationAsSet( Class<? extends Gene> gene,int genel,int chromol ,int n){
        return initPopulation(SetChromosome.class,LinkedHashSet.class,gene,genel,chromol,n);
    }
}
