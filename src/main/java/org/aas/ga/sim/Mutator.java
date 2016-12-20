package org.aas.ga.sim;

import org.aas.ga.chromo.Chromosome;


/**
 * Created by Adam on 11/16/2016.
 */
public interface Mutator<T extends Chromosome>
{
    public void mutate(T chromosome);
    public void refresh(T chromosome, double p);
}
