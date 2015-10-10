package ru.redenergy.gui.component.base;

import ru.redenergy.gui.component.IGuiComponent;

public interface IShow extends IGuiComponent{
    
    void onInit();
 
    void setSize(int width, int height);
    
    Stage getStage();
    
    void setStage(Stage stage);
    
}
