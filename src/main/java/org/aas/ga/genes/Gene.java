/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.aas.ga.genes;

import java.util.List;

/**
 *
 * @author Adam
 */
public interface Gene<T>
{
    public void setLength(int len);
    public int getLength();
    public List<T> getDna();
    public void setDna(List<T> dna);
    public void mutate(double p);
    public Gene createRandom();
    public Gene copy();
    public void setSuppressed(boolean suppressed);
    public boolean isSuppressed();
    public boolean isDominant();

    public void setDominant(boolean dominant);

}
