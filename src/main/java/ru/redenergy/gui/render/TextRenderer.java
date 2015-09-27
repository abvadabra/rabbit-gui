package ru.redenergy.gui.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;

public class TextRenderer {
	
	public static int renderCenteredString(int xPos, int yPos, String text){
		return renderString(xPos - getFontRenderer().getStringWidth(text) / 2, yPos, text);
	}
	
	public static int renderString(int xPos, int yPos, String text){
		return renderString(xPos, yPos, text, 0xFFFFFF);
	}
	
	public static int renderString(int xPos, int yPos, String text, int color){
		return renderString(xPos, yPos, text, color, false);
	}
	
	public static int renderString(int xPos, int yPos, String text, int color, boolean shadow){
		return getFontRenderer().drawString(text, xPos, yPos, color, shadow);
	}
	
	public static FontRenderer getFontRenderer(){
		return Minecraft.getMinecraft().fontRenderer;
	}
}
