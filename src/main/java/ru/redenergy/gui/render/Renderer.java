package ru.redenergy.gui.render;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;

public class Renderer {

    /**
     * Draws rectangle with the previously binded texture
     * 
     * @param posX
     *            - Position on the screen for X-axis
     * @param posY
     *            - Position on the screen for Y-axis
     * @param uPos
     *            - X position of image on binded texture
     * @param vPos
     *            - Y position of image on binded texture
     * @param width
     *            - width of rectangle
     * @param height
     *            - height of rectangle
     */
    public static void drawTexturedModalRect(int posX, int posY, int uPos, int vPos, int width, int height) {
        float f = 0.00390625F;
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV((double) (posX + 0), (double) (posY + height), (double) 0,
                (double) ((float) (uPos + 0) * f), (double) ((float) (vPos + height) * f));
        tessellator.addVertexWithUV((double) (posX + width), (double) (posY + height), (double) 0,
                (double) ((float) (uPos + width) * f), (double) ((float) (vPos + height) * f));
        tessellator.addVertexWithUV((double) (posX + width), (double) (posY + 0), (double) 0,
                (double) ((float) (uPos + width) * f), (double) ((float) (vPos + 0) * f));
        tessellator.addVertexWithUV((double) (posX + 0), (double) (posY + 0), (double) 0,
                (double) ((float) (uPos + 0) * f), (double) ((float) (vPos + 0) * f));
        tessellator.draw();
    }

    /**
     * Draws solid rectangle with the given color with top left point at xTop,
     * yTop and bottom right point at xBot, yBot
     * 
     * @param xTop
     * @param yTop
     * @param xBot
     * @param yBot
     * @param color
     */
    public static void drawRect(int xTop, int yTop, int xBot, int yBot, int color) {
        int temp;
        if (xTop < xBot) {
            temp = xTop;
            xTop = xBot;
            xBot = temp;
        }
        if (yTop < yBot) {
            temp = yTop;
            yTop = yBot;
            yBot = temp;
        }
        float f3 = (float) (color >> 24 & 255) / 255.0F;
        float f = (float) (color >> 16 & 255) / 255.0F;
        float f1 = (float) (color >> 8 & 255) / 255.0F;
        float f2 = (float) (color & 255) / 255.0F;
        Tessellator tessellator = Tessellator.instance;
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        GL11.glColor4f(f, f1, f2, f3);
        tessellator.startDrawingQuads();
        tessellator.addVertex((double) xTop, (double) yBot, 0.0D);
        tessellator.addVertex((double) xBot, (double) yBot, 0.0D);
        tessellator.addVertex((double) xBot, (double) yTop, 0.0D);
        tessellator.addVertex((double) xTop, (double) yTop, 0.0D);
        tessellator.draw();
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_BLEND);
    }

    public static void drawRectWithSpecialGL(int xTop, int yTop, int xBot, int yBot, int color, Runnable specialGL) {
        int temp;
        if (xTop < xBot) {
            temp = xTop;
            xTop = xBot;
            xBot = temp;
        }
        if (yTop < yBot) {
            temp = yTop;
            yTop = yBot;
            yBot = temp;
        }
        float f3 = (float) (color >> 24 & 255) / 255.0F;
        float f = (float) (color >> 16 & 255) / 255.0F;
        float f1 = (float) (color >> 8 & 255) / 255.0F;
        float f2 = (float) (color & 255) / 255.0F;
        Tessellator tessellator = Tessellator.instance;
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        specialGL.run();
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        GL11.glColor4f(f, f1, f2, f3);
        tessellator.startDrawingQuads();
        tessellator.addVertex((double) xTop, (double) yBot, 0.0D);
        tessellator.addVertex((double) xBot, (double) yBot, 0.0D);
        tessellator.addVertex((double) xBot, (double) yTop, 0.0D);
        tessellator.addVertex((double) xTop, (double) yTop, 0.0D);
        tessellator.draw();
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_BLEND);
    }
}
