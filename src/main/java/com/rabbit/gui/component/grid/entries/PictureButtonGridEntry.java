package com.rabbit.gui.component.grid.entries;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

import org.lwjgl.opengl.GL11;

import com.rabbit.gui.component.control.Button;
import com.rabbit.gui.component.control.PictureButton;
import com.rabbit.gui.component.control.Button.ButtonClickListener;
import com.rabbit.gui.component.grid.Grid;
import com.rabbit.gui.component.list.DisplayList;
import com.rabbit.gui.render.TextAlignment;
import com.rabbit.gui.render.TextRenderer;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

import com.rabbit.gui.render.Renderer;

/**
 * Implementation of the ListEntry witch draws the given string in the center of entry slot
 */
public class PictureButtonGridEntry extends Button implements GridEntry  {

    /**
     * Listener which would be called when user click the entry
     */
    private OnClickListener listener;
    
    private ResourceLocation pictureTexture;
	private int imageWidth;
	private int imageHeight;

    public PictureButtonGridEntry(int width, int height, ResourceLocation texture){
    	this(width, height, texture, null);
    }

    public PictureButtonGridEntry(int width, int height, ResourceLocation texture, OnClickListener listener){
    	super(0, 0, width, height, "");
    	this.pictureTexture = texture;
		try {
			BufferedImage image = ImageIO
					.read(Minecraft.getMinecraft().getResourceManager().getResource(texture).getInputStream());
			this.imageWidth = image.getWidth();
			this.imageHeight = image.getHeight();
		} catch (IOException ioex) {
			throw new RuntimeException("Can't get resource", ioex);
		}
        this.listener = listener;
    }

    @Override
    public void onDraw(Grid grid, int posX, int posY, int width, int height, int mouseX, int mouseY) {
    	if(this.getX() != posX){
    		this.setX(posX);
    	}
    	if(this.getY() != posY){
    		this.setY(posY);
    	}
    	if(this.getWidth() != width){
    		this.setWidth(width);
    	}
    	if(this.getHeight() != height){
    		this.setHeight(height);
    	}
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

	public PictureButtonGridEntry setPictureTexture(ResourceLocation res) {
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
		Renderer.drawTexturedModalRect(getX(), getY(), 0, 0, getWidth(), getHeight(), getWidth() - 2,
				getHeight() - 2, 0);
		GL11.glPopMatrix();
	}
	
	public PictureButtonGridEntry doesDrawHoverText(boolean state) {
		this.drawHoverText = state;
		return this;
	}

	public PictureButtonGridEntry addHoverText(String text) {
		this.hoverText.add(text);
		return this;
	}

	public PictureButtonGridEntry setHoverText(List<String> text) {
		this.hoverText = text;
		return this;
	}

	public List<String> getHoverText() {
		return this.hoverText;
	}
	
    public PictureButtonGridEntry setClickListener(OnClickListener onClicked) {
        this.listener = onClicked;
        return this;
    }

    @Override
    public void onClick(Grid grid, int mouseX, int mouseY) {
        if(listener != null) listener.onClick(this, grid, mouseX, mouseY);
    }

    public static interface OnClickListener{
        void onClick(PictureButtonGridEntry entry, Grid grid, int mouseX, int mouseY);
    }
}
