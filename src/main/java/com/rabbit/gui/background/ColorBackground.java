package com.rabbit.gui.background;

import java.awt.Color;

import com.rabbit.gui.component.IBackground;
import com.rabbit.gui.render.Renderer;

public class ColorBackground implements IBackground {
    
    private final Color color;
    
    public ColorBackground(Color color){
        this.color = color;
    }
    
    @Override
    public void onDraw(int width, int height, int mouseX, int mouseY, float partialTicks) {
        Renderer.drawRect(0, 0, width, height, this.color.getRGB());
    }

}
