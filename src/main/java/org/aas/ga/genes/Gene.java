/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.aas.ga.genes;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Adam
 */
public interface Gene<T>
{
     void setLength(int len);
     int getLength();
     List<T> getDna();
     void setDna(List<T> dna);
     void mutate(double p);
     Gene createRandom();
     Gene createRandom(List<T> options);
     Gene copy();
     void setSuppressed(boolean suppressed);
     boolean isSuppressed();
     boolean isDominant();
     void setDominant(boolean dominant);
     Set<T> getGENETIC_MATERIAL_OPTIONS();
     void setGENETIC_MATERIAL_OPTIONS(Collection<T> options);

}
