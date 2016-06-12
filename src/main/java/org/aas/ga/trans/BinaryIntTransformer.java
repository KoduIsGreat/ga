/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.aas.ga.trans;

import org.aas.ga.genes.BinaryGene;
import org.aas.ga.genes.Gene;

/**
 *
 * @author Adam
 */
public class BinaryIntTransformer extends BaseTransformer<Integer>
{
    public BinaryIntTransformer(){}
    @Override
    public Integer transformGene(Gene gene)
    {
        BinaryGene bg = (BinaryGene) gene;
        int val =0;
        for (Integer i : bg.getDna())
            val += Math.pow(2.0*bg.getDna().get(i), bg.getDna().size()-i);
        return val;
    }
    
}
