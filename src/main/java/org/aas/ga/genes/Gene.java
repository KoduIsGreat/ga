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
     T getDna();
     void setDna(T dna);
     Gene copy();
     boolean isDominant();
     void setDominant(boolean dominant);

}
