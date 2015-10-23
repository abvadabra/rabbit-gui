package com.rabbit.gui.base;

import java.util.List; 

import com.rabbit.gui.component.IGuiWidget;

public interface WidgetContainer {
    
    List<IGuiWidget> getComponentsList();
    
    default <T> IGuiWidget findComponentById(String id){
        return getComponentsList().stream().filter(com -> id.equals(com.getId())).findFirst().orElse(null);
    }
    
}
