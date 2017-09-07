package org.aas.ga.examples.AsciiMatcher;

import org.aas.ga.algo.AbstractGeneticAlgorithm;
import org.aas.ga.chromo.BaseChromosome;
import org.aas.ga.chromo.Chromosome;
import org.aas.ga.genes.Gene;
import org.aas.ga.sim.Mutator;

import java.util.List;

/**
 * Created by Adam on 6/16/2016.
 */
public class AsciiMatcherGA extends AbstractGeneticAlgorithm {

    private final String target;

    public AsciiMatcherGA(List<Chromosome> pop, double absW, double relW, Mutator mutator, double p_crossover, int gen, boolean elitist, boolean inverseFitRanking, int quit_after, int refresh_after, String target) {
        super(pop, absW, relW, mutator, p_crossover, gen, elitist,inverseFitRanking, quit_after, refresh_after);
        this.target = target;
    }
    public AsciiMatcherGA(String target){this(null,.5,.5,null,.5,50000,false,true,5000,2500,target);}

    @Override
    public void evaluateFitness(Chromosome chromo)
    {
        Double fit = 0.0;
        String stringChromo = chromo.toString();
        for(int k =0; k<stringChromo.length(); k ++)
        {
            int geneFit = HammingDistance((byte)stringChromo.charAt(k),(byte)target.charAt(k));
            Gene[] genes = chromo.getGenes();
            if(geneFit == 0 && !(genes[k].isDominant()))
            {
                genes[k].setDominant(true);
            }
            fit += geneFit;
        }
        chromo.setFitness(fit);
    }


    private int HammingDistance(byte a, byte b)
    {
        if (a == b)
            return 0;
        return 1;
    }

    @Override
    public boolean shouldTerminate(){return this.getFittest().getFitness() == 0;}

}
