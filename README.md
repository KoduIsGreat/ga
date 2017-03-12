# Genetic Algorithm API

## Synopsis
This is my attempt at a relatively straight-forward implementation of a genetic algorithm in Java.
Its core goals are to :
 - Abstract away genetic operator complexity for simple adopotion
 - Allow extensibility of core algorithm operations to allow rapid creation of customized algorithms 


## Code Example

A simple example could be the following post on /r/dailyprogrammer 
https://www.reddit.com/r/dailyprogrammer/comments/40rs67/20160113_challenge_249_intermediate_hello_world/

To accomplish this we  subclass the AbstractGeneticAlgorithm class and override the evaluteFitness method.
Additionally for our specific case, we need to override the Should Terminate method, which lets the algorithm know when to stop.
Unlike most problems genetic algorithms are used to solving this one has a known end-point (when we've found the matched string).
```java
public class AsciiMatcherGA extends AbstractGeneticAlgorithm {

    private final String target;

    public AsciiMatcherGA(List<Chromosome> pop, double absW, double relW, Mutator mutator, double p_crossover, int gen, boolean elitist, boolean inverseFitRanking, int quit_after, int refresh_after, String target) {
        super(pop, absW, relW, mutator, p_crossover, gen, elitist,inverseFitRanking, quit_after, refresh_after);
        this.target = target;
    }
    public AsciiMatcherGA(String target){this(null,.5,.5,null,.5,50000,false,true,5000,2500,target);}

    @Override
    public void evaluateFitness(Chromosome chromo)
    {
        Double fit = 0.0;
        String stringChromo = chromo.toString();
        for(int k =0; k<stringChromo.length(); k ++)
        {
            int geneFit = HammingDistance((byte)stringChromo.charAt(k),(byte)target.charAt(k));
            List<Gene> genes = (List)chromo.getGenes();
            if(geneFit == 0 && !(genes.get(k).isDominant()))
            {
                genes.get(k).setDominant(true);
            }
            fit += geneFit;
        }
        chromo.setFitness(fit);
    }


    private int HammingDistance(byte a, byte b)
    {
        if (a == b)
            return 0;
        return 1;
    }

    @Override
    public boolean shouldTerminate(){return this.getFittest().getFitness() == 0;}

}
```

And using our new ascii matching class.
The API provides a Simulation class which serves as a builder of factories and other information to initialize the initial population state of the algorithm.
(Simulation is still a WIP)
```java
public class AsciiMatching
{
    public static void main(String[]args)
    {
        String input = "Hello, World!"
        AsciiMatcherGA ga = new AsciiMatcherGA(input);
        Simulation sim = new Simulation(DefaultAlleleOptions.ALL_ASCII,15125L,ga);
        sim.setChromoLength(input.length());
        sim.setMutator(new BaseMutator());
        sim.init();
        sim.run();

    }
}
```
## Requirements
    -maven 3.x : https://maven.apache.org/
    -java sdk 1.8 : http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html

## Installation
    
Currently not hosted on any maven repo, so perform the following to build the source
```bash
git clone https://github.com/KoduIsGreat/ga.git
cd your/dir/path/with/pom.xml
mvn clean package
```

## API Reference
No javadoc yet







