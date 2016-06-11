/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.aas.ga.genes;

/**
 *
 * @author Adam
 */
public interface Gene
{
    public void setLength(int len);
    public int getLength();
    public void mutate(double p);
    public Gene createRandom();
    public Gene copy();

}