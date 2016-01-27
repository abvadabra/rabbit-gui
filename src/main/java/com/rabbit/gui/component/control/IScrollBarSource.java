package com.rabbit.gui.component.control;

/**
 * Represents component which will override ScrollBar behavior, typically used in lists
 */
public interface IScrollBarSource {

    /**
     * Will be called if scrollbar handles mouse wheel, you can use ScrollBar#updateProgress to change scrollbar position
     * @param scrollBar - instance of ScrollBar component
     */
    void handleMouseWheel(ScrollBar scrollBar);

}
