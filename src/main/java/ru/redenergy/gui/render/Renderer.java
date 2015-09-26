package ru.redenergy.gui.render;

import net.minecraft.client.renderer.Tessellator;

public class Renderer {
	
	/**
	 * Draws rectangle with the previously binded texture
	 * @param posX - Position on the screen for X-axis
	 * @param posY - Position on the screen for Y-axis
	 * @param uPos - X position of image on binded texture
	 * @param vPos - Y position of image on binded texture
	 * @param width - width of rectangle
	 * @param height - height of rectangle
	 */
    public static void drawTexturedModalRect(int posX, int posY, int uPos, int vPos, int width, int height)
    {
        float f = 0.00390625F;
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV((double)(posX + 0), (double)(posY + height), (double)0, (double)((float)(uPos + 0) * f), (double)((float)(vPos + height) * f));
        tessellator.addVertexWithUV((double)(posX + width), (double)(posY + height), (double)0, (double)((float)(uPos + width) * f), (double)((float)(vPos + height) * f));
        tessellator.addVertexWithUV((double)(posX + width), (double)(posY + 0), (double)0, (double)((float)(uPos + width) * f), (double)((float)(vPos + 0) * f));
        tessellator.addVertexWithUV((double)(posX + 0), (double)(posY + 0), (double)0, (double)((float)(uPos + 0) * f), (double)((float)(vPos + 0) * f));
        tessellator.draw();
    }
}
