/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.aas.ga;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Random;

/**
 *
 * @author Adam
 */
public class Chromosome implements Comparable<Chromosome>{
    private final int MALE = 0;
    private final int FEMALE = 1;
    private byte[] data;
    private int size;
    private int fitness;
    private int age;
    private int sex; // 0 male 1 female
    private LinkedHashMap<Integer,Byte> desirableGenes = new LinkedHashMap<>();
    public Chromosome(int size)
    {
        this.size = size;
        init(size);
    }
    protected Chromosome(byte[]data){
        this.data = data;
        sex = data[0]%2;    
        fitness = 0;
        age = 0; 
    }

    private void init(int size)
    {
        data = getRandomBytes(size);
        sex = data[0]%2;    
        fitness = 0;
        age = 0;   
    }
    
    private  byte randomByte()
    {
         return ( (byte) (32 + (new Random()).nextInt(95)) );
    }
    
    private byte[] getRandomBytes(int length)
    {
        byte[] random_data = new byte[length];
        for(int i =0; i< length; i ++)
            random_data[i] = randomByte();
        
        return random_data;
    }
    
    private int HammingDistance(byte a, byte b)
    {
        int dist = 1;
        if(a == b)
            dist --;
        return dist;
    }
    
    @Override
    public int hashCode()
    {
        int hash = 7;
        hash = 37 * hash + Arrays.hashCode(this.data);
        hash = 37 * hash + this.size;
        hash = 37 * hash + this.fitness;
        hash = 37 * hash + this.age;
        hash = 37 * hash + this.sex;
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
        if (this.size != other.size)
        {
            return false;
        }
        if (this.fitness != other.fitness)
        {
            return false;
        }
        if (this.age != other.age)
        {
            return false;
        }
        return this.sex != other.sex;
    }
    
    @Override
    public int compareTo(Chromosome other)
    {
        if(this.fitness == other.getFitness())
            return 0;
        
        return (this.fitness < other.getFitness()) ? -1 : 1;
    }
    
    protected void mutate(double rate)
    {    
        Random rand = new Random();
        for(int i =0; i < data.length; i++)
        {
            if(!desirableGenes.containsKey(i))
                data[i] = rand.nextDouble() < rate ? randomByte()  : data[i];
        }
    }
       
    protected void calculateFitness(byte[]target)
    {   
        desirableGenes.clear();
        for(int i =0 ; i < target.length;i++)
        {
            int val = HammingDistance(data[i],target[i]);
            if (val==0)
                desirableGenes.put(i, data[i]);
            fitness += val;   
        }
    }
    
    protected byte[] getData()
    {
        return data;
    }

    protected void setData(byte[] data)
    {
        this.data = data;
    }

    protected int getSize()
    {
        return size;
    }

    protected void setSize(int size)
    {
        this.size = size;
    }
    
    protected int getFitness()
    {
        return fitness;
    }
    
    protected int getAge()
    {
        return age;
    }

    protected void age()
    {
        age += 1;
    }
    
    protected int getSex()
    {
        return sex;
    }
    protected void setSex(int sex){
        this.sex = sex;
    }

    public LinkedHashMap<Integer,Byte> getDesirableGenes(){
        return desirableGenes;
    }
    
    public void setDesirableGenes(LinkedHashMap<Integer,Byte> desirableGenes)
    {
        this.desirableGenes = desirableGenes;
    }
}
