/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.aas.ga.genes;

import java.util.Arrays;


/**
 *
 * @author Adam
 */
public class AsciiGene extends AbstractGene<String>
{
    public AsciiGene(int length, boolean suppressed)
    {
        super(Arrays.asList("a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z",
                                 "A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z",
                                 "0","1","2","3","4","5","6","7","8","9","0","!","@","#","$","%","^","&","*","(",")","-","=","_","+",".",
                                 ">",",","<","/","?",";",":","{","}"),length, suppressed);
    }
    
    public AsciiGene(int length){
        super(Arrays.asList("a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z",
                                 "A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z",
                                 "0","1","2","3","4","5","6","7","8","9","0","!","@","#","$","%","^","&","*","(",")","-","=","_","+",".",
                                 ">",",","<","/","?",";",":","{","}"),length);
    }
    
    public AsciiGene()
    {
        super(Arrays.asList("a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z",
                                 "A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z",
                                 "0","1","2","3","4","5","6","7","8","9","0","!","@","#","$","%","^","&","*","(",")","-","=","_","+",".",
                                 ">",",","<","/","?",";",":","{","}"));
    }

}
