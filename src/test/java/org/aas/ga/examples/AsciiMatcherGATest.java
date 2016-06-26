package org.aas.ga.examples;

import factory.EntityFactory;
import org.aas.ga.chromo.Chromosome;
import org.aas.ga.chromo.DefaultChromosome;
import org.aas.ga.genes.AsciiGene;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Adam on 6/25/2016.
 */
public class AsciiMatcherGATest {

    @Test
    public void evaluateFitness() throws Exception
    {
        String target = "Hello, World!";
        AsciiMatcherGA ga = new AsciiMatcherGA(target);
        Chromosome chromosome = EntityFactory.createAsciiChromosome("Hello, World!");
        ga.evaluateFitness(chromosome);
        double val  = chromosome.getFitness();
        assertTrue(val==0.0);



    }

}