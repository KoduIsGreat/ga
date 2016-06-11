/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.aas.ga.trans;

import org.aas.ga.genes.Base10Gene;
import org.aas.ga.genes.Gene;

/**
 *
 * @author Adam
 */
public class Base10IntTransformer extends BaseTransformer<Integer>
{

    @Override
    public Integer transformGene(Gene gene)
    {
        Base10Gene b10g = (Base10Gene)gene;
        String s ="";
        for(Integer i : b10g.getDna())
        {
            s= s.concat(i.toString());
        }
        return Integer.parseInt(s);
    }
    
}
