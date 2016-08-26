package org.aas.ga.genes;

import org.aas.ga.examples.TravelingSalesman.CityGene;

/**
 * Created by Adam on 8/25/2016.
 */
public enum DefaultGeneTypes {
    ASCII_GENE(AsciiGene.class,"AsciiGene"),
    BASE10_GENE(Base10Gene.class,"Base10Gene"),
    BINARY_GENE(BinaryGene.class,"BinaryGene"),
    DNA_GENE(DNAGene.class,"DNAGene"),
    CITY_GENE(CityGene.class,"CityGene");

    private Class<? extends Gene> gene;
    private String name;
    DefaultGeneTypes(Class<? extends Gene> clazz, String geneName){
        gene = clazz;
        name = geneName;
    }
    public Class<? extends Gene> getGeneClass(){ return gene;}
    public String getGeneName(){return name;}
}
