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
public class Base10Gene extends BaseGene<Integer>
{

    public Base10Gene(List<Integer> dna, boolean suppressed, int length)
    {
        super(dna, Arrays.asList(0,1,2,3,4,5,6,7,8,9), suppressed, length);
    }
    
}
