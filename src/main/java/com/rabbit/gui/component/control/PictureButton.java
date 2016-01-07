package com.rabbit.gui.component.control;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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

@LayoutComponent
public class PictureButton extends Button {

	private ResourceLocation pictureTexture;
	private int imageWidth;
	private int imageHeight;

	/** Dummy constructor. Used in layout */
	private PictureButton() {
		super();
	}

	public PictureButton(int xPos, int yPos, int width, int height, ResourceLocation texture) {
		super(xPos, yPos, width, height, "");
		this.pictureTexture = texture;
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
				renderPicture();
			} else if (isButtonUnderMouse(mouseX, mouseY)) {
				drawButton(HOVER_STATE);
				renderPicture();
				if (this.drawHoverText) {
					Renderer.drawHoveringText(this.hoverText, mouseX, mouseY);
				}
			} else {
				drawButton(IDLE_STATE);
				renderPicture();
			}
		}
	}

	public ResourceLocation getPictureTexture() {
		return pictureTexture;
	}

	public PictureButton setPictureTexture(ResourceLocation res) {
		this.pictureTexture = res;
		return this;
	}

	private void renderPicture() {
		GL11.glPushMatrix();
		GL11.glColor4f(1, 1, 1, 1);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		Minecraft.getMinecraft().renderEngine.bindTexture(pictureTexture);
		Renderer.drawTexturedModalRect(getX() + 1, getY(), 0, 0, getWidth(), getHeight(), getWidth() - 2,
				getHeight() - 2, 0);
		GL11.glPopMatrix();
	}

}
