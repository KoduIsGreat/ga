/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.aas.ga;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Random;


/**
 *
 * @author Adam
 */
public class Simulation 
{
    
    ArrayList<Chromosome> population = new ArrayList<>();
    ArrayList<Chromosome> survivors = new ArrayList<>();
    
    byte[] target;
    int pop;
    int gen;
    private final int FITTEST =0;
    private int mostFitValue = 9999;

    public Simulation(String input, int pop)
    {
        this.target = input.getBytes();
        this.pop = pop;    
        populate(pop,target.length);
    }
    
    private void populate(int pop,int length)
    {
        for(int i =population.size() ; i < pop ;i++)
        {
            population.add(new Chromosome(length));
        }
    }

    private byte[] concat(byte[] first, byte[] second) 
    {
        byte[] result = Arrays.copyOf(first, first.length + second.length);
        System.arraycopy(second, 0, result, first.length, second.length);
        return result;
    }
    
    private void mate(double rate,Random rand)
    {       
        population.addAll(crossOver(survivors.remove(rand.nextInt(survivors.size())),
                                        survivors.remove(rand.nextInt(survivors.size())),
                                        rate));
    }
    
    private void nextGen(double rate)
    {   
        Random rand = new Random();
        while(!survivors.isEmpty())
        {
            mate(rate,rand);
        }
        
        populate(pop,target.length);
        gen++;
    }
    private LinkedHashMap<Integer,Byte> getChildDesirableGenes(LinkedHashMap<Integer,Byte> male, LinkedHashMap<Integer,Byte> female){
        byte[] newData = new byte[target.length];
        for(Integer i : female.keySet())
        {
            male.putIfAbsent(i, female.get(i));
        }
  
        
        return male;
    }
    private byte[] getChildData(LinkedHashMap<Integer,Byte> genes)
    {
        byte[] newData = new byte[target.length];
        for(int i =0 ; i<newData.length ; i++)
            {
                if(genes.containsKey(i))
                {
                    newData[i] = genes.get(i);
                }
            }

            return newData;
    }
    private ArrayList<Chromosome> crossOver(Chromosome male, Chromosome female,double rate)
    {
        ArrayList<Chromosome> toNextGen = new ArrayList<>();
        toNextGen.add(male);
        toNextGen.add(female);
        LinkedHashMap<Integer,Byte> genes = getChildDesirableGenes(male.getDesirableGenes(),
                                                                   female.getDesirableGenes());
        byte[] childData = getChildData(genes);
        
        Chromosome child = new Chromosome(childData);
        child.setDesirableGenes(genes);
        child.mutate(rate);
        child.calculateFitness(target);
        toNextGen.add(child);
        
        return toNextGen;        
    }

    private void mutatePopulation(double rate)
    {
        for(Chromosome chromo : this.population)
        {   
            if(chromo.getFitness() == 0 || chromo.getAge()>gen/2)
            {
                chromo.mutate(rate);
                chromo.calculateFitness(target);
            }
        }
    }
 
    private  void printStringChromosome(byte[] byteString)
    {
        for (int i = 0; i < byteString.length; i ++)
            System.out.print((char) byteString[i]);
        System.out.print("\n");
    }
    
    private void writeGenerationLine(int generation, Chromosome fittest)
    {
        System.out.print("Generation " + generation +" Fitness "+ fittest.getFitness() + " current ");
        printStringChromosome(fittest.getData());
    }
    
    private void getElites(double percentage)
    {
        if(!survivors.isEmpty())
        {           
            survivors.clear();
        }
        for(int i = 0 ; i<pop*percentage; i++)
        {
            population.get(i).age();
            survivors.add(population.get(i));
        }
        population.clear();
    }
    
    public void Evolve(double rate, double elitePercentage)
    {
        gen = 0;
        while(mostFitValue>0)
        {           
           mutatePopulation(rate);
           Collections.sort(population);
           writeGenerationLine(gen,population.get(FITTEST));
           mostFitValue = population.get(FITTEST).getFitness();
           if(mostFitValue ==0)
               break;
           getElites(elitePercentage);          
           nextGen(rate);
        }
        writeGenerationLine(gen,population.get(FITTEST));
    }
    
    
}
