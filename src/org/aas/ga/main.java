/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.aas.ga;
import org.aas.ga.chromo.Chromosome;
import org.aas.ga.genes.BinaryGene;

/**
 *
 * @author Adam
 */
public class main {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
//      Simulation sim = new Simulation("Then you've heard more than I can speak to, answered the Gaffer. I know nothing about jools . Mr. Bilbo is free with his money, and there seems no lack of it; but I know of no tunnel-making. I saw Mr. Bilbo when he came back, a matter of sixty years ago, when I was a lad. I'd not long come prentice to old Holman (him being my dad's cousin), but he had me up at Bag End helping him to keep folks from trampling and trapessing all over the garden while the sale was on. And in the middle of it all Mr. Bilbo comes up the Hill with a pony and some mighty big bags and a couple of chests. I don't doubt they were mostly full of treasure he had picked up in foreign parts, where there be mountains of gold, they say; but there wasn't enough to fill tunnels. But my lad Sam will know more about that. He's in and out of Bag End. Crazy about stories of the old days he is, and he listens to all Mr. Bilbo's tales. Mr. Bilbo has learned him his letters - meaning no harm, mark you, and I hope no harm will come of it.",200,2500);
//      sim.Evolve(.5,.25);
        Chromosome chromo = new Chromosome(3,12,new BinaryGene());
        System.out.println("we did something");
    }
    
}
