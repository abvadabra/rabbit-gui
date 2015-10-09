package ru.redenergy.gui.api.components;

import java.util.Collections;
import java.util.List;

/**
 * Represents gui element which contains and displays multiple objects
 * @author RedEnergy
 *
 * @param <T> - type of contained objects
 */
public interface MultipleModel<T> {
    
    public MultipleModel<T> add(T object);
    
    public MultipleModel<T> addAll(T ... objects);
    
    public MultipleModel<T> remove(T object);
    
    public MultipleModel<T> clear();
    
    public Object getContent();
}
