package ru.redenergy.gui.internal;

import net.minecraft.client.gui.GuiScreen;
import ru.redenergy.gui.api.IGuiPane;

public abstract class GuiPane extends GuiScreen implements IGuiPane{


	@Override
	public void onDraw(int mouseX, int mouseY, float partialTicks) {
		getComponentsList().forEach(component -> component.onDraw(mouseX, mouseY, partialTicks));
	}

	@Override
	public void onKeyTyped(char typedChar, int typedIndex) {
		getComponentsList().forEach(component -> component.onKeyTyped(typedChar, typedIndex));		
	}

	@Override
	public void onMouseClicked(int posX, int posY, int mouseButtonIndex) {
		getComponentsList().forEach(component -> component.onMouseClicked(posX, posY, mouseButtonIndex));
	}

	@Override
	public void onUpdate() {
		getComponentsList().forEach(component -> component.onUpdate());
	}

	@Override
	public void onClose() {
		getComponentsList().forEach(component -> component.onClose());
	}
	
	
	/**                   VANILLA CALLS                          */
	@Override
	public void drawScreen(int p_73863_1_, int p_73863_2_, float p_73863_3_) {
		onDraw(p_73863_1_, p_73863_2_, p_73863_3_);
	}

	@Override
	protected void keyTyped(char p_73869_1_, int p_73869_2_) {
		onKeyTyped(p_73869_1_, p_73869_2_);
	}

	@Override
	protected void mouseClicked(int p_73864_1_, int p_73864_2_, int p_73864_3_) {
		onMouseClicked(p_73864_1_, p_73864_2_, p_73864_3_);
	}

	@Override
	public void initGui() {
		onCreate();
	}

	@Override
	public void updateScreen() {
		onUpdate();
	}

	@Override
	public void onGuiClosed() {
		onClose();
	}

	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}


}
