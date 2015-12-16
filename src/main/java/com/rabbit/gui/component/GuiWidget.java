package com.rabbit.gui.component;

import java.util.ArrayList;
import java.util.List;

import com.rabbit.gui.base.WidgetContainer;
import com.rabbit.gui.exception.IdAlreadyRegisteredException;
import com.rabbit.gui.layout.LayoutComponent;

/**
 * Represents components of the screen
 * 
 * @author RedEnergy
 */
public abstract class GuiWidget implements IGui, WidgetContainer {

    protected WidgetContainer parent;
    protected List<IGui> components;
    
    @LayoutComponent
    protected String id;
    
    @LayoutComponent
    protected int x;
    
    @LayoutComponent
    protected int y;
    
    @LayoutComponent
    protected int width;
    
    @LayoutComponent
    protected int height;

    protected GuiWidget(){
        this.x = -1;
        this.y = -1;
        this.width = -1;
        this.height = -1;
    }
    
    protected GuiWidget(int x, int y, int width, int height){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }
    
    /**
     * Called on every render tick
     * 
     * @param mouseX
     * @param mouseY
     * @param partialTicks
     */
    @Override
    public void onDraw(int mouseX, int mouseY, float partialTicks) {
        getComponentsList().forEach(com -> com.onDraw(mouseX, mouseY, partialTicks));
    }

    /**
     * Called when user presses key on keyboard
     * 
     * @param typedChar
     * @param typedIndex
     */
    @Override
    public void onKeyTyped(char typedChar, int typedIndex) {
        getComponentsList().forEach(com -> com.onKeyTyped(typedChar, typedIndex));
    }

    /**
     * Called when user clicks mouse
     * 
     * @param posX
     * @param posY
     * @param mouseButtonIndex
     *
     * @return <code>true</code> if the element has been clicked
     */
    @Override
    public boolean onMouseClicked(int posX, int posY, int mouseButtonIndex, boolean overlap) {
        boolean clicked = false;
        for(IGui com : getComponentsList()) {
            clicked = com.onMouseClicked(posX, posY, mouseButtonIndex, clicked) || clicked;
        }
        return clicked;
    }

    @Override
    public void onMouseRelease(int mouseX, int mouseY) {
        getComponentsList().forEach(com -> com.onMouseRelease(mouseX, mouseY));
    }
    
    /**
     * Called every update tick (usually 20 times in second)
     */
    @Override
    public void onUpdate() {
        getComponentsList().forEach(com -> com.onUpdate());        
    }

    /**
     * Called when screen is about to be closed
     */
    @Override
    public void onClose() {
        getComponentsList().forEach(com -> com.onClose());
    }
    
    /**
     * Called then mouse moved or scrolled
     */
    @Override
    public void onMouseInput() {
        getComponentsList().forEach(com -> com.onMouseInput());
    }

    /**
     * Returns pane in which this component registered
     * 
     * @return parent pane - can be null if it hasn't been registered yet
     */
    @Override
    public WidgetContainer getParent() {
        return parent;
    }

    @Override
    public void setParent(WidgetContainer pane) {
        this.parent = pane;
    }

    /**
     * Called on every screen resize. All components recommended to be
     * registered in this moment
     */
    @Override
    public void setup() {
        getComponentsList().clear();
    }

    /**
     * Registers component as a part of pane
     * 
     * @param component
     */
    @Override
    public void registerComponent(IGui component) {
        this.getComponentsList().add(component);
        if(component instanceof WidgetContainer) ((WidgetContainer) component).onRegistered(this);
    }

    /**
     * Called when component registered in pane
     */
    @Override
    public void onRegistered(WidgetContainer pane) {
        setParent(pane);
    }

    
    @Override
    public List<IGui> getComponentsList() {
        if(components == null){
            components = new ArrayList<>();
        }
        return components;
    }
    
    
    public int getX() {
        return x;
    }

    public GuiWidget setX(int x) {
        this.x = x;
        return this;
    }

    public int getY() {
        return y;
    }

    public GuiWidget setY(int y) {
        this.y = y;
        return this;
    }

    public int getWidth() {
        return width;
    }

    public GuiWidget setWidth(int width) {
        this.width = width;
        return this;
    }

    public int getHeight() {
        return height;
    }

    public GuiWidget setHeight(int height) {
        this.height = height;
        return this;
    }

    /**
     * Returns and id component, can be <code>null</code>
     * @return
     */
    @Override
    public String getId() {
        return id;
    }
    
    /**
     * Sets this component id to provided
     * @return self
     */
    @Override
    public <T> IGui setId(String id) {
        assignId(id);
        return this;
    }
    
    protected final void assignId(String id){
        if(findComponentById(id) != null) throw new IdAlreadyRegisteredException("Id " + id + " already occupied");
        this.id = id;
    }

}
