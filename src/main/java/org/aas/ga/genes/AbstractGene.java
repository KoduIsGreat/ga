/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.aas.ga.genes;

import java.util.*;


/**
 *
 * @author Adam
 * @param <T>
 */
public abstract class AbstractGene<T> implements Gene<T>
{

    private GeneticMaterialOptions goptions;
    protected Set<T> GENETIC_MATERIAL_OPTIONS = new LinkedHashSet<>();
    private List<T> dna = new ArrayList<>();
    private boolean suppressed;
    private boolean dominant;
    private int length;

    protected AbstractGene(){}
    protected AbstractGene(List<T>options,int length)
    {
        this.GENETIC_MATERIAL_OPTIONS.addAll(options);
        this.length = length;
        this.suppressed =false;
        this.dominant = false;
    }

    public AbstractGene(List<T>options, T... data){
        this.dna = Arrays.asList(data);
        this.GENETIC_MATERIAL_OPTIONS.addAll(options);
        this.suppressed = false;
        this.dominant = false;
    }
    
    protected AbstractGene(Set<T>options,int length, boolean suppressed,boolean dominant)
    {
        this.GENETIC_MATERIAL_OPTIONS = options;
        this.length = length;
        this.suppressed =suppressed;
        this.dominant = dominant;
    }
    
    protected AbstractGene(List<T> options, List<T> dna, int length, boolean suppressed, boolean dominant)
    {
        this.GENETIC_MATERIAL_OPTIONS.addAll(options);
        this.dna =dna;
        this.length = length;
        this.suppressed = suppressed;
        this.dominant = dominant;
    }
  
    protected AbstractGene(List<T> options)
    {
        this.GENETIC_MATERIAL_OPTIONS.addAll(options);
    }
    protected AbstractGene(List<T> dna, int length, boolean suppressed, boolean dominant){
        this.dna = dna;
        this.length = length;
        this.suppressed = suppressed;
        this.dominant = dominant;
    }
    public T getRandomGeneticMaterial()
    {
        ArrayList<T> list  = new ArrayList<>(goptions.getOptions());
        Random rand = new Random();
        return list.get(rand.nextInt(list.size()));
    }

    public T getRandomGeneticMaterial(List<T> GENETIC_MATERIAL_OPTIONS)
    {
        Random rand = new Random();
        return GENETIC_MATERIAL_OPTIONS.remove(rand.nextInt(GENETIC_MATERIAL_OPTIONS.size()));
    }

    @Override
    public int hashCode()
    {
        int hash = 5;
        hash = 67 * hash + (this.suppressed ? 1 : 0);
        hash = 67 * hash + (this.dominant ? 1 : 0);
        hash = (67 * hash) + Objects.hashCode(this.dna);
        return hash;
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        for (T data : dna)
            sb.append(data.toString());
        return sb.toString();
    }
    
    @Override
    public boolean equals(Object obj)
    {
        if (obj == null)
        {
            return false;
        }
        if (getClass() != obj.getClass())
        {
            return false;
        }
        final AbstractGene<?> other = (AbstractGene<?>) obj;
        if (this.suppressed != other.suppressed)
        {
            return false;
        }
        if(this.dominant != other.dominant)
        {
            return false;
        }
        if (this.length != other.length)
        {
            return false;
        }
        return dna.hashCode() == other.dna.hashCode();
    }

    @Override
    public Gene createRandom()
    {
        List<T> dna = new ArrayList();
        while(dna.size() < this.length)
            dna.add(getRandomGeneticMaterial());
        this.dna = dna;
        return copy();
    }



    @Override
    public Gene createRandom(List<T> options)
    {
        List<T> dna = new ArrayList();
        while(dna.size() < this.length)
            dna.add(getRandomGeneticMaterial(options));
        this.dna = dna;
        return copy();
    }
    @Override
    public void mutate(double p)
    {
        Random rand = new Random();
        List<T> new_dna = new ArrayList();
        for(T strand : dna)
        {
            if(rand.nextDouble() < p && !this.dominant)
            {
                new_dna.add(getRandomGeneticMaterial());
            }
            else
            {
                new_dna.add(strand);
            }
        }
        this.dna = new_dna;
    }

    @Override
    public Gene copy()
    {
        return new AbstractGene(new ArrayList(this.GENETIC_MATERIAL_OPTIONS),this.dna,this.length,this.suppressed,this.dominant) {};
    }
    @Override
    public List<T> getDna()
    {
        return this.dna;
    }

    @Override
    public void setDna(List dna){
        this.dna = dna;
    }

    @Override
    public boolean isSuppressed()
    {
        return suppressed;
    }
    @Override
    public void setSuppressed(boolean suppressed)
    {
        this.suppressed = suppressed;
    }
    @Override
    public int getLength()
    {
        return length;
    }
    @Override
    public void setLength(int length)
    {
        this.length = length;
    }
    @Override
    public boolean isDominant() {
        return dominant;
    }
    @Override
    public void setDominant(boolean dominant) {
        this.dominant = dominant;
    }

    @Override
    public Set<T> getGENETIC_MATERIAL_OPTIONS(){
        return this.GENETIC_MATERIAL_OPTIONS;
    }
    @Override
    public void setGENETIC_MATERIAL_OPTIONS(Collection<T> options){
        GENETIC_MATERIAL_OPTIONS.clear();
        GENETIC_MATERIAL_OPTIONS.addAll(options);
    }
}

