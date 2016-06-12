package org.aas.ga.algo;

import org.aas.ga.chromo.Chromosome;
import org.aas.ga.trans.Transformer;

import java.util.List;

/**
 * Created by Adam on 6/12/2016.
 */
public abstract class AbstractGeneticAlgorithm implements GeneticAlgorithm {

    private double absFitWeight;
    private double relFitWeight;
    private Transformer transformer;
    private int origPopSize;
    private List<Chromosome> population;
    private double minRunFit;
    private double maxRunFit;
    @Override
    public void evaluateFitness(Chromosome chromo) {

    }

    @Override
    public Chromosome getWeakest() {
        return null;
    }

    @Override
    public Chromosome getFittest() {
        return null;
    }

    @Override
    public void sort() {

    }

    @Override
    public List<Chromosome> compete() {
        return null;
    }
    @Override
    public List<Chromosome> reproduce(List<Chromosome> survivors, double pCrossover, int targetSize){
        return null;
    }

    @Override
    public List<Chromosome> reproduce(List<Chromosome> survivors, double pCrossover){
        return null;
    }

    @Override
    public void mutate(double p) {

    }

    @Override
    public void refresh() {

    }

    @Override
    public void init() {

    }

    @Override
    public boolean shouldTerminate() {
        return false;
    }

    @Override
    public void run() {

    }

    public double getAbsFitWeight() {
        return absFitWeight;
    }

    public void setAbsFitWeight(double absFitWeight) {
        this.absFitWeight = absFitWeight;
    }

    public double getMaxRunFit() {
        return maxRunFit;
    }

    public void setMaxRunFit(double maxRunFit) {
        this.maxRunFit = maxRunFit;
    }

    public Transformer getTransformer() {
        return transformer;
    }

    public void setTransformer(Transformer transformer) {
        this.transformer = transformer;
    }

    public int getOrigPopSize() {
        return origPopSize;
    }

    public void setOrigPopSize(int origPopSize) {
        this.origPopSize = origPopSize;
    }
    @Override
    public List<Chromosome> getPopulation() {
        return population;
    }
    @Override
    public void setPopulation(List<Chromosome> population) {
        this.population = population;
    }

    public double getMinRunFit() {
        return minRunFit;
    }

    public void setMinRunFit(double minRunFit) {
        this.minRunFit = minRunFit;
    }

    public double getRelFitWeight() {
        return relFitWeight;
    }

    public void setRelFitWeight(double relFitWeight) {
        this.relFitWeight = relFitWeight;
    }
}
