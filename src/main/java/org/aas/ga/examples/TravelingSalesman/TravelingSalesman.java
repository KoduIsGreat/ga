package org.aas.ga.examples.TravelingSalesman;

import org.aas.ga.sim.Simulation;
import java.util.LinkedHashSet;

/**
 * Created by Adam on 8/29/2016.
 */
public class TravelingSalesman
{
    public static void main(String[] args)
    {
        Cities cities = new Cities();
        cities.setOptions(City.createRandomCities(20));
        Simulation sim = new Simulation(cities,15151L,new TravelingSalesmanGA());
        sim.setChromoLength(cities.getOptions().size());
        sim.setGeneDataStructure(LinkedHashSet.class);
        sim.init();
        sim.run();
    }
}
