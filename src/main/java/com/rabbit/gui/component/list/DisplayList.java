package com.rabbit.gui.component.list;

import com.rabbit.gui.component.GuiWidget;
import com.rabbit.gui.component.WidgetList;
import com.rabbit.gui.component.list.entries.ListEntry;
import com.rabbit.gui.layout.LayoutComponent;
import com.rabbit.gui.render.Renderer;
import com.rabbit.gui.utils.Geometry;

import java.util.Arrays;
import java.util.List;

@LayoutComponent
public class DisplayList extends GuiWidget implements WidgetList<ListEntry>{

    protected boolean visibleBackground = true;

    @LayoutComponent
    protected int slotHeight;
    
    @LayoutComponent
    protected List<ListEntry> content;
    
    protected DisplayList(){}
    
    public DisplayList(int xPos, int yPos, int width, int height, int slotHeight, List<ListEntry> content) {
        super(xPos, yPos, width, height);
        this.slotHeight = slotHeight;
        this.content = content;
    }
    
    @Override
    public DisplayList add(ListEntry object) {
        this.content.add(object);
        return this;
    }

    @Override
    public DisplayList addAll(ListEntry... objects) {
        this.content.addAll(Arrays.asList(objects));
        return this;
    }

    @Override
    public DisplayList remove(ListEntry object) {
        this.content.remove(object);
        return this;
    }

    @Override
    public DisplayList clear() {
        this.content.clear();
        return this;
    }

    @Override
    public List<ListEntry> getContent() {
        return content;
    }
    
    @Override
    public void onDraw(int mouseX, int mouseY, float partialTicks) {
        if(isVisibleBackground()) {
            drawListBackground();
        }
        drawListContent(mouseX, mouseY);
        super.onDraw(mouseX, mouseY, partialTicks);
    }
    
    protected void drawListBackground(){
        Renderer.drawRect(getX() - 1, getY() - 1, getX() + this.width + 1, getY() + this.height + 1, -6250336);
        Renderer.drawRect(getX(), getY(), getX() + this.width, getY() + this.height, -0xFFFFFF-1);
    }
    
    protected void drawListContent(int mouseX, int mouseY){
        for(int i = 0; i < content.size(); i++){
            ListEntry entry = content.get(i);
            int slotPosX = getX();
            int slotPosY = getY() + i * slotHeight;
            int slotWidth = this.width;
            int slotHeight = this.slotHeight;
            entry.onDraw(this, slotPosX, slotPosY, slotWidth, slotHeight, mouseX, mouseY);
        }
    }

    @Override
    public boolean onMouseClicked(int posX, int posY, int mouseButtonIndex, boolean overlap) {
        super.onMouseClicked(posX, posY, mouseButtonIndex, overlap);
        boolean clickedOnList = !overlap && Geometry.isDotInArea(getX(), getY(), this.width, this.height, posX, posY);
        if(clickedOnList){
            handleMouseClickList(posX, posY);
        }
        return clickedOnList;
    }
    
    protected void handleMouseClickList(int mouseX, int mouseY){
        for(int i = 0; i < content.size(); i++){
            ListEntry entry = content.get(i);
			entry.setSelected(false);
            int slotPosX = getX();
            int slotPosY = getY() + i * slotHeight;
            int slotWidth = this.width;
            int slotHeight = this.slotHeight;
            boolean clickedOnEntry = Geometry.isDotInArea(slotPosX, slotPosY, slotWidth, slotHeight, mouseX, mouseY);
            if(clickedOnEntry) entry.onClick(this, mouseX, mouseY);
        } 
    }

    @Override
    public DisplayList setId(String id) {
        assignId(id);
        return this;
    }

    public DisplayList setVisibleBackground(boolean visibleBackground) {
        this.visibleBackground = visibleBackground;
        return this;
    }

    public boolean isVisibleBackground(){
        return this.visibleBackground;
    }
}
