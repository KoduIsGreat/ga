/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.aas.ga;


import java.util.ArrayList;
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
    
    private final byte[] target;
    private final int pop;
    private final int max_gen;
    private final int FITTEST =0;
    private int cur_gen;

    public Simulation(String input, int pop, int max_gen)
    {
        this.target = input.getBytes();
        this.pop = pop;
        this.max_gen = max_gen;
        populate(pop,target.length);
    }
    
    private void populate(int pop,int length)
    {
        for(int i =population.size() ; i < pop ;i++)
        {
            population.add(new Chromosome(length));
        }
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
        cur_gen++;
    }
    
    private LinkedHashMap<Integer,Byte> getChildDesirableGenes(LinkedHashMap<Integer,Byte> male, LinkedHashMap<Integer,Byte> female)
    {
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
            if(chromo.getFitness() == 0 || chromo.getAge()>cur_gen/2)
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
        cur_gen = 0;
        while(cur_gen<max_gen)
        {           
           mutatePopulation(rate);
           Collections.sort(population);
           writeGenerationLine(cur_gen,population.get(FITTEST));
           if(population.get(FITTEST).getFitness() ==0)
               break;
           getElites(elitePercentage);          
           nextGen(rate);
        }
    }
    
    
}
