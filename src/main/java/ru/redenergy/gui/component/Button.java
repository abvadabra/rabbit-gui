package ru.redenergy.gui.component;

import java.util.stream.IntStream;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.util.ResourceLocation;
import ru.redenergy.gui.api.IGuiComponent;
import ru.redenergy.gui.render.Renderer;

public class Button implements IGuiComponent{

	private int width;
	private int height;
	private int xPos;
	private int yPos;
	private String title;
	private boolean isVisible = true;
	private boolean isEnabled = true;
	private Runnable onClick;
	public ResourceLocation buttonTexture = new ResourceLocation("textures/gui/widgets.png");
	private static final int DISABLED_STATE = 0;
	private static final int IDLE_STATE = 1;
	private static final int HOVER_STATE = 2;
	
	public Button(int xPos, int yPos, int width, int height, String title){
		this.xPos = xPos;
		this.yPos = yPos;
		this.width = width;
		this.height = height;
		this.title = title;
	}
	
	@Override
	public void onCreate() {}

	@Override
	public void onDraw(int mouseX, int mouseY, float partialTicks) {
		if(isVisible()){
			Minecraft.getMinecraft().getTextureManager().bindTexture(buttonTexture);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			GL11.glEnable(GL11.GL_BLEND);
			OpenGlHelper.glBlendFunc(770, 771, 1, 0);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			if(!isEnabled()){
				drawButton(DISABLED_STATE);
			} else if(isButtonUnderMouse(mouseX, mouseY)){
				drawButton(HOVER_STATE);
			} else {
				drawButton(IDLE_STATE);
			}
		}
	}
	
	private void drawButton(int state){
		Renderer.drawTexturedModalRect(this.xPos, this.yPos, 0, 46 + (state * 20), this.width, 2);
		Renderer.drawTexturedModalRect(this.xPos, this.yPos + this.height - 3, 0, 46 + (state * 20) + 17, this.width, 3);
		IntStream.range(0, this.height - 5).forEach(idx -> Renderer.drawTexturedModalRect(this.xPos, this.yPos + 2 + (1 * idx), 0, 46 + (state * 20) + 2 + idx % 15, this.width, 1));
		
	}
	
	@Override
	public void onMouseClicked(int posX, int posY, int mouseButtonIndex) {
		if(isButtonUnderMouse(posX, posY) && isEnabled()){
			if(getClickListener() != null) getClickListener().run();
			playClickSound();
		}
	}


	@Override
	public void onKeyTyped(char typedChar, int typedIndex) {}

	@Override
	public void onUpdate(){}

	@Override
	public void onClose() {}

	public boolean isButtonUnderMouse(int mouseX, int mouseY){
		return mouseX >= xPos && mouseX <= xPos + width &&
				mouseY >= yPos && mouseY <= yPos + height;
	}
	
	public Button setClickListener(Runnable onClicked){
		this.onClick = onClicked;
		return this;
	}
	
	public Runnable getClickListener(){
		return onClick;
	}
	
	public boolean isVisible(){
		return isVisible;
	}
	
	public boolean isEnabled(){
		return isEnabled;
	}
	
	public Button setIsVisible(boolean isVisible){
		this.isVisible = isVisible;
		return this;
	}
	
	public Button setIsEnabled(boolean isEnabled){
		this.isEnabled = isEnabled;
		return this;
	}
	
	public ResourceLocation getButtonTexture(){
		return buttonTexture;
	}
	
	public Button setCustomTexture(ResourceLocation res){
		this.buttonTexture = res;
		return this;
	}
	
	public void playClickSound(){
		Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
	}

}
