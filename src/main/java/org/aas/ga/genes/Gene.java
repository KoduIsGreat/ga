/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.aas.ga.genes;

import java.util.Collection;

/**
 *
 * @author Adam
 */
public interface Gene<T>
{
     void setLength(int len);
     int getLength();
     Collection<T> getDna();
     void setDna(Collection<T> dna);
//     void mutate(double p);
     Gene copy();
     boolean isDominant();
     void setDominant(boolean dominant);


}
