package com.rabbit.gui.component.control;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;

import org.lwjgl.opengl.GL11;

import com.rabbit.gui.component.GuiWidget;
import com.rabbit.gui.component.Shiftable;
import com.rabbit.gui.layout.LayoutComponent;
import com.rabbit.gui.render.Renderer;
import com.rabbit.gui.render.TextRenderer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.util.ResourceLocation;

/**
 * Simple button component <br>
 * Supported width: <b> 0 - 400 </b> (due to texture length it can't be larger)
 * <br>
 * Supported height: <b> 5 - INFINITY </b> <br>
 * 
 * Use {@link #setClickListener(ButtonClickListener)} to define action on button
 * pressed
 */
@LayoutComponent
public class PictureButton extends GuiWidget implements Shiftable {

	protected static final int DISABLED_STATE = 0;
	protected static final int IDLE_STATE = 1;
	protected static final int HOVER_STATE = 2;

	protected ResourceLocation buttonTexture = new ResourceLocation("textures/gui/widgets.png");

	private ResourceLocation texture;
	private int imageWidth;
	private int imageHeight;

	@LayoutComponent
	protected boolean isVisible = true;

	@LayoutComponent
	protected boolean isEnabled = true;

	protected ButtonClickListener onClick;

	/** Dummy constructor. Used in layout */
	private PictureButton() {
	}

	public PictureButton(int xPos, int yPos, int width, int height, ResourceLocation texture) {
		super(xPos, yPos, width, height);
		this.texture = texture;
		try {
			BufferedImage image = ImageIO
					.read(Minecraft.getMinecraft().getResourceManager().getResource(texture).getInputStream());
			this.imageWidth = image.getWidth();
			this.imageHeight = image.getHeight();
		} catch (IOException ioex) {
			throw new RuntimeException("Can't get resource", ioex);
		}

	}

	@Override
	public void onDraw(int mouseX, int mouseY, float partialTicks) {
		if (isVisible()) {
			prepareRender();
			if (!isEnabled()) {
				drawButton(DISABLED_STATE);
			} else if (isButtonUnderMouse(mouseX, mouseY)) {
				drawButton(HOVER_STATE);
			} else {
				drawButton(IDLE_STATE);
			}
			renderPicture();
		}
	}

	protected void prepareRender() {
		Minecraft.getMinecraft().getTextureManager().bindTexture(getButtonTexture());
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glEnable(GL11.GL_BLEND);
		OpenGlHelper.glBlendFunc(770, 771, 1, 0);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
	}

	protected void drawButton(int state) {
		Renderer.drawContinuousTexturedBox(getX(), getY(), 0, 46 + (20 * state), getWidth(), getHeight(), 200, 20, 2, 3,
				2, 2);
	}

	@Override
	public boolean onMouseClicked(int posX, int posY, int mouseButtonIndex, boolean overlap) {
		boolean clicked = isButtonUnderMouse(posX, posY) && isEnabled() && !overlap;
		if (clicked) {
			if (getClickListener() != null) {
				getClickListener().onClick(this);
			}
			playClickSound();
		}
		return clicked;
	}

	public boolean isButtonUnderMouse(int mouseX, int mouseY) {
		return mouseX >= getX() && mouseX <= getX() + getWidth() && mouseY >= getY() && mouseY <= getY() + getHeight();
	}

	/**
	 * Provided listener will be executed by pressing the button
	 * 
	 * @param onClicked
	 *            listener
	 * @return self
	 */
	public PictureButton setClickListener(ButtonClickListener onClicked) {
		this.onClick = onClicked;
		return this;
	}

	public ButtonClickListener getClickListener() {
		return onClick;
	}

	/**
	 * @return <code> true </code> if button would be rendered
	 */
	public boolean isVisible() {
		return isVisible;
	}

	/**
	 * @return <code> true</code> if button can be clicked
	 */
	public boolean isEnabled() {
		return isEnabled;
	}

	public PictureButton setIsVisible(boolean isVisible) {
		this.isVisible = isVisible;
		return this;
	}

	public PictureButton setIsEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
		return this;
	}

	public ResourceLocation getButtonTexture() {
		return buttonTexture;
	}

	public PictureButton setCustomTexture(ResourceLocation res) {
		this.buttonTexture = res;
		return this;
	}

	private void renderPicture() {
		GL11.glPushMatrix();
		GL11.glColor4f(1, 1, 1, 1);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		Minecraft.getMinecraft().renderEngine.bindTexture(texture);
		Renderer.drawTexturedModalRect(getX() + 1, getY(), 0, 0, getWidth(), getHeight(), getWidth() - 2,
				getHeight() - 2, 0);
		GL11.glPopMatrix();
	}

	protected void playClickSound() {
		Minecraft.getMinecraft().getSoundHandler()
				.playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
	}

	@Override
	public PictureButton setId(String id) {
		assignId(id);
		return this;
	}

	@FunctionalInterface
	public static interface ButtonClickListener {
		void onClick(PictureButton button);
	}

	@Override
	public void shiftX(int x) {
		this.setX(getX() + x);
	}

	@Override
	public void shiftY(int y) {
		this.setY(getY() + y);
	}
}
