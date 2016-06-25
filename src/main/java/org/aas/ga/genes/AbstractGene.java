/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.aas.ga.genes;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Random;



/**
 *
 * @author Adam
 * @param <T>
 */
public abstract class AbstractGene<T> implements Gene
{
    protected List<T> GENETIC_MATERIAL_OPTIONS = new ArrayList();

    private boolean suppressed;
    private int length;
    private List<T> dna;
    
    protected AbstractGene(List<T>options,int length)
    {
        this.GENETIC_MATERIAL_OPTIONS = options;
        this.length = length;
        this.suppressed =false;
        
    }
    
    protected AbstractGene(List<T>options,int length, boolean suppressed)
    {
        this.GENETIC_MATERIAL_OPTIONS = options;
        this.length = length;
        this.suppressed =suppressed;

    }
    
    private AbstractGene(List<T> dna, List<T>options,int length, boolean suppressed)
    {
        this.GENETIC_MATERIAL_OPTIONS = options;
        this.dna =dna;
        this.length = length;
        this.suppressed = suppressed;        
    }
  
    protected AbstractGene(List<T> options)
    {
        this.GENETIC_MATERIAL_OPTIONS = options;
    }
    
    private T getRandomGeneticMaterial()
    {
        Random rand = new Random();
        return GENETIC_MATERIAL_OPTIONS.get(rand.nextInt(GENETIC_MATERIAL_OPTIONS.size()));
    }
    @Override
    public int hashCode()
    {
        int hash = 5;
        hash = 67 * hash + (this.suppressed ? 1 : 0);
        hash = (67 * hash) + Objects.hashCode(this.dna);
        return hash;
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        for (T data : dna)
            sb.append(data);
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
        if (this.length != other.length)
        {
            return false;
        }
        Iterator<T> itr = this.dna.iterator();
        Iterator<?> oitr = other.dna.iterator();
        while(itr.hasNext())
        {
           T val =  itr.next();
           T oval = (T) oitr.next();
           if( val != oval)
               return false;           
        }
        return true;
    }

    @Override
    public Gene createRandom()
    {
        List<T> dna = new ArrayList();
        while(dna.size() < this.length)
            dna.add(getRandomGeneticMaterial());
     
        return new AbstractGene(dna,this.GENETIC_MATERIAL_OPTIONS,this.length,this.suppressed) {};
    }
    
    @Override
    public void mutate(double p){
        Random rand = new Random();
        List<T> new_dna = new ArrayList();
        for(T strand : dna)
        {
            if(rand.nextDouble() < p && !this.suppressed)
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
        return new AbstractGene(this.GENETIC_MATERIAL_OPTIONS,this.dna,this.length,this.suppressed) {};
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
}

