package com.rabbit.gui.show;

import com.rabbit.gui.base.Stage;
import com.rabbit.gui.component.IGuiWidget;

public interface IShow extends IGuiWidget{
    
    void onInit();
 
    void setSize(int width, int height);
    
    Stage getStage();
    
    void setStage(Stage stage);
    
    String getTitle();
    
    void setTitle(String title);
    
    int getWidth();
    
    int getHeight();
    
}
