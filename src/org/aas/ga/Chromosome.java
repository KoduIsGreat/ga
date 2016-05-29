/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.aas.ga;

import java.util.Arrays;
import java.util.Random;
import static org.aas.ga.main.AB;

/**
 *
 * @author Adam
 */
public class Chromosome {

    private byte[] data;
    private int size;

    
    public Chromosome(int size,byte[] target)
    {
        this.size = size;
        birth(size);

    }
    

    
    @Override
    public int hashCode()
    {
        int hash = 7;
        hash = 41 * hash + Arrays.hashCode(this.data);
        hash = 41 * hash + this.size;
        return hash;
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
        final Chromosome other = (Chromosome) obj;
        if (!Arrays.equals(this.data, other.data))
        {
            return false;
        }
        return this.size != other.size;
    }
    
    
    private void birth(int length)
    {
        Random rand = new Random();
        StringBuilder sb = new StringBuilder();
        for(int i =0; i< length; i ++)
            sb.append(AB.charAt(rand.nextInt(AB.length())));
        
        data = sb.toString().getBytes();
    }
    
    public void mutate(double rate)
    {    
        Random rand = new Random();
 
        for(int i =0; i < data.length; i++)
        {
            data[i] = rand.nextDouble() < rate ? randomByte()  : data[i];
        }
        
    }
    
    private  byte randomByte()
    {
         return ( (byte) (32 + (new Random()).nextInt(95)) );
    }
    
    public byte[] getData()
    {
        return data;
    }

    public void setData(byte[] data)
    {
        this.data = data;
    }

    public int getSize()
    {
        return size;
    }

    public void setSize(int size)
    {
        this.size = size;
    }
    

}
