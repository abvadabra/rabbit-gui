package ru.redenergy.gui.api;

/**
 * Represents part of graphic user interface
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
     * Called every update tick (usually 20 times in second)
     */
    void onUpdate();

    /**
     * Called when screen is about to be closed
     */
    void onClose();

}
