package org.aas.ga.genes;

import java.util.Collection;

/**
 * Created by Adam on 8/28/2016.
 */
public interface AlleleOptions<T> {
    public Collection<T> getOptions();
    public void setOptions(Collection<T> options);
    public void buildRandomOptions(int n);
    public T pickRandom();
    public T pickRandom(boolean containsUniques);
    public T[] getRandomOrder();
}
