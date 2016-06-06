/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.aas.ga.genes;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Adam
 */
public class BinaryGene extends AbstractGene<Integer> implements Gene
{

    public BinaryGene(int length, boolean suppressed) throws InvalidGeneticOperatorException
    {      
        super(Arrays.asList(0,1),length,suppressed);
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
        List<Integer> new_dna = new ArrayList<>();
        for(Integer bit : this.getDna())
        {
            Integer new_bit = (bit == 1) ? 0 : 1;
            new_dna.add(new_bit);
        }
    }
}
