package com.rabbit.gui.component;

import java.util.Collections;
import java.util.List;

/**
 * Represents gui element which contains and displays multiple objects
 * @author RedEnergy
 *
 * @param <T> - type of contained objects
 */
public interface WidgetContainer<T> {
    
    public WidgetContainer<T> add(T object);
    
    public WidgetContainer<T> addAll(T ... objects);
    
    public WidgetContainer<T> remove(T object);
    
    public WidgetContainer<T> clear();
    
    public Object getContent();
}
