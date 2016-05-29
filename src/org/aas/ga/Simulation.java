/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.aas.ga;


import java.util.ArrayList;
import java.util.Random;
import static org.aas.ga.main.AB;

/**
 *
 * @author Adam
 */
public class Simulation 
{
    
 //   ArrayList<Chromosome> population = new ArrayList<>();
    byte[] target;
    int pop;
//    byte[] fittest;
//    byte[] current;
    public Simulation(String input, int pop)
    {
        this.target = input.getBytes();
        this.pop = pop;
// populate(pop,target.length);
    }
    
//    private void populate(int pop,int length)
//    {
//        for(int i =0 ; i < pop ;i++)
//        {
//            population.add(new Chromosome(length,target));
//        }
//    }
    private int HammingDistance(byte a, byte b)
    {
        int dist = 1;
        if(a == b)
            dist --;
        return dist;
    }
    private int calculateFitness(byte[]chromo)
    {     
        int fit =0;
        for(int i =0 ; i < target.length;i++)
            fit += HammingDistance(chromo[i],target[i]);
        
        return fit;
    }
    private byte[] mutate(double rate,byte[]data)
    {    
        Random rand = new Random();
        byte[] newChromo = new byte[target.length];
        for(int i =0; i < data.length; i++)
        {
            newChromo[i] = rand.nextDouble() < rate ? randomByte()  : data[i];
        }
        return newChromo;
    }
    
    private  byte randomByte()
    {
         return ( (byte) (32 + (new Random()).nextInt(95)) );
    }
    
    public  void Evolve(double rate){
        
        int generation =0;        

        byte[] current= generateRandomBytes();
        byte[] fittest = current;
        while(calculateFitness(current)>0)
        {
            writeGenerationLine(generation,current);
            for(int i = 0; i <pop ; i ++)
            {     
                byte [] mutation =mutate(rate,current);
                if(calculateFitness(mutation)<= calculateFitness(fittest))
                {
                    fittest = mutation;
                }
                current = fittest;
            }
            generation++;
        }
        writeGenerationLine(generation,current);
    }
    
    private void mutatePopulation(double rate)
    {
//        for(Chromosome chromo : this.population)
//        {     
//            chromo.mutate(rate);
//            if(calculateFitness(chromo.getData())< calculateFitness(fittest))
//            {
//                fittest = chromo.getData();
//            }
//            current = fittest;
//        }
    }
    private byte[] generateRandomBytes(){
        Random rand = new Random();
        StringBuilder sb = new StringBuilder();
        for(int i =0; i< target.length; i ++)
            sb.append(AB.charAt(rand.nextInt(AB.length())));
        
        return sb.toString().getBytes();
    }
    
    private  void printStringChromosome(byte[] byteString){
        for (int i = 0; i < byteString.length; i ++)
            System.out.print((char) byteString[i]);
        System.out.print("\n");
    }
    private void writeGenerationLine(int generation, byte[]current)
    {
        System.out.print("Generation " + generation +" Fitness "+ calculateFitness(current)+ " current ");
        printStringChromosome(current);
    }
}
