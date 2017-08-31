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
public class BaseGene<T> implements Gene<T>
{


    private T dna ;
    private boolean dominant;
    public BaseGene(){}

    
    public BaseGene(T dna, boolean dominant)
    {
        this.dna =dna;
        this.dominant = dominant;
    }
    
    @Override
    public int hashCode()
    {
        int hash = 5;
        hash = 67 * hash + (this.dominant ? 1 : 0);
        hash = (67 * hash) + Objects.hashCode(this.dna);
        return hash;
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
            sb.append(dna.toString());
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
        final BaseGene<?> other = (BaseGene<?>) obj;
        return hashCode() == other.hashCode();
    }


    @Override
    public Gene copy()
    {
        return new BaseGene(this.dna,this.dominant) ;
    }
    @Override
    public T getDna()
    {
        return this.dna;
    }

    @Override
    public void setDna(T dna){
        this.dna = dna;
    }

    @Override
    public boolean isDominant() {
        return dominant;
    }
    @Override
    public void setDominant(boolean dominant) {
        this.dominant = dominant;
    }

}

