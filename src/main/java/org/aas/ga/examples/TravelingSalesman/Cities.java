package org.aas.ga.examples.TravelingSalesman;

import org.aas.ga.genes.AlleleOptions;

import java.util.Collection;

/**
 * Created by Adam on 8/28/2016.
 */
public class Cities implements AlleleOptions
{
    private Collection<City> options ;

    public Cities(){}

    public Cities(Collection<City> options)
    {
        this.options=options;
    }


    @Override
    public Collection<City> getOptions() {
        return options;
    }

    @Override
    public void setOptions(Collection options) {
        this.options = options;

    }
}
