package com.rabbit.gui.component.list;

import java.util.Arrays;
import java.util.List;

import com.rabbit.gui.component.GuiComponent;
import com.rabbit.gui.component.MultipleModel;
import com.rabbit.gui.component.list.DisplayList.ListEntry;
import com.rabbit.gui.layout.LayoutComponent;
import com.rabbit.gui.render.Renderer;
import com.rabbit.gui.utils.GeometryUtils;

@LayoutComponent
public class DisplayList extends GuiComponent implements MultipleModel<ListEntry>{

    @LayoutComponent
    protected int xPos;
    
    @LayoutComponent
    protected int yPos;
    
    @LayoutComponent
    protected int width;
    
    @LayoutComponent
    protected int height;
    
    @LayoutComponent
    protected int slotHeight;
    
    @LayoutComponent
    protected List<ListEntry> content;
    
    private DisplayList(){}
    
    public DisplayList(int xPos, int yPos, int width, int height, int slotHeight, List<ListEntry> content) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.width = width;
        this.height = height;
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
        super.onDraw(mouseX, mouseY, partialTicks);
        Renderer.drawRect(this.xPos - 1, this.yPos - 1, this.xPos + this.width + 1, this.yPos + this.height + 1, -6250336);
        Renderer.drawRect(this.xPos, this.yPos, this.xPos + this.width, this.yPos + this.height, -0xFFFFFF-1);
        for(int i = 0; i < content.size(); i++){
            ListEntry entry = content.get(i);
            int slotPosX = this.xPos;
            int slotPosY = this.yPos + i * slotHeight;
            int slotWidth = this.width;
            int slotHeight = this.slotHeight;
            entry.onDraw(this, slotPosX, slotPosY, slotWidth, slotHeight, mouseX, mouseY);
        }
    }

    @Override
    public void onMouseClicked(int posX, int posY, int mouseButtonIndex) {
        super.onMouseClicked(posX, posY, mouseButtonIndex);
        boolean clickedOnList = GeometryUtils.isDotInArea(this.xPos, this.yPos, this.width, this.height, posX, posY);
        if(clickedOnList){
            for(int i = 0; i < content.size(); i++){
                ListEntry entry = content.get(i);
                int slotPosX = this.xPos;
                int slotPosY = this.yPos + i * slotHeight;
                int slotWidth = this.width;
                int slotHeight = this.slotHeight;
                boolean clickedOnEntry = GeometryUtils.isDotInArea(slotPosX, slotPosY, slotWidth, slotHeight, posX, posY);
                if(clickedOnEntry) entry.onClick(this, posX, posY);
            }
        }
    }

    @Override
    public DisplayList setId(String id) {
        assignId(id);
        return this;
    }
    
    public interface ListEntry{

        public default void onDraw(DisplayList list, int posX, int posY, int width, int height, int mouseX, int mouseY){}
        
        public default void onClick(DisplayList list, int mouseX, int mouseY){}
    
    }

}
