package ru.redenergy.gui.component.base;

import java.util.ArrayList;
import java.util.List;

import ru.redenergy.gui.api.IGuiComponent;
import ru.redenergy.gui.api.IShow;

public abstract class Show implements IShow{

    private List<IGuiComponent> components = new ArrayList();
    protected String id;
    protected int width, height;
    
    
    @Override
    public void setSize(int width, int height){
        this.width = width;
        this.height = height;
    }
    
    @Override
    public void onInit(){}
    
    @Override
    public void setup() {
        getComponentsList().forEach(com -> com.setup());
    }

    @Override
    public void registerComponent(IGuiComponent component) {
        components.add(component);
    }

    @Override
    public List<IGuiComponent> getComponentsList() {
        return components;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public <T> IGuiComponent setId(String id) {
        this.id = id;
        return this;
    }

    @Deprecated
    @Override
    public IGuiComponent getParent() {return null;}

    @Deprecated
    @Override
    public void setParent(IGuiComponent pane) {}

    @Override
    public void onDraw(int mouseX, int mouseY, float partialTicks) {
        getComponentsList().forEach(com -> com.onDraw(mouseX, mouseY, partialTicks));
    }

    @Override
    public void onKeyTyped(char typedChar, int typedIndex) {
        getComponentsList().forEach(com -> com.onKeyTyped(typedChar, typedIndex));        
    }

    @Override
    public void onMouseClicked(int posX, int posY, int mouseButtonIndex) {
        getComponentsList().forEach(com -> com.onMouseClicked(posX, posY, mouseButtonIndex));
    }

    @Override
    public void onUpdate() {
        getComponentsList().forEach(com -> com.onUpdate());
    }

    @Override
    public void onClose() {
        getComponentsList().forEach(com -> com.onClose());
    }

}
