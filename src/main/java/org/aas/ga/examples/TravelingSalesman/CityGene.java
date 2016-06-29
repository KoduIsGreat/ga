package org.aas.ga.examples.TravelingSalesman;

import org.aas.ga.genes.AbstractGene;
import org.aas.ga.genes.Gene;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Adam on 6/27/2016.
 */
public class CityGene extends AbstractGene<City> {

    protected CityGene(List<City>options, int length) {
        super(options, length);
    }

    public CityGene(List<City> options, City... data) {
        super(options, data);
    }

    protected CityGene(List<City> options, int length, boolean suppressed, boolean dominant) {
        super(options, length, suppressed, dominant);
    }

    protected CityGene(List<City>dna,List<City> options, int length, boolean suppressed, boolean dominant) {
        super(dna,options, length, suppressed, dominant);
    }

    protected CityGene(List<City> options) {
        super(options);
    }

    @Override
    public Gene createRandom()
    {
        List<City> choices = new ArrayList<>(GENETIC_MATERIAL_OPTIONS);
        List<City> dna = new ArrayList();
        while(dna.size() < this.getLength())
            dna.add(getRandomGeneticMaterial(choices));
        return new CityGene(dna,GENETIC_MATERIAL_OPTIONS,this.getLength(),this.isSuppressed(),this.isDominant());
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        for (City data : getDna())
            sb.append(data.id);
        return sb.toString();
    }
}
