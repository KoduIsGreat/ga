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
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author Adam
 * @param <T>
 */
public abstract class BaseGene<T>
{
    protected List<T> GENETIC_MATERIAL_OPTIONS = new ArrayList<>();

    private boolean suppressed;
    private int length;
    private List<T> dna;
    
    public BaseGene(List<T> dna,List<T>options ,boolean suppressed, int length)
    {    
        try
        {
            GENETIC_MATERIAL_OPTIONS.addAll(options);
            validateDNA(dna);
            this.length = length;                                     
            this.suppressed = suppressed;
            this.dna = dna;
        }
        catch (InvalidGeneticOperatorException ex)
        {
            Logger.getLogger(BaseGene.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public BaseGene(List<T> dna,List<T> options){
        try
        {
            GENETIC_MATERIAL_OPTIONS.addAll(options);
            validateDNA(dna);
            this.dna = dna;          
            this.length = dna.size();
            this.suppressed = false;
        }
        catch (InvalidGeneticOperatorException ex)
        {
            Logger.getLogger(BaseGene.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    private void validateDNA(List<T> dna) throws InvalidGeneticOperatorException
    {
        for(T strand : dna){
            if(!GENETIC_MATERIAL_OPTIONS.contains(strand))
            {
                throw new InvalidGeneticOperatorException("Invalid Genetic material in DNA at index "+dna.indexOf(strand),new Exception());
            }
        }    
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
        hash = 67 * hash + Objects.hashCode(this.dna);
        return hash;
    }

    @Override
    public String toString()
    {
        return "BaseGene{" + "GENETIC_MATERIAL_OPTIONS=" + GENETIC_MATERIAL_OPTIONS + ", suppressed=" + suppressed + ", length=" + length + ", dna=" + dna + '}';
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
        final BaseGene<?> other = (BaseGene<?>) obj;
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
    
    public  BaseGene<T> createRandom()
    {
        List<T> dna = new ArrayList<>();
        while(dna.size() <= this.length)
            dna.add(getRandomGeneticMaterial());
     
        return new BaseGene(dna,this.GENETIC_MATERIAL_OPTIONS,false,this.length) {};
    }
    
    public void mutate(double p){
        Random rand = new Random();
        List<T> new_dna = new ArrayList<>();
        for(T strand : dna)
        {
            if(rand.nextDouble() < p)
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
    
    public BaseGene<T> copy()
    {
        return new BaseGene(this.dna,this.GENETIC_MATERIAL_OPTIONS,this.suppressed,this.length) {};
    }
    
    public List<T> getDna()
    {
        return dna;
    }

    public void setDna(List<T> dna)
    {
        this.dna = dna;
    }
    
    public boolean isSuppressed()
    {
        return suppressed;
    }

    public void setSuppressed(boolean suppressed)
    {
        this.suppressed = suppressed;
    }

    public int getLength()
    {
        return length;
    }

    public void setLength(int length)
    {
        this.length = length;
    }
}

class InvalidGeneticOperatorException extends Exception{
    
    public InvalidGeneticOperatorException(String message,Throwable cause){
        super(message,cause);
    }
}
