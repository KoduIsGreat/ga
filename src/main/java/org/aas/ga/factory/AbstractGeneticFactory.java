package org.aas.ga.factory;

import org.aas.ga.genes.Gene;
import org.aas.ga.genes.GeneticMaterialOptions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

/**
 * Created by Adam on 11/17/2016.
 */
public abstract class AbstractGeneticFactory<T> {
    protected static final int DEFAULT_SIZE = 1;
    protected static final Class<? extends Collection> DEFAULT_STORAGE_TYPE = ArrayList.class;
    protected Class<T> clazz;
    protected Class<? extends Collection> dataStructure;
    protected Random seed;
    protected int length;
    protected GeneticMaterialOptions options;
    public AbstractGeneticFactory(){}
    public AbstractGeneticFactory(Class<T> clazz,Class<? extends Collection>dataStructure,GeneticMaterialOptions options,Random seed,int length)
    {
        this.clazz = clazz;
        this.options = options;
        this.length = length;
        this.dataStructure = dataStructure;
        this.seed = seed;
    }
     public abstract T create();
}
