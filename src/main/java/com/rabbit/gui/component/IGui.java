package com.rabbit.gui.component;

import java.util.List;

import com.rabbit.gui.base.WidgetContainer;

/**
 * Represents components of the screen
 * 
 * @author RedEnergy
 */
public interface IGui {
    
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
     * Called when mouse is released
     * 
     * @param mouseX
     * @param mouseY
     */
    void onMouseRelease(int mouseX, int mouseY);

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
    void registerComponent(IGui component);

    /**
     * Returns and id component, can be <code>null</code>
     * @return
     */
    String getId();
    
    /**
     * Sets this component id to provided
     * @return self
     */
    <T> IGui setId(String id);
    
    /**
     * Called when component registered in pane
     */
    default void onRegistered(WidgetContainer pane) {
        setParent(pane);
    }

    /**
     * Returns pane in which this component registered
     * 
     * @return parent pane - can be null if it hasn't been registered yet
     */
    WidgetContainer getParent();

    void setParent(WidgetContainer pane);
}
