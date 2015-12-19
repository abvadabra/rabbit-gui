package com.rabbit.gui.base;

import java.util.List;

import com.rabbit.gui.component.IGui;

public interface WidgetContainer {
    
    /**
     * Registers component as a part of pane
     * 
     * @param component
     */
    void registerComponent(IGui component);
    
    /**
     * Called when component registered in pane
     */
    void onRegistered(WidgetContainer pane);
    
    List<IGui> getComponentsList();
    
    /**
     * Returns pane in which this component registered
     * 
     * @return parent pane - can be null if it hasn't been registered yet
     */
    WidgetContainer getParent();
    
    default <T> T findComponentById(String id){
        return (T) getComponentsList().stream().filter(com -> id.equals(com.getId())).findFirst().orElse(null);
    }
    
}
