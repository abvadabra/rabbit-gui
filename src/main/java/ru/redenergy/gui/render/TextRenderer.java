package ru.redenergy.gui.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;

public class TextRenderer {

    /**
     * Renders string centered
     * 
     * @param xPos
     * @param yPos
     * @param text
     * @return X position of rendered string
     */
    public static int renderCenteredString(int xPos, int yPos, String text) {
        return renderString(xPos - getFontRenderer().getStringWidth(text) / 2, yPos, text);
    }

    /**
     * See {@link #renderString(int, int, String, int, boolean)}
     */
    public static int renderString(int xPos, int yPos, String text) {
        return renderString(xPos, yPos, text, 0xFFFFFF);
    }

    /**
     * See {@link #renderString(int, int, String, int, boolean)}
     */
    public static int renderString(int xPos, int yPos, String text, int color) {
        return renderString(xPos, yPos, text, color, false);
    }

    /**
     * Renders string at the givens x and y
     * 
     * @param xPos
     * @param yPos
     * @param text
     * @param color
     * @param shadow
     * @return X position of rendered string
     */
    public static int renderString(int xPos, int yPos, String text, int color, boolean shadow) {
        return getFontRenderer().drawString(text, xPos, yPos, color, shadow);
    }

    /**
     * Returns main FontRenderer (commonly picked from Minecraft.class instance)
     * 
     * @return {@link FontRenderer} instance
     */
    public static FontRenderer getFontRenderer() {
        return Minecraft.getMinecraft().fontRenderer;
    }
}
