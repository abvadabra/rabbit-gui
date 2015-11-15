package com.rabbit.gui.render;

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
     * @deprecated use {@link #renderString(int, int, String, TextAlignment)} with TextAlignment.CENTER instead
     */
    @Deprecated
    public static int renderCenteredString(int xPos, int yPos, String text) {
        return renderCenteredString(xPos, yPos, text, 0xFFFFFF);
    }
    
    /**
     * Renders string centered
     * 
     * @param xPos
     * @param yPos
     * @param text
     * @param color
     * @return X position of rendered string
     * @deprecated use {@link #renderString(int, int, String, TextAlignment)} with TextAlignment.CENTER instead
     */
    @Deprecated
    public static int renderCenteredString(int xPos, int yPos, String text, int color) {
        return renderString(xPos - getFontRenderer().getStringWidth(text) / 2, yPos, text, color, TextAlignment.LEFT);
    }


    /**
     * See {@link #renderString(int, int, String, int, boolean)}
     */
    public static int renderString(int xPos, int yPos, String text) {
        return renderString(xPos, yPos, text, 0xFFFFFF, TextAlignment.LEFT);
    }

    public static int renderString(int xPos, int yPos, String text, TextAlignment align){
        return renderString(xPos, yPos, text, 0xFFFFFF, align);
    }
    /**
     * See {@link #renderString(int, int, String, int, boolean)}
     */
    public static int renderString(int xPos, int yPos, String text, int color, TextAlignment align) {
        return renderString(xPos, yPos, text, color, false, align);
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
    public static int renderString(int xPos, int yPos, String text, int color, boolean shadow, TextAlignment align) {
        switch(align){
            case LEFT:
                return getFontRenderer().drawString(text, xPos, yPos, color, shadow);
            case CENTER:
                return getFontRenderer().drawString(text, xPos - getFontRenderer().getStringWidth(text) / 2, yPos, color, shadow);
            case RIGHT:
                return getFontRenderer().drawString(text, xPos - getFontRenderer().getStringWidth(text), yPos, color, shadow);
        }
        return -1;
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
