package org.aas.ga.sim;


import org.aas.ga.chromo.Chromosome;

/**
 * Created by Adam on 11/16/2016.
 */
public interface Mutator
{
    public void mutate(Chromosome chromosome);
    public void refresh(Chromosome chromosome, double p);
}
