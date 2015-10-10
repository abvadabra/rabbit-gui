package ru.redenergy.gui.base;

import java.util.ArrayList;
import java.util.List;

import ru.redenergy.gui.component.IGuiComponent;

public abstract class Show implements IShow, ComponentContainer{

    private List<IGuiComponent> components = new ArrayList();
    protected String id;
    protected int width, height;
    protected Stage stage;
    
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

    @Override
    public ComponentContainer getParent() {
        return null;
    }

    @Override
    public Stage getStage() {
        return stage;
    }

    @Override
    public void setStage(Stage stage) {
        this.stage = stage;
    }
    
    /*
     * DO NOT CALL THIS METHOD. Use #setStage instead!
     */
    @Deprecated
    @Override
    public final void setParent(ComponentContainer c){}
    
    

}
