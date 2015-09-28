package ru.redenergy.gui.api;

import java.util.List;

/**
 * Represents screen with it's components
 * @author RedEnergy
 */
public interface IGuiPane extends IGui{
	
	/**
	 * Called when gui displayed for the first time
	 */
	void onInit();
	
	/**
	 * Called on every screen resize. All components recommended to be registered in this moment
	 */
	void setupPane();
	
	/**
	 * Registers component as a part of pane
	 * @param component
	 */
	void registerComponent(IGuiComponent component);
	
	
	/**
	 * Returns list of all registered components
	 * @return registered components
	 */
	List<IGuiComponent> getComponentsList();
}
