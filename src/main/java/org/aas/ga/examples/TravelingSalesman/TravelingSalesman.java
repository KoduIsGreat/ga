package org.aas.ga.examples.TravelingSalesman;

import org.aas.ga.sim.SetMutator;
import org.aas.ga.sim.Simulation;
import java.util.LinkedHashSet;

/**
 * Created by Adam on 8/29/2016.
 */
public class TravelingSalesman
{
    public static void main(String[] args)
    {
        Cities cities = new Cities(20);
        Simulation sim = new Simulation(cities,199991L,new TravelingSalesmanGA());
        sim.setChromoLength(cities.getOptions().size());
        sim.setGeneDataStructure(LinkedHashSet.class);
        sim.setMutator(new SetMutator());
        sim.init();
        sim.run();

    }
}
