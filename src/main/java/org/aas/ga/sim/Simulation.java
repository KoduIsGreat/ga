package org.aas.ga.sim;

import org.aas.ga.algo.GeneticAlgorithm;
import org.aas.ga.chromo.BaseChromosome;
import org.aas.ga.chromo.Chromosome;
import org.aas.ga.err.WeightsException;
import org.aas.ga.factory.ChromosomeFactory;
import org.aas.ga.factory.GeneFactory;
import org.aas.ga.genes.BaseGene;
import org.aas.ga.genes.Gene;
import org.aas.ga.genes.AlleleOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Created by Adam on 8/28/2016.
 */
public class Simulation implements Runnable{

    Logger LOG = LoggerFactory.getLogger(Simulation.class);
    public AlleleOptions options;
    public Random seed;
    public static final int DEFAULT_GENE_SIZE = 1;
    public static final int DEFAULT_CHROMO_SIZE =10;
    public static final int DEFAULT_POP_SIZE = 200;
    public static final double DEFAULT_MUTATION_RATE = .5;
    public static final Class<? extends Gene> DEFAULT_GENE_TYPE = BaseGene.class;
    public static final Class<? extends Chromosome> DEFAULT_CHROMO_TYPE = BaseChromosome.class;
    public static final Class<? extends Collection> DEFAULT_STORAGE_TYPE = ArrayList.class;


    private GeneticAlgorithm algorithm;
    private boolean running;
    private int popSize;

    private int geneLength;
    private int chromoLength;
    private double pMutate ;
    private Class<? extends Gene> gene;
    private Class<? extends Chromosome> chromosome;
    private Class<? extends Collection> dnaDataStructure;
    private Class<? extends Collection> geneDataStructure;
    private Class<? extends Collection> chromosomeDataStructure;
    private ChromosomeFactory chromoFactory;
    private Mutator mutator = new BaseMutator();
    public Simulation(){}

    public Simulation(AlleleOptions options, GeneticAlgorithm algo)
    {
        this(options,new Random().nextLong(),algo);
    }

    public Simulation (AlleleOptions options, Long seed, GeneticAlgorithm algorithm)
    {
        this.options = options;
        this.seed = new Random(seed);
        this.algorithm = algorithm;
        this.pMutate = DEFAULT_MUTATION_RATE;
        this.popSize = DEFAULT_POP_SIZE;
        this.geneLength = DEFAULT_GENE_SIZE;
        this.chromoLength = DEFAULT_CHROMO_SIZE;
        this.gene = DEFAULT_GENE_TYPE;
        this.chromosome = DEFAULT_CHROMO_TYPE;
        this.dnaDataStructure = DEFAULT_STORAGE_TYPE;
        this.geneDataStructure = DEFAULT_STORAGE_TYPE;
        this.chromosomeDataStructure = DEFAULT_STORAGE_TYPE;
    }

    private void buildFactories()
    {
        GeneFactory geneFactory = new GeneFactory(options,seed);
        chromoFactory = new ChromosomeFactory(geneFactory,chromoLength);

    }

    public void init()
    {
        buildFactories();

        mutator.setDnaDataStructure(dnaDataStructure);
        mutator.setOptions(options);
        mutator.setP(pMutate);
        mutator.setSeed(seed);

        this.algorithm.setPopulation(chromoFactory.create(popSize));
        this.algorithm.setMutator(mutator);
        this.algorithm.setDoElitism(true);
        this.algorithm.setInverseFitnessRanking(true);
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Simulation that = (Simulation) o;

        if (popSize != that.popSize) return false;
        if (geneLength != that.geneLength) return false;
        if (chromoLength != that.chromoLength) return false;
        if (!algorithm.equals(that.algorithm)) return false;
        if (!gene.equals(that.gene)) return false;
        if (!chromosome.equals(that.chromosome)) return false;
        return geneDataStructure.equals(that.geneDataStructure);
    }

    @Override
    public int hashCode() {
        int result = algorithm.hashCode();
        result = 31 * result + popSize;
        result = 31 * result + geneLength;
        result = 31 * result + chromoLength;
        result = 31 * result + gene.hashCode();
        result = 31 * result + chromosome.hashCode();
        result = 31 * result + geneDataStructure.hashCode();
        return result;
    }

    public Map<Integer,Chromosome> getOverallFitnessMap(){
        return this.algorithm.getOverallFitnessMap();
    }
    public double getpMutate() {
        return pMutate;
    }

    public void setpMutate(double pMutate) {
        this.pMutate = pMutate;
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

    public  AlleleOptions getOptions() {
        return options;
    }

    public  void setOptions(AlleleOptions options) {
        this.options = options;
    }

    public  Random getSeed() {
        return this.seed;
    }

    public  void setSeed(Random seed) {
        this.seed = seed;
    }

    public ChromosomeFactory getChromoFactory() {
        return chromoFactory;
    }

    public void setChromoFactory(ChromosomeFactory chromoFactory) {
        this.chromoFactory = chromoFactory;
    }

    public Mutator getMutator() {
        return mutator;
    }

    public void setMutator(Mutator mutator) {
        this.mutator = mutator;
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

    public Class<? extends Collection> getGeneDataStructure() {
        return geneDataStructure;
    }

    public void setGeneDataStructure(Class<? extends Collection> geneDataStructure)
    {
        this.geneDataStructure = geneDataStructure;
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
        try
        {
            algorithm.run();
        }
        catch (WeightsException e)
        {
            LOG.error(e.getMessage());
        }
        this.setRunning(false);
    }
}
