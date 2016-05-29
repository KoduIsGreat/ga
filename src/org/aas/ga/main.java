/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.aas.ga;


/**
 *
 * @author Adam
 */
public class main {
static final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz !@#$%^&*()-=_+|\'][{};:?.>,<";   
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
      Simulation sim = new Simulation("Hello, World!",400);
      sim.Evolve(0.5,.3);
        
    }
    
}
