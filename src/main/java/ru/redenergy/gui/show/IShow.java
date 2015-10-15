package ru.redenergy.gui.show;

import ru.redenergy.gui.base.Stage;
import ru.redenergy.gui.component.IGuiComponent;

public interface IShow extends IGuiComponent{
    
    void onInit();
 
    void setSize(int width, int height);
    
    Stage getStage();
    
    void setStage(Stage stage);
    
    String getTitle();
    
    void setTitle(String title);
    
    int getWidth();
    
    int getHeight();
    
}
