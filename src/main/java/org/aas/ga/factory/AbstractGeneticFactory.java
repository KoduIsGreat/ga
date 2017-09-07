package org.aas.ga.factory;

import org.aas.ga.genes.AlleleOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

/**
 * Created by Adam on 11/17/2016.
 */
abstract class AbstractGeneticFactory<T> implements Factory<T>
{

    Logger LOG = LoggerFactory.getLogger(AbstractGeneticFactory.class);
    static final int DEFAULT_SIZE = 1;
    static final Class<? extends Collection> DEFAULT_STORAGE_TYPE = ArrayList.class;
    Class<T> clazz;
    Class<? extends Collection> phenotypeDataStructure;
    Class<? extends Collection> phenotypeCollectionDataStructure;
    Random seed;
    int length;
    protected AlleleOptions options;
    AbstractGeneticFactory(){}
    AbstractGeneticFactory(Class<T> clazz, Class<? extends Collection>phenotypeDataStructure, Class<? extends Collection>phenotypeCollectionDataStructure, AlleleOptions options, Random seed, int length)
    {
        this.clazz = clazz;
        this.options = options;
        this.length = length;
        this.phenotypeDataStructure = phenotypeDataStructure;
        this.phenotypeCollectionDataStructure = phenotypeCollectionDataStructure;
        this.seed = seed;
    }
    public abstract T create()throws InstantiationException,IllegalAccessException;

//    public T[] create(int size)
//    {
//        try
//        {
//            T[] coll =
//            while(coll.size()<size)
//                coll.add(create());
//            return coll;
//        }
//        catch (InstantiationException |IllegalAccessException e)
//        {
//            LOG.error(e.getMessage());
//            return null;
//        }
//    }
}
