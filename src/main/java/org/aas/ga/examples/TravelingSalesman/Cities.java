package org.aas.ga.examples.TravelingSalesman;

import org.aas.ga.genes.AlleleOptions;

import java.util.*;

/**
 * Created by Adam on 8/28/2016.
 */
public class Cities implements AlleleOptions<City>
{
    private LinkedHashSet<City> options = new LinkedHashSet<>() ;

    public Cities(int num){ buildRandomOptions(20);}

    @Override
    public Collection<City> getOptions() {
        return options;
    }

    @Override
    public void setOptions(Collection<City> options)
    {
        this.options =new LinkedHashSet<City>(options);
    }

    @Override
    public void buildRandomOptions(int n)
    {
        Random rand = new Random();
        while(options.size()<n)
        {
            Double x = rand.nextDouble() * 100;
            Double y = rand.nextDouble() * 100;
            options.add(new City(x,y));
        }

    }

    @Override
    public City pickRandom()
    {
        City city;
        Integer randm = new Random().nextInt(options.size());
        int i = 0;
        Iterator<City> itr = options.iterator();
        do
        {
            city = itr.next();
            i++;
        }
        while(i<randm);
        return city;
    }
}
