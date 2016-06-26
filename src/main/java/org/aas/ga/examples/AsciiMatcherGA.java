package org.aas.ga.examples;

import org.aas.ga.algo.AbstractGeneticAlgorithm;
import org.aas.ga.chromo.Chromosome;
import org.aas.ga.chromo.ChromosomeFactory;
import org.aas.ga.genes.AsciiGene;
import org.aas.ga.genes.Gene;

import java.util.ArrayList;
import java.util.Arrays;

import java.util.List;

/**
 * Created by Adam on 6/16/2016.
 */
public class AsciiMatcherGA extends AbstractGeneticAlgorithm {

    private final String target;

    public AsciiMatcherGA(List<Chromosome> pop, double absW, double relW, double p_mutate, double p_crossover, int gen, boolean elitist, int quit_after, int refresh_after, String target) {
        super(pop, absW, relW, p_mutate, p_crossover, gen, elitist, quit_after, refresh_after);
        this.target = target;
    }

    public AsciiMatcherGA(List<Chromosome> pop, int gen, double absW, double relW, double p_mutate, double p_crossover, String target) {
        super(pop, gen, absW, relW, p_mutate, p_crossover);
        this.target =target;
    }
    public AsciiMatcherGA(List<Chromosome>pop,String target){
        super(pop,50000,.5,.5,.5,.5);
        this.target = target;
    }
    public AsciiMatcherGA(String target){this.target = target;}

    @Override
    public void evaluateFitness(Chromosome chromo)
    {
        Double fit = 0.0;
        String stringChromo = chromo.toString();
        for(int k =0; k<stringChromo.length(); k ++)
        {
            int geneFit = HammingDistance((byte)stringChromo.charAt(k),(byte)target.charAt(k));
            if(geneFit == 0 && !chromo.getGenes().get(k).isDominant())
            {
                chromo.getGenes().get(k).setDominant(true);
            }
            fit += geneFit;
        }
        chromo.setFitness(fit);
    }

    private int HammingDistance(byte a, byte b)
    {
        int dist = 1;
        if (a == b)
            dist--;
        return dist;
    }

    @Override
    public boolean shouldTerminate(){
        return this.getFittest().getFitness() == 0;
    }

    public static void main(String[] args)
    {
        String input = "In a hole in the ground there lived a hobbit. Not a nasty, dirty, wet hole, filled with the ends of worms and an oozy smell, nor yet a dry, bare, sandy hole with nothing in it to sit down on or to eat: it was a hobbit-hole and that means comfort.";
        AsciiMatcherGA ga = new AsciiMatcherGA(ChromosomeFactory.createDefaultChromosomes(new AsciiGene(1),input.length(),200),50000,.5,.4,.6,.5,input);
        ga.setInverseFitnessRanking(true);
        ga.run();
    }


}
