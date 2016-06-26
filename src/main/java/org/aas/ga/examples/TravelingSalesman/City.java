package org.aas.ga.examples.TravelingSalesman;

/**
 * Created by Adam on 6/26/2016.
 */
public class City {

    String name;
    Double xCoordinate;
    Double yCoordinate;
    public City(String name,Double x, Double y)
    {
        this.name = name;
        xCoordinate = x;
        yCoordinate = y;
    }
}
