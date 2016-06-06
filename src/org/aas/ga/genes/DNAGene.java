/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.aas.ga.genes;

import java.util.Arrays;


/**
 *
 * @author Adam
 */
public class DNAGene extends AbstractGene<String> implements Gene
{

    public DNAGene(int length, boolean suppressed)
    {
        super(Arrays.asList("A","C","T","G"),length, suppressed);
    }
    
    public DNAGene(int length)
    {
        super(Arrays.asList("A","C","T","G"),length);
    }
    
    public DNAGene()
    {
        super(Arrays.asList("A","C","T","G"));
    }
}
