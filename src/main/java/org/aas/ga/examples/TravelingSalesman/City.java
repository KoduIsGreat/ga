package org.aas.ga.examples.TravelingSalesman;


import java.util.*;

/**
 * Created by Adam on 6/26/2016.
 */
public class City {


    int id;
    Double xCoordinate;
    Double yCoordinate;

    private City(Double x, Double y)
    {

        xCoordinate = x;
        yCoordinate = y;
        id = 31 * id + (xCoordinate != null ? xCoordinate.hashCode() : 0);
        id = 31 * id + (yCoordinate != null ? yCoordinate.hashCode() : 0);
        id = Math.abs(id);
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        City city = (City) o;

        if (id != city.id) return false;
        if (xCoordinate != null ? !xCoordinate.equals(city.xCoordinate) : city.xCoordinate != null) return false;
        return yCoordinate != null ? yCoordinate.equals(city.yCoordinate) : city.yCoordinate == null;

    }

    @Override
    public int hashCode()
    {
        int result =  id;
        result = 31 * result + (xCoordinate != null ? xCoordinate.hashCode() : 0);
        result = 31 * result + (yCoordinate != null ? yCoordinate.hashCode() : 0);
        return result;
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append(" id:").append(id);
        return sb.toString();
    }

    public static Set<City> createRandomCities(int n)
    {
        HashSet<City> cities = new HashSet<>();
        Random rand = new Random();
        while(cities.size()<n)
        {
            Double x = rand.nextDouble() * 100;
            Double y = rand.nextDouble() * 100;
            cities.add(new City(x,y));
        }
        return cities;
    }
}
