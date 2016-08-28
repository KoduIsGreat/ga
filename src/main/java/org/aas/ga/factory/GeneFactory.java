package org.aas.ga.factory;

import org.aas.ga.genes.Gene;

import java.util.LinkedHashSet;
import java.util.Set;


/**
 * Created by Adam on 7/26/2016.
 */
public class GeneFactory<T> {

    protected Set<T> GENETIC_MATERIAL_OPTIONS ;
    private static final int DEFAULT_SIZE = 1;
    Class<? extends Gene> gene;


    private int geneLength ;

    public GeneFactory(Class<? extends Gene> gene)
    {
        this.gene = gene;
        geneLength = DEFAULT_SIZE;
    }
    public GeneFactory(Class<? extends Gene> gene, int geneLength){
        this.gene = gene;
        this.geneLength = geneLength;
    }

    public Gene createGene()
    {
        try
        {
            Gene newGene = gene.newInstance();
            if(GENETIC_MATERIAL_OPTIONS != null)
                newGene.setGENETIC_MATERIAL_OPTIONS(GENETIC_MATERIAL_OPTIONS);
            newGene.setLength(geneLength);
            return newGene.createRandom();
        }
        catch (InstantiationException e)
        {
            e.printStackTrace();
            return null;
        }
        catch (IllegalAccessException e)
        {
            e.printStackTrace();
            return null;
        }
    }

    public Class<? extends Gene> getGene() {
        return gene;
    }

    public void setGene(Class<? extends Gene> gene) {
        this.gene = gene;
    }

    public int getGeneLength() {
        return geneLength;
    }

    public void setGeneLength(int geneLength) {
        this.geneLength = geneLength;
    }

    public Set<T> getGENETIC_MATERIAL_OPTIONS() {
        return GENETIC_MATERIAL_OPTIONS;
    }

    public void setGENETIC_MATERIAL_OPTIONS(Set<T> GENETIC_MATERIAL_OPTIONS) {
        this.GENETIC_MATERIAL_OPTIONS = GENETIC_MATERIAL_OPTIONS;
    }

}
