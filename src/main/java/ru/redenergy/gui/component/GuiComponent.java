package ru.redenergy.gui.component;

import java.util.ArrayList;
import java.util.List;

import ru.redenergy.gui.api.IGuiComponent;

public abstract class GuiComponent implements IGuiComponent {

    protected IGuiComponent parent;
    protected List<IGuiComponent> components = new ArrayList<>();

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
    
    

}
