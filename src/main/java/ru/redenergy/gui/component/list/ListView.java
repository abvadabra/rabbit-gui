package ru.redenergy.gui.component.list;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.lwjgl.util.Rectangle;

import ru.redenergy.gui.api.IGuiComponent;
import ru.redenergy.gui.component.GuiComponent;

public abstract class ListView<T> extends GuiComponent {

    protected Rectangle shape;
    protected List<T> content;
    
    public ListView(int xPos, int yPos, int width, int height, T ... values){
        this(new Rectangle(xPos, yPos, width, height), values);
    }
    
    public ListView(Rectangle shape, T ... values){
        this.shape = shape;
        this.content = new ArrayList(Arrays.asList(values));
    }

    public abstract ListView<T> add(T object);
    
    public abstract ListView<T> addAll(T ... objects);
    
    public abstract ListView<T> remove(T object);
    
    public abstract ListView<T> clear();
    
    public List<T> getContent(){
        return Collections.unmodifiableList(content);
    }
    
    @Override
    public ListView<T> setId(String id) {
        assignId(id);
        return this;
    }
}
