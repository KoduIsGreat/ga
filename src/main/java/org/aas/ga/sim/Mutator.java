package org.aas.ga.sim;


import org.aas.ga.chromo.Chromosome;
import org.aas.ga.genes.AlleleOptions;

import java.util.Collection;
import java.util.Random;

/**
 * Created by Adam on 11/16/2016.
 */
public interface Mutator
{
    public void mutate(Chromosome chromosome);
    public void refresh(Chromosome chromosome, double p);
    public double getP();
    public void setP(double p);
    public Random getSeed();
    public void setSeed(Random seed);
    public AlleleOptions getOptions();
    public void setOptions(AlleleOptions options);
    public Class<? extends Collection> getDnaDataStructure();
    public void setDnaDataStructure(Class<? extends Collection> clazz);

}
