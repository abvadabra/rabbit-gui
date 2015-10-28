package com.rabbit.gui.base;

import java.util.List; 

import com.rabbit.gui.component.IGui;

public interface WidgetContainer {
    
    List<IGui> getComponentsList();
    
    default <T> IGui findComponentById(String id){
        return getComponentsList().stream().filter(com -> id.equals(com.getId())).findFirst().orElse(null);
    }
    
}
