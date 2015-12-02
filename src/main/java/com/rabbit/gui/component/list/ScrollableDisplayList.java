package com.rabbit.gui.component.list;

import java.awt.*;
import java.util.List;

import com.rabbit.gui.component.control.ScrollBar;
import com.rabbit.gui.component.list.entries.ListEntry;
import com.rabbit.gui.layout.LayoutComponent;
import com.rabbit.gui.render.Renderer;
import com.rabbit.gui.utils.Geometry;
import net.minecraft.client.Minecraft;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

@LayoutComponent
public class ScrollableDisplayList extends DisplayList {

    protected ScrollBar scrollBar;
    
    private ScrollableDisplayList(){super();}
    
    public ScrollableDisplayList(int xPos, int yPos, int width, int height, int slotHeight, List<ListEntry> content) {
        super(xPos, yPos, width, height, slotHeight, content);
    }

    @Override
    public void onDraw(int mouseX, int mouseY, float partialTicks) {
        super.onDraw(mouseX, mouseY, partialTicks);

    }

    @Override
    public void setup() {
        super.setup();
        int scrollerSize = this.height / (this.content.isEmpty() ? 1 : this.content.size());
        if(scrollerSize < 10) scrollerSize = 10;
        if(this.content.size() < this.height / this.slotHeight) scrollerSize = this.height - 4;
        scrollBar = new ScrollBar(getX() + this.width - 10, getY(), 10, this.height, scrollerSize);
        registerComponent(scrollBar);
    }
    
    @Override
    protected void drawListContent(int mouseX, int mouseY){
        scrollBar.setVisiblie(!canFit());
        scrollBar.setHandleMouseWheel(!canFit());
        scrollBar.setScrollerSize(getScrollerSize());
        int scale = Geometry.computeScaleFactor();
        for(int i = 0; i < content.size(); i++){
            ListEntry entry = content.get(i);
            int slotPosX = getX();
            int slotPosY = ((getY() + i * slotHeight) - (int)((this.slotHeight * scrollBar.getProgress() * this.content.size()) * 0.925F));
            int slotWidth = this.width; 
            int slotHeight = this.slotHeight;
            if(slotPosY < getY() + this.height && slotPosY + slotHeight > getY()){
                GL11.glPushMatrix();
                GL11.glEnable(GL11.GL_SCISSOR_TEST);
                Minecraft mc = Minecraft.getMinecraft();
                GL11.glScissor(getX() * scale, mc.displayHeight - (getY() + getHeight()) * scale, getWidth() * scale, getHeight() * scale);
                entry.onDraw(this, slotPosX, slotPosY, slotWidth, slotHeight, mouseX, mouseY);
                GL11.glDisable(GL11.GL_SCISSOR_TEST);
                GL11.glPopMatrix();
            }
        }
    }
    
    @Override
    protected void handleMouseClickList(int mouseX, int mouseY){
        for(int i = 0; i < content.size(); i++){
            ListEntry entry = content.get(i);
            int slotPosX = getX();
            int slotPosY = ((getY() + i * slotHeight) - (int)((this.slotHeight * scrollBar.getProgress() * this.content.size()) * 0.925F));
            int slotWidth = this.width;
            int slotHeight = this.slotHeight;
            boolean scrollbarActive = scrollBar.isScrolling() && scrollBar.isVisible();
            if(slotPosY + slotHeight <= getY() + this.height && slotPosY >= getY() && !scrollbarActive){
                boolean clickedOnEntry = Geometry.isDotInArea(slotPosX, slotPosY, slotWidth, slotHeight, mouseX, mouseY);
                if(clickedOnEntry) entry.onClick(this, mouseX, mouseY);
            }
        } 
    }
    
    /**
     * Returns true if content height of list is not more that list actual height
     */
    private boolean canFit(){
        return this.content.size() * this.slotHeight < this.height;
    }

    private int getScrollerSize(){
        return (int)(1F * this.height / (this.content.size() * this.slotHeight) * (this.height - 4));
    }
    
}
