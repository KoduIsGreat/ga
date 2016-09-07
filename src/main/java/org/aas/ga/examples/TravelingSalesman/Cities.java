package org.aas.ga.examples.TravelingSalesman;

import org.aas.ga.genes.GeneticMaterialOptions;

import java.util.Collection;

/**
 * Created by Adam on 8/28/2016.
 */
public class Cities implements GeneticMaterialOptions
{
    public Cities(){}
    private Collection<City> options ;

    @Override
    public Collection<City> getOptions() {
        return options;
    }

    @Override
    public void setOptions(Collection options) {
        this.options = options;

    }
}
