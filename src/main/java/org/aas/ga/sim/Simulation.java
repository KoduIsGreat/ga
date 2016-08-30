package org.aas.ga.sim;

import org.aas.ga.algo.GeneticAlgorithm;
import org.aas.ga.chromo.BaseChromosome;
import org.aas.ga.chromo.Chromosome;
import org.aas.ga.genes.BaseGene;
import org.aas.ga.genes.Gene;
import org.aas.ga.genes.GeneticMaterialOptions;
import org.aas.ga.util.RandomUtil;

import java.util.*;

/**
 * Created by Adam on 8/28/2016.
 */
public class Simulation implements Runnable{
    public static GeneticMaterialOptions options = null;
    public static Random seed;
    private static final int DEFAULT_GENE_SIZE = 1;
    private static final int DEFAULT_CHROMO_SIZE =10;
    private static final int DEFAULT_POP_SIZE = 200;
    private static final Class<? extends Gene> DEFAULT_GENE_TYPE = BaseGene.class;
    private static final Class<? extends Chromosome> DEFAULT_CHROMO_TYPE = BaseChromosome.class;
    private static final Class<? extends Collection> DEFAULT_STORAGE_TYPE = ArrayList.class;


    private GeneticAlgorithm algorithm;
    private boolean running;
    private int popSize;
    private int geneLength;
    private int chromoLength;
    private Class<? extends Gene> gene;
    private Class<? extends Chromosome> chromosome;
    private Class<? extends Collection> geneDataStructure;

    public Simulation(){}
    public Simulation(GeneticMaterialOptions options,GeneticAlgorithm algo)
    {
        this(options,null,algo);
    }

    public Simulation (GeneticMaterialOptions options, Long seed, GeneticAlgorithm algorithm)
    {
        this.options = options;
        this.seed = new Random(seed);
        this.algorithm = algorithm;
        this.popSize = DEFAULT_POP_SIZE;
        this.geneLength = DEFAULT_GENE_SIZE;
        this.chromoLength = DEFAULT_CHROMO_SIZE;
        this.gene = DEFAULT_GENE_TYPE;
        this.chromosome = DEFAULT_CHROMO_TYPE;
        this.geneDataStructure = DEFAULT_STORAGE_TYPE;
    }

    private List<Chromosome> initPopulation()
    {
        List<Chromosome> population = new ArrayList<>();
        while(population.size()<popSize)
        {
            Chromosome chromo = null;
            Collection<Gene> genes = null;

            try
            {
                chromo = chromosome.newInstance();
                genes = (Collection<Gene>) geneDataStructure.newInstance();
            }
            catch (InstantiationException | IllegalAccessException e)
            {
                e.printStackTrace();
            }


            while(genes.size()<chromoLength)
                genes.add(createGene());
            chromo.setGenes(genes);
            population.add(chromo);
        }
        return population;
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

    public void init()
    {
        this.algorithm.setPopulation(initPopulation());
        this.algorithm.setDoElitism(true);
        this.algorithm.setInverseFitnessRanking(true);
    }



    private  Gene createGene()
    {
        try
        {
            Gene newGene = gene.newInstance();
            newGene.setLength(geneLength);

            Collection dna = geneDataStructure.newInstance();
            while(dna.size() < DEFAULT_GENE_SIZE)
                dna.add(RandomUtil.getRandomGeneticMaterial(options,seed));

            newGene.setDna(dna);
            return newGene;
        }
        catch (InstantiationException  | IllegalAccessException  e)
        {
            e.printStackTrace();
            return null;
        }
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
