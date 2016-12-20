package org.aas.ga.sim;

import org.aas.ga.algo.GeneticAlgorithm;
import org.aas.ga.chromo.BaseChromosome;
import org.aas.ga.chromo.Chromosome;
import org.aas.ga.factory.ChromosomeFactory;
import org.aas.ga.factory.GeneFactory;
import org.aas.ga.genes.BaseGene;
import org.aas.ga.genes.Gene;
import org.aas.ga.genes.GeneticMaterialOptions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

/**
 * Created by Adam on 8/28/2016.
 */
public class Simulation implements Runnable{
    public static GeneticMaterialOptions options;
    public static Random seed;
    private static final int DEFAULT_GENE_SIZE = 1;
    private static final int DEFAULT_CHROMO_SIZE =10;
    private static final int DEFAULT_POP_SIZE = 200;
    private static final double DEFAULT_MUTATION_RATE = .5;
    private static final Class<? extends Gene> DEFAULT_GENE_TYPE = BaseGene.class;
    private static final Class<? extends Chromosome> DEFAULT_CHROMO_TYPE = BaseChromosome.class;
    private static final Class<? extends Collection> DEFAULT_STORAGE_TYPE = ArrayList.class;


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
    private ChromosomeFactory<? extends Chromosome> chromoFactory;
    private BaseMutator<? extends Chromosome> mutator;
    public Simulation(){}

    public Simulation(GeneticMaterialOptions options,GeneticAlgorithm algo)
    {
        this(options,new Random().nextLong(),algo);
    }

    public Simulation (GeneticMaterialOptions options, Long seed, GeneticAlgorithm algorithm)
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

    private void  build()
    {
        GeneFactory<? extends Gene> geneFactory = new GeneFactory<Gene>(gene,dnaDataStructure,geneDataStructure,options,seed,geneLength);
        chromoFactory = new ChromosomeFactory<Chromosome>(chromosome,geneDataStructure,chromosomeDataStructure,options,geneFactory,seed,chromoLength);
        mutator = new BaseMutator<>(pMutate,options,geneDataStructure,seed);
    }

    public void init()
    {
        build();
        this.algorithm.setPopulation((List) chromoFactory.create(popSize));
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

    public static GeneticMaterialOptions getOptions() {
        return options;
    }

    public static void setOptions(GeneticMaterialOptions options) {
        Simulation.options = options;
    }

    public static Random getSeed() {
        return seed;
    }

    public static void setSeed(Random seed) {
        Simulation.seed = seed;
    }

    public ChromosomeFactory<? extends Chromosome> getChromoFactory() {
        return chromoFactory;
    }

    public void setChromoFactory(ChromosomeFactory<? extends Chromosome> chromoFactory) {
        this.chromoFactory = chromoFactory;
    }

    public BaseMutator<? extends Chromosome> getMutator() {
        return mutator;
    }

    public void setMutator(BaseMutator<? extends Chromosome> mutator) {
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

    public void setGeneDataStructure(Class<? extends Collection> geneDataStructure) {
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
        algorithm.run();
        this.setRunning(false);
    }
}
