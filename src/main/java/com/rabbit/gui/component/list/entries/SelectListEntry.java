package com.rabbit.gui.component.list.entries;

import com.rabbit.gui.component.list.DisplayList;
import com.rabbit.gui.render.Renderer;

public abstract class SelectListEntry implements ListEntry {

    protected boolean selected = false;

    @Override
    public void onDraw(DisplayList list, int posX, int posY, int width, int height, int mouseX, int mouseY) {
        if(isSelected()){
            Renderer.drawRect(posX, posY, posX + width, posY + height,  0x7FA9A9FF);
        }
    }

    @Override
    public void onClick(DisplayList list, int mouseX, int mouseY) {
        this.selected = !selected;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setIsSelected(boolean selected) {
        this.selected = selected;
    }
}
