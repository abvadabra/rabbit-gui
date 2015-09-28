package ru.redenergy.gui.component;

import ru.redenergy.gui.api.IGuiComponent;
import ru.redenergy.gui.api.IGuiPane;

public abstract class GuiComponent implements IGuiComponent {
	
	protected IGuiPane pane;
	
	@Override
	public void onDraw(int mouseX, int mouseY, float partialTicks){}

	@Override
	public void onKeyTyped(char typedChar, int typedIndex) {}

	@Override
	public void onMouseClicked(int posX, int posY, int mouseButtonIndex) {}

	@Override
	public void onUpdate() {}

	@Override
	public void onClose() {}

	@Override
	public IGuiPane getPane() {
		return pane;
	}

	@Override
	public void setPane(IGuiPane pane) {
		this.pane = pane;
	}

}
