package org.aas.ga.chromo;

/**
 * Created by Adam on 8/26/2016.
 */
public enum DefaultChromosomeTypes {
    LIST_CHROMOSOME(ListChromosome.class,"ListChromosome"),
    SET_CHROMOSOME(SetChromosome.class,"SetChromosome");


    private Class<? extends Chromosome> chromosome;
    private String name;

    DefaultChromosomeTypes(Class<? extends Chromosome> chromosome,String name){
        this.chromosome = chromosome;
        this.name = name;
    }

    public Class<? extends Chromosome> getChromosome() {
        return chromosome;
    }

    public void setChromosome(Class<? extends Chromosome> chromosome) {
        this.chromosome = chromosome;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
