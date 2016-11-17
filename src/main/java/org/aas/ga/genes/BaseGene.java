/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.aas.ga.genes;

import org.aas.ga.sim.Simulation;
import org.aas.ga.util.RandomUtil;


import java.util.*;


/**
 *
 * @author Adam
 * @param <T>
 */
public class BaseGene<T> implements Gene<T>
{


    private Collection<T> dna ;
    private boolean dominant;
    private int length;

    public BaseGene(){}

    
    public BaseGene(Collection<T> dna, int length, boolean dominant)
    {
        this.dna =dna;
        this.length = length;
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
        final BaseGene<?> other = (BaseGene<?>) obj;
        return hashCode() == other.hashCode();
    }


    @Override
    public void mutate(double p)
    {
        Random rand = new Random();
        if(Simulation.seed != null)
            rand = Simulation.seed;

        List<T> new_dna = new ArrayList();
        for(T strand : dna)
        {
            if(rand.nextDouble() < p && !this.dominant)
            {
                new_dna.add((T) RandomUtil.getRandomGeneticMaterial(Simulation.options,Simulation.seed));
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
        return new BaseGene(this.dna,this.length,this.dominant) ;
    }
    @Override
    public Collection<T> getDna()
    {
        return this.dna;
    }

    @Override
    public void setDna(Collection dna){
        this.dna = dna;
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

}

