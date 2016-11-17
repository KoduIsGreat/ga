package org.aas.ga.examples.AsciiMatcher;

import org.aas.ga.genes.DefaultGeneticMaterialOptions;
import org.aas.ga.sim.Simulation;

/**
 * Created by Adam on 8/28/2016.
 */
public class AsciiMatching
{
    public static void main(String[]args)
    {
        String input = "In a hole in the ground there lived a hobbit. Not a nasty, dirty, wet hole, filled with the ends of worms and an oozy smell, nor yet a dry, bare, sandy hole with nothing in it to sit down on or to eat: it was a hobbit-hole and that means comfort.";
//        BaseMutator<BaseChromosome> mutator = new BaseMutator(.5,DefaultGeneticMaterialOptions.ALL_ASCII,new Random(15125L));
//        GeneFactory<BaseGene<String>> geneFactory = new GeneFactory(BaseGene.class,DefaultGeneticMaterialOptions.ALL_ASCII);
//        ChromosomeFactory<BaseChromosome> chromosomeFactory= new ChromosomeFactory<>(BaseChromosome.class,DefaultGeneticMaterialOptions.ALL_ASCII,geneFactory,new Random(15125L),input.length());
//        List<BaseChromosome> population = chromosomeFactory.create(200);
//        AsciiMatcherGA algo = new AsciiMatcherGA(input,population,mutator);
//        algo.setDoElitism(true);
//        algo.setInverseFitnessRanking(true);
//        algo.run();
        Simulation sim = new Simulation(DefaultGeneticMaterialOptions.ALL_ASCII,15125L,new AsciiMatcherGA(input));
        sim.setChromoLength(input.length());
        sim.init();
        sim.run();

    }
}
