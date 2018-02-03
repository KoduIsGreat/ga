package org.aas.ga.factory;

import org.aas.ga.chromo.BaseChromosome;
import org.aas.ga.chromo.Chromosome;
import org.aas.ga.genes.Gene;
import org.aas.ga.genes.AlleleOptions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

/**
 * Created by Adam on 11/16/2016.
 */
public class ChromosomeFactory {
    private GeneFactory geneFactory;

    int length ;
    public ChromosomeFactory(GeneFactory geneFactory,int length)
    {

        this.geneFactory = geneFactory;
        this.length = length;
    }


    public List<Chromosome> create(int n){
        ArrayList<Chromosome> list = new ArrayList<>(n);
        for(int i = 0 ; i < n ; i++)
            list.add(create());
        return list;
    }
    public BaseChromosome create()
    {
        BaseChromosome chromo = new BaseChromosome();
        Gene [] genes = new Gene[length];
        for(int i = 0; i < length ; i++){

            genes[i] = geneFactory.create();
        }

        chromo.setGenes(genes);
        return chromo;
    }
}
