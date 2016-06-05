/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.aas.ga.genes;

import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Adam
 */
public class DNAGene extends BaseGene<String>
{

    public DNAGene(List<String> dna, List<String> options, boolean suppressed, int length)
    {
        super(dna,Arrays.asList("A","C","T","G"), suppressed, length);
    }
    
}
