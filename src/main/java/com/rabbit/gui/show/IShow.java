package com.rabbit.gui.show;

import com.rabbit.gui.base.Stage;
import com.rabbit.gui.component.IGuiComponent;

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
