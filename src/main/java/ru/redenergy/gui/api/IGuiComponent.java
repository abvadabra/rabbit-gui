package ru.redenergy.gui.api;

import java.util.List;

/**
 * Represents components of the screen
 * 
 * @author RedEnergy
 */
public interface IGuiComponent extends IGui {

    
    
    @Override
    default void onDraw(int mouseX, int mouseY, float partialTicks) {
        getComponentsList().forEach(com -> com.onDraw(mouseX, mouseY, partialTicks));
    }

    @Override
    default void onKeyTyped(char typedChar, int typedIndex) {
        getComponentsList().forEach(com -> com.onKeyTyped(typedChar, typedIndex));
    }

    @Override
    default void onMouseClicked(int posX, int posY, int mouseButtonIndex) {
        getComponentsList().forEach(com -> com.onMouseClicked(posX, posY, mouseButtonIndex));
    }

    @Override
    default void onUpdate() {
        getComponentsList().forEach(com -> com.onUpdate());        
    }

    @Override
    default void onClose() {
        getComponentsList().forEach(com -> com.onClose());
    }

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
     * Returns list of all registered components
     * 
     * @return registered components
     */
    List<IGuiComponent> getComponentsList();
    
    /**
     * Called when component registered in pane
     */
    default void onRegistered(IGuiComponent pane) {
        setParent(pane);
    }

    /**
     * Returns pane in which this component registered
     * 
     * @return parent pane - can be null if it hasn't been registered yet
     */
    IGuiComponent getParent();

    void setParent(IGuiComponent pane);
}
