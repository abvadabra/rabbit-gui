package ru.redenergy.gui.show;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.Display;

import net.minecraft.util.StringUtils;
import ru.redenergy.gui.base.ComponentContainer;
import ru.redenergy.gui.base.Stage;
import ru.redenergy.gui.component.IGuiComponent;

public abstract class Show implements IShow, ComponentContainer{

    protected List<IGuiComponent> components = new ArrayList();
    protected String id;
    protected int width, height;
    protected Stage stage;
    protected String title;
    
    private void updateDisplayTitle(){
        Display.setTitle("Minecraft 1.7.10" + " - " + title);
    }
    
    @Override
    public void setSize(int width, int height){
        this.width = width;
        this.height = height;
    }
    
    @Override
    public void onInit(){
        if(!StringUtils.isNullOrEmpty(title)){
            updateDisplayTitle();
        }
    }
    
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
        Display.setTitle("Minecraft 1.7.10");
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
    
    @Override
    public String getTitle(){
        return title;
    }
    
    @Override
    public void setTitle(String title){
        this.title = title;
        updateDisplayTitle();
    }
    
    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    /*
     * DO NOT CALL THIS METHOD. Use #setStage instead!
     */
    @Deprecated
    @Override
    public final void setParent(ComponentContainer c){}
    
    

}
