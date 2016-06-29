/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.aas.ga.genes;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 *
 * @author Adam
 */
public class BinaryGene extends AbstractGene<Integer>
{

    public BinaryGene(int length, boolean suppressed,boolean dominant)
    {      
        super(Arrays.asList(0,1),length,suppressed,dominant);
    }
    
    public BinaryGene(int length)
    {
        super(Arrays.asList(0,1),length);
    }
    public BinaryGene()
    {
        super(Arrays.asList(0,1));
    }
    @Override
    public void mutate(double p)
    {
        Random rand = new Random();
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
