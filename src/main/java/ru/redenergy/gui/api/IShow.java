package ru.redenergy.gui.api;

import ru.redenergy.gui.component.base.Stage;

public interface IShow extends IGuiComponent{
    
    public void onInit();
 
    public void setSize(int width, int height);
    
}
