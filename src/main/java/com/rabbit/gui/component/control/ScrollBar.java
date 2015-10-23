package com.rabbit.gui.component.control;

import com.rabbit.gui.component.GuiWidget;
import com.rabbit.gui.render.Renderer;


/**
 * 
 * @deprecated Not yet implemented
 */
public class ScrollBar extends GuiWidget {

    protected int xPos;
    
    protected int yPos;
    
    protected int width;
    
    protected int height;

    protected int scrolled = 0;
    
    protected int scrollerSize;
    
    public ScrollBar(int xPos, int yPos, int width, int height, int scrollerSize){
        this.xPos = xPos;
        this.yPos = yPos;
        this.width = width;
        this.height = height;
        this.scrollerSize = scrollerSize;
    }

    @Override
    public void onDraw(int mouseX, int mouseY, float partialTicks) {
        super.onDraw(mouseX, mouseY, partialTicks);
        Renderer.drawRect(this.xPos - 2, this.yPos - 2, this.xPos + this.width + 2, this.yPos + this.height + 2, -0xFFFFFF);
        Renderer.drawRect(this.xPos, this.yPos, this.xPos + this.width, this.yPos + this.height, Integer.MAX_VALUE);
        Renderer.drawRect(this.xPos + 2, this.yPos + 4, this.xPos + this.width - 2, this.yPos + this.height - 4, -0x8B8B8B - 1);
        drawScroller(mouseY, mouseY, mouseX, mouseY);
    }
    
    private void drawScroller(int xPos, int yPos, int width, int height){
        Renderer.drawRect(xPos, yPos, xPos + width, yPos + height, -0xF1FA21F);
    }
    
}
