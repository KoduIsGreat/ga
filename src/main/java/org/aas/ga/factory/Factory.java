package org.aas.ga.factory;

import java.util.Collection;

/**
 * Created by adamshelton on 11/17/2016.
 */
public interface Factory<T> {
    public T create();
    public  Collection<T> create(int size);
}
