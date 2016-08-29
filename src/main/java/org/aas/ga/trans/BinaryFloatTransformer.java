/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.aas.ga.trans;


import org.aas.ga.genes.BinaryGene;
import org.aas.ga.genes.Gene;

import java.util.Collection;

/**
 *
 * @author Adam
 */
public class BinaryFloatTransformer extends AbstractTransformer<Double>
{
    private int sig_len;
    private boolean signed;

    public BinaryFloatTransformer(int len,boolean signed){
        sig_len = len;
        this.signed = signed;
    }

    @Override
    public Double transformGene(Gene gene)
    {
//       BinaryGene bg = (BinaryGene) gene;
//       int sign =1;
//       int base_idx_start;
//       if(this.signed)
//       {
//          sign = (bg.getDna().get(0) == 0) ? 1 : -1;
//          base_idx_start = 1;
//       }
//       else
//       {
//          base_idx_start = 0;
//       }
//
//       int base =0;
//       for(int i =base_idx_start; i < this.sig_len ; i++ )
//           base += Math.pow(2.0*bg.getDna().get(i), this.sig_len - i);
//
//       int exp_sign = (bg.getDna().get(1+this.sig_len) == 0) ? 1 : -1;
//       int exp = 0;
//       for(int j = this.sig_len+2; j < bg.getDna().size() ; j++)
//           exp += Math.pow(2.0*bg.getDna().get(j), bg.getDna().size()-j);
//
//       return Math.pow(sign*base, exp_sign*exp);
           return 0.0;
    }
    
}
