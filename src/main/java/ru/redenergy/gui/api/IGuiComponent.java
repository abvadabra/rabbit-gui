package ru.redenergy.gui.api;

public interface IGuiComponent {
	
	void onCreate();
	
	void onDraw(int mouseX, int mouseY, float partialTicks);
	
	void onKeyTyped(char typedChar, int typedIndex);
	
	void onMouseClicked(int posX, int posY, int mouseButtonIndex);
	
	void onUpdate();
	
	void onClose();
	
}
