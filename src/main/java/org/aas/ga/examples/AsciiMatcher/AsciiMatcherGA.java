package org.aas.ga.examples.AsciiMatcher;

import org.aas.ga.algo.AbstractGeneticAlgorithm;
import org.aas.ga.chromo.AbstractCollectionChromosome;
import org.aas.ga.chromo.Chromosome;
import org.aas.ga.genes.Gene;

import java.util.List;

/**
 * Created by Adam on 6/16/2016.
 */
public class AsciiMatcherGA<T extends AbstractCollectionChromosome> extends AbstractGeneticAlgorithm<T> {

    private final String target;

    public AsciiMatcherGA(List<T> pop, double absW, double relW, double p_mutate, double p_crossover, int gen, boolean elitist,boolean inverseFitRanking, int quit_after, int refresh_after, String target) {
        super(pop, absW, relW, p_mutate, p_crossover, gen, elitist,inverseFitRanking, quit_after, refresh_after);
        this.target = target;
    }

    public AsciiMatcherGA(List<T> pop, int gen, double absW, double relW, double p_mutate, double p_crossover, String target) {
        this(pop,absW,relW,p_mutate,p_crossover,gen,false,true,5000,2500,target);

    }
    public AsciiMatcherGA(List<T>pop,String target){
        this(pop,.5,.5,.5,.5,50000,false,true,5000,2500,target);

    }
    public AsciiMatcherGA(String target){this(null,.5,.5,.5,.5,50000,false,true,5000,2500,target);}

    @Override
    public void evaluateFitness(Chromosome chromo)
    {
        Double fit = 0.0;
        String stringChromo = chromo.toString();
        for(int k =0; k<stringChromo.length(); k ++)
        {
            int geneFit = HammingDistance((byte)stringChromo.charAt(k),(byte)target.charAt(k));
            List<Gene> genes = (List)chromo.getGenes();
            if(geneFit == 0 && !(genes.get(k).isDominant()))
            {
                genes.get(k).setDominant(true);
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
    public boolean shouldTerminate(){return this.getFittest().getFitness() == 0;}

}
