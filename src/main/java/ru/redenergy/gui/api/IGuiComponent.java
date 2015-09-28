package ru.redenergy.gui.api;

/**
 * Represents components of the screen
 * 
 * @author RedEnergy
 */
public interface IGuiComponent extends IGui {

    /**
     * Called when component registered in pane
     */
    default void onRegistered(IGuiPane pane) {
        setPane(pane);
    }

    /**
     * Returns pane in which this component registered
     * 
     * @return parent pane - can be null if it hasn't been registered yet
     */
    IGuiPane getPane();

    void setPane(IGuiPane pane);
}
