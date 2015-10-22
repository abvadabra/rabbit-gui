package com.rabbit.gui.component;

import java.util.List;

import com.rabbit.gui.base.ComponentContainer;

/**
 * Represents components of the screen
 * 
 * @author RedEnergy
 */
public interface IGuiComponent {
    
    /**
     * Called on every render tick
     * 
     * @param mouseX
     * @param mouseY
     * @param partialTicks
     */
    void onDraw(int mouseX, int mouseY, float partialTicks);

    /**
     * Called when user presses key on keyboard
     * 
     * @param typedChar
     * @param typedIndex
     */
    void onKeyTyped(char typedChar, int typedIndex);

    /**
     * Called when user clicks mouse
     * 
     * @param posX
     * @param posY
     * @param mouseButtonIndex
     */
    void onMouseClicked(int posX, int posY, int mouseButtonIndex);

    /**
     * Called every update tick (usually 20 times in second)
     */
    void onUpdate();

    /**
     * Called when screen is about to be closed
     */
    void onClose();
    
    /**
     * Called then mouse moved or scrolled
     */
    void onMouseInput();
    
    /**
     * Called on every screen resize. All components recommended to be
     * registered in this moment
     */
    void setup();

    /**
     * Registers component as a part of pane
     * 
     * @param component
     */
    void registerComponent(IGuiComponent component);

    /**
     * Returns and id component, can be <code>null</code>
     * @return
     */
    String getId();
    
    /**
     * Sets this component id to provided
     * @return self
     */
    <T> IGuiComponent setId(String id);
    
    /**
     * Called when component registered in pane
     */
    default void onRegistered(ComponentContainer pane) {
        setParent(pane);
    }

    /**
     * Returns pane in which this component registered
     * 
     * @return parent pane - can be null if it hasn't been registered yet
     */
    ComponentContainer getParent();

    void setParent(ComponentContainer pane);
}
