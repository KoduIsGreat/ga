package factory;

import org.aas.ga.chromo.Chromosome;
import org.aas.ga.chromo.DefaultChromosome;
import org.aas.ga.genes.AsciiGene;

/**
 * Created by Adam on 6/25/2016.
 */
public class EntityFactory {

    public static Chromosome createAsciiChromosome(String  data){
        DefaultChromosome chromosome = new DefaultChromosome();

        for(int i = 0; i < data.length();i++ )
        {
            chromosome.getGenes().add(new AsciiGene(data.substring(i,i+1)));
        }
        return chromosome;
    }
}
