package org.aas.ga.sim;

import org.aas.ga.algo.GeneticAlgorithm;
import org.aas.ga.chromo.Chromosome;
import org.aas.ga.chromo.ListChromosome;
import org.aas.ga.genes.BaseGene;
import org.aas.ga.genes.Gene;
import org.aas.ga.genes.GeneticMaterialOptions;
import org.aas.ga.util.SimulationConfig;

import java.util.*;

/**
 * Created by Adam on 8/28/2016.
 */
public class Simulation implements Runnable{
    public static GeneticMaterialOptions options = null;
    public static Random seed;

    private GeneticAlgorithm algorithm;
    private boolean running;
    private int popSize;
    private int geneLength;
    private int chromoLength;
    private Class<? extends Gene> gene;
    private Class<? extends Chromosome> chromosome;

    public Simulation(GeneticMaterialOptions options,GeneticAlgorithm algo)
    {
        this(options,null,algo);
    }

    public Simulation (GeneticMaterialOptions options, Long seed, GeneticAlgorithm algorithm)
    {
        this.options = options;
        this.seed = new Random(seed);
        this.algorithm = algorithm;
        this.popSize = 200;
        this.geneLength = 1;
        this.chromoLength = 10;
        this.gene = BaseGene.class;
        this.chromosome = ListChromosome.class;

    }

    public void populateAsList()
    {
        algorithm.setPopulation(SimulationConfig.initPopulationAsList(gene,geneLength,chromoLength,popSize));
    }
    public void populateAsSet(){
        algorithm.setPopulation(SimulationConfig.initPopulationAsSet(gene,geneLength,chromoLength,popSize));
    }
    public GeneticAlgorithm getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(GeneticAlgorithm algorithm) {
        this.algorithm = algorithm;
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }
    public int getPopSize() {
        return popSize;
    }

    public void setPopSize(int popSize) {
        this.popSize = popSize;
    }

    public int getGeneLength() {
        return geneLength;
    }

    public void setGeneLength(int geneLength) {
        this.geneLength = geneLength;
    }

    public int getChromoLength() {
        return chromoLength;
    }

    public void setChromoLength(int chromoLength) {
        this.chromoLength = chromoLength;
    }

    public Class<? extends Gene> getGene() {
        return gene;
    }

    public void setGene(Class<? extends Gene> gene) {
        this.gene = gene;
    }

    public Class<? extends Chromosome> getChromosome() {
        return chromosome;
    }

    public void setChromosome(Class<? extends Chromosome> chromosome) {
        this.chromosome = chromosome;
    }

    @Override
    public void run()
    {
        this.setRunning(true);
        algorithm.run();
        this.setRunning(false);
    }
}
