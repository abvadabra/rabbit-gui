package ru.redenergy.gui.component;

import java.util.ArrayList;
import java.util.List;

import ru.redenergy.gui.api.IGuiComponent;
import ru.redenergy.gui.api.exception.IdAlreadyRegisteredException;

public abstract class GuiComponent implements IGuiComponent {

    protected IGuiComponent parent;
    protected List<IGuiComponent> components = new ArrayList<>();
    
    protected String id;

    @Override
    public IGuiComponent getParent() {
        return parent;
    }

    @Override
    public void setParent(IGuiComponent pane) {
        this.parent = pane;
    }
    
    @Override
    public void setup() {
        components.clear();
    }

    @Override
    public void registerComponent(IGuiComponent component) {
        this.components.add(component);
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
        assignId(id);
        return this;
    }
    
    protected final void assignId(String id){
        if(findComponentById(id) != null) throw new IdAlreadyRegisteredException("Id " + id + " already occupied");
        this.id = id;
    }
    
    
    

}
