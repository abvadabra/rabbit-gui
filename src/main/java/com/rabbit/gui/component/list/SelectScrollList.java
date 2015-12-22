package com.rabbit.gui.component.list;

import com.rabbit.gui.component.list.entries.ListEntry;
import com.rabbit.gui.component.list.entries.SelectListEntry;

import java.util.List;
import java.util.stream.Collectors;

public class SelectScrollList extends ScrollableDisplayList{

    protected List<SelectListEntry> content;

    public SelectScrollList(int xPos, int yPos, int width, int height, int slotHeight, List<ListEntry> content) {
        super(xPos, yPos, width, height, slotHeight, content);
    }

    public List<SelectListEntry> getSelected(){
        return content.stream().filter(SelectListEntry::isSelected).collect(Collectors.toList());
    }

}
