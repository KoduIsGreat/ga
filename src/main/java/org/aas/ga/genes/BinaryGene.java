/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.aas.ga.genes;


import org.aas.ga.sim.Simulation;

import java.util.*;

/**
 *
 * @author Adam
 */
public class BinaryGene extends BaseGene<Integer>
{

    public BinaryGene(Collection<Integer> dna, int length, boolean suppressed, boolean dominant)
    {      
        super(dna,length,suppressed,dominant);
    }
    

    @Override
    public void mutate(double p)
    {

        Random rand = new Random();
        if(Simulation.seed !=null)
            rand = Simulation.seed;

        List<Integer> new_dna = new ArrayList();
        for(Integer bit : this.getDna())
        {
            if(rand.nextDouble() < p)
                 new_dna.add((bit == 1) ? 0 : 1);
            else
                 new_dna.add(bit);
        }
    }
}
