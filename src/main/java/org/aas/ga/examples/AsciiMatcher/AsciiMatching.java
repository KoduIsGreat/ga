package org.aas.ga.examples.AsciiMatcher;

import org.aas.ga.genes.DefaultAlleleOptions;
import org.aas.ga.sim.BaseMutator;
import org.aas.ga.sim.Simulation;

/**
 * Created by Adam on 8/28/2016.
 */
public class AsciiMatching
{
    public static void main(String[]args)
    {
        String input = "In a hole in the ground there lived a hobbit. Not a nasty, dirty, wet hole, filled with the ends of worms and an oozy smell, nor yet a dry, bare, sandy hole with nothing in it to sit down on or to eat: it was a hobbit-hole and that means comfort.";
        AsciiMatcherGA ga = new AsciiMatcherGA(input);
        Simulation sim = new Simulation(DefaultAlleleOptions.ALL_ASCII,15125L,ga);
        sim.setChromoLength(input.length());
        sim.setMutator(new BaseMutator());
        sim.init();
        sim.run();

    }
}
