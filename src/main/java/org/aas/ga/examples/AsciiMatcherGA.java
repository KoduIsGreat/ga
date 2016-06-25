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

    private final List<String> target;

    public AsciiMatcherGA(List<Chromosome> pop, double absW, double relW, double p_mutate, double p_crossover, int gen, boolean elitist, int quit_after, int refresh_after, String[] target) {
        super(pop, absW, relW, p_mutate, p_crossover, gen, elitist, quit_after, refresh_after);
        this.target = Arrays.asList(target);
    }

    public AsciiMatcherGA(List<Chromosome> pop, int gen, double absW, double relW, double p_mutate, double p_crossover, String[] target) {
        super(pop, gen, absW, relW, p_mutate, p_crossover);
        this.target =Arrays.asList(target);
    }


    @Override
    public void evaluateFitness(Chromosome chromo)
    {
        Double fit = 0.0;
        List<String> s = new ArrayList<>();
        int i = 0;
        for (Gene gene : chromo.getGenes())
        {
            s.clear();
            s.addAll(gene.getDna());
            for(int j = 0; j < gene.getDna().size(); j++)
            {
                int geneFit = HammingDistance(target.get(i).getBytes(), s.get(j).getBytes());

                if(geneFit == 0 && !gene.isDominant())
                {
                    gene.setDominant(true);
                }
                i++;
                fit += geneFit;
            }
        }
        chromo.setFitness(fit);
    }

    private int HammingDistance(byte[] a, byte[] b)
    {
        int dist = a.length;
        for(int i =0 ; i < a.length ; i++) {
            if (a[i] == b[i])
                dist--;
        }

        return dist;
    }

    @Override
    public boolean shouldTerminate(){
        return this.getFittest().getFitness() == 0;
    }

    public static void main(String[] args)
    {
        String [] input = {"h","e","l","l","o"," "," ","w","o","r","l","d"};
        AsciiMatcherGA ga = new AsciiMatcherGA(ChromosomeFactory.createDefaultChromosomes(new AsciiGene(1),input.length,2000),50000,.5,.5,.5,.5,input);
        ga.setInverseFitnessRanking(true);
        ga.run();
    }


}
