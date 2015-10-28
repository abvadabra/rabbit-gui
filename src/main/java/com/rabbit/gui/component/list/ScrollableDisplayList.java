package com.rabbit.gui.component.list;

import java.util.List;

import com.rabbit.gui.component.control.ScrollBar;
import com.rabbit.gui.component.list.DisplayList.ListEntry;
import com.rabbit.gui.layout.LayoutComponent;
import com.rabbit.gui.utils.GeometryUtils;

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
    public void onMouseClicked(int posX, int posY, int mouseButtonIndex) {
        super.onMouseClicked(posX, posY, mouseButtonIndex);
    }

    @Override
    public void setup() {
        super.setup();
        int scrollerSize = this.height / this.content.size();
        if(scrollerSize < 10) scrollerSize = 10;
        if(this.content.size() < this.height / this.slotHeight) scrollerSize = this.height - 4;
        scrollBar = new ScrollBar(this.xPos + this.width - 10, this.yPos, 10, this.height, scrollerSize);
        registerComponent(scrollBar);
    }
    
    @Override
    protected void drawListContent(int mouseX, int mouseY){
        for(int i = 0; i < content.size(); i++){
            ListEntry entry = content.get(i);
            int slotPosX = this.xPos;
            int slotPosY = ((this.yPos + i * slotHeight) - (int)((this.slotHeight * scrollBar.getProgress() * this.content.size()) * 0.925F));
            int slotWidth = this.width; 
            int slotHeight = this.slotHeight;
            if(slotPosY + slotHeight <= this.yPos + this.height && slotPosY >= this.yPos){
                entry.onDraw(this, slotPosX, slotPosY, slotWidth, slotHeight, mouseX, mouseY);
            }
        }
    }
    
    @Override
    protected void handleMouseClickList(int mouseX, int mouseY){
        for(int i = 0; i < content.size(); i++){
            ListEntry entry = content.get(i);
            int slotPosX = this.xPos;
            int slotPosY = ((this.yPos + i * slotHeight) - (int)((this.slotHeight * scrollBar.getProgress() * this.content.size()) * 0.925F));
            int slotWidth = this.width;
            int slotHeight = this.slotHeight;
            if(slotPosY + slotHeight <= this.yPos + this.height && slotPosY >= this.yPos){
                boolean clickedOnEntry = GeometryUtils.isDotInArea(slotPosX, slotPosY, slotWidth, slotHeight, mouseX, mouseY);
                if(clickedOnEntry) entry.onClick(this, mouseX, mouseY);
            }
        } 
    }

    
    
}
