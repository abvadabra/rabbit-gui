package com.rabbit.gui.base;

import java.util.List; 

import com.rabbit.gui.component.IGuiComponent;

public interface ComponentContainer {
    
    List<IGuiComponent> getComponentsList();
    
    default <T> IGuiComponent findComponentById(String id){
        return getComponentsList().stream().filter(com -> id.equals(com.getId())).findFirst().orElse(null);
    }
    
}
