package ru.redenergy.gui.render;

import net.minecraft.client.Minecraft;

public class TextRenderer {
	
	public static void renderCenteredString(int xPos, int yPos, String text){
		renderString(xPos - Minecraft.getMinecraft().fontRenderer.getStringWidth(text) / 2, yPos, text);
	}
	
	public static void renderString(int xPos, int yPos, String text){
		renderString(xPos, yPos, text, 0xFFFFFF);
	}
	
	public static void renderString(int xPos, int yPos, String text, int color){
		renderString(xPos, yPos, text, color, false);
	}
	
	public static void renderString(int xPos, int yPos, String text, int color, boolean shadow){
		Minecraft.getMinecraft().fontRenderer.drawString(text, xPos, yPos, color, shadow);
	}
}
