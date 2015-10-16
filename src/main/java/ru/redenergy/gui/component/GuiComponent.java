package ru.redenergy.gui.component;

import java.util.ArrayList;
import java.util.List;

import ru.redenergy.gui.base.ComponentContainer;
import ru.redenergy.gui.exception.IdAlreadyRegisteredException;
import ru.redenergy.gui.layout.LayoutComponent;

public abstract class GuiComponent implements IGuiComponent, ComponentContainer {

    protected ComponentContainer parent;
    protected List<IGuiComponent> components;
    
    @LayoutComponent
    protected String id;

    public void onDraw(int mouseX, int mouseY, float partialTicks) {
        getComponentsList().forEach(com -> com.onDraw(mouseX, mouseY, partialTicks));
    }

    public void onKeyTyped(char typedChar, int typedIndex) {
        getComponentsList().forEach(com -> com.onKeyTyped(typedChar, typedIndex));
    }

    public void onMouseClicked(int posX, int posY, int mouseButtonIndex) {
        getComponentsList().forEach(com -> com.onMouseClicked(posX, posY, mouseButtonIndex));
    }

    public void onUpdate() {
        getComponentsList().forEach(com -> com.onUpdate());        
    }

    public void onClose() {
        getComponentsList().forEach(com -> com.onClose());
    }
    
    @Override
    public ComponentContainer getParent() {
        return parent;
    }

    @Override
    public void setParent(ComponentContainer pane) {
        this.parent = pane;
    }
    
    @Override
    public void setup() {
        getComponentsList().clear();
    }

    @Override
    public void registerComponent(IGuiComponent component) {
        this.getComponentsList().add(component);
    }

    @Override
    public List<IGuiComponent> getComponentsList() {
        if(components == null){
            components = new ArrayList<>();
        }
        return components;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public <T> IGuiComponent setId(String id) {
        assignId(id);
        return this;
    }
    
    protected final void assignId(String id){
        if(findComponentById(id) != null) throw new IdAlreadyRegisteredException("Id " + id + " already occupied");
        this.id = id;
    }

}
