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
public class Base10Gene extends AbstractGene<Integer>
{

    public Base10Gene(int length, boolean suppressed,boolean dominant)
    {
        super(Arrays.asList(0,1,2,3,4,5,6,7,8,9),length, suppressed,dominant);
    }
 
    public Base10Gene(int length)
    {
        super(Arrays.asList(0,1,2,3,4,5,6,7,8,9),length);
    }
    
    public Base10Gene()
    {
        super(Arrays.asList(0,1,2,3,4,5,6,7,8,9));
    }
}
