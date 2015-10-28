package com.rabbit.gui.show;

import com.rabbit.gui.base.Stage;
import com.rabbit.gui.component.IGui;

public interface IShow extends IGui{
    
    void onInit();
 
    void setSize(int width, int height);
    
    Stage getStage();
    
    void setStage(Stage stage);
    
    String getTitle();
    
    void setTitle(String title);
    
    int getWidth();
    
    int getHeight();
    
}
