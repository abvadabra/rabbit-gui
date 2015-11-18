package com.rabbit.gui.render;

import java.util.stream.IntStream;

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
        tessellator.addVertexWithUV(posX + 0, posY + height, 0,
                (uPos + 0) * f, (vPos + height) * f);
        tessellator.addVertexWithUV(posX + width, posY + height, 0,
                (uPos + width) * f, (vPos + height) * f);
        tessellator.addVertexWithUV(posX + width, posY + 0, 0,
                (uPos + width) * f, (vPos + 0) * f);
        tessellator.addVertexWithUV(posX + 0, posY + 0, 0,
                (uPos + 0) * f, (vPos + 0) * f);
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
        float f3 = (color >> 24 & 255) / 255.0F;
        float f = (color >> 16 & 255) / 255.0F;
        float f1 = (color >> 8 & 255) / 255.0F;
        float f2 = (color & 255) / 255.0F;
        Tessellator tessellator = Tessellator.instance;
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        GL11.glColor4f(f, f1, f2, f3);
        tessellator.startDrawingQuads();
        tessellator.addVertex(xTop, yBot, 0.0D);
        tessellator.addVertex(xBot, yBot, 0.0D);
        tessellator.addVertex(xBot, yTop, 0.0D);
        tessellator.addVertex(xTop, yTop, 0.0D);
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
        float f3 = (color >> 24 & 255) / 255.0F;
        float f = (color >> 16 & 255) / 255.0F;
        float f1 = (color >> 8 & 255) / 255.0F;
        float f2 = (color & 255) / 255.0F;
        Tessellator tessellator = Tessellator.instance;
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        specialGL.run();
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        GL11.glColor4f(f, f1, f2, f3);
        tessellator.startDrawingQuads();
        tessellator.addVertex(xTop, yBot, 0.0D);
        tessellator.addVertex(xBot, yBot, 0.0D);
        tessellator.addVertex(xBot, yTop, 0.0D);
        tessellator.addVertex(xTop, yTop, 0.0D);
        tessellator.draw();
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_BLEND);
    }
    
    /**
     * Draws a textured box of any size (smallest size is borderSize * 2 square) based on a fixed size textured box with continuous borders
     * and filler. It is assumed that the desired texture ResourceLocation object has been bound using
     * Minecraft.getMinecraft().getTextureManager().bindTexture(resourceLocation).
     * 
     * @param x x axis offset
     * @param y y axis offset
     * @param u bound resource location image x offset
     * @param v bound resource location image y offset
     * @param width the desired box width
     * @param height the desired box height
     * @param textureWidth the width of the box texture in the resource location image
     * @param textureHeight the height of the box texture in the resource location image
     * @param topBorder the size of the box's top border
     * @param bottomBorder the size of the box's bottom border
     * @param leftBorder the size of the box's left border
     * @param rightBorder the size of the box's right border
     */
    public static void drawContinuousTexturedBox(int x, int y, int u, int v, int width, int height, int textureWidth, int textureHeight,
            int topBorder, int bottomBorder, int leftBorder, int rightBorder){
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glEnable(GL11.GL_BLEND);
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        int fillerWidth = textureWidth - leftBorder - rightBorder;
        int fillerHeight = textureHeight - topBorder - bottomBorder;
        int canvasWidth = width - leftBorder - rightBorder;
        int canvasHeight = height - topBorder - bottomBorder;
        int xPasses = canvasWidth / fillerWidth;
        int remainderWidth = canvasWidth % fillerWidth;
        int yPasses = canvasHeight / fillerHeight;
        int remainderHeight = canvasHeight % fillerHeight;
        
        drawTexturedModalRect(x, y, u, v, leftBorder, topBorder);
        drawTexturedModalRect(x + leftBorder + canvasWidth, y, u + leftBorder + fillerWidth, v, rightBorder, topBorder);
        drawTexturedModalRect(x, y + topBorder + canvasHeight, u, v + topBorder + fillerHeight, leftBorder, bottomBorder);
        drawTexturedModalRect(x + leftBorder + canvasWidth, y + topBorder + canvasHeight, u + leftBorder + fillerWidth, v + topBorder + fillerHeight, rightBorder, bottomBorder);
        IntStream.range(0, xPasses + (remainderWidth > 0 ? 1 : 0)).forEach(i -> {
            drawTexturedModalRect(x + leftBorder + (i * fillerWidth), y, u + leftBorder, v, (i == xPasses ? remainderWidth : fillerWidth), topBorder);
            drawTexturedModalRect(x + leftBorder + (i * fillerWidth), y + topBorder + canvasHeight, u + leftBorder, v + topBorder + fillerHeight, (i == xPasses ? remainderWidth : fillerWidth), bottomBorder);
            IntStream.range(0, yPasses + (remainderHeight > 0 ? 1 : 0)).forEach(j -> {
                drawTexturedModalRect(x + leftBorder + (i * fillerWidth), y + topBorder + (j * fillerHeight), u + leftBorder, v + topBorder, (i == xPasses ? remainderWidth : fillerWidth), (j == yPasses ? remainderHeight : fillerHeight));
            });
        });
        IntStream.range(0, yPasses + (remainderHeight > 0 ? 1 : 0)).forEach(j -> {
            drawTexturedModalRect(x, y + topBorder + (j * fillerHeight), u, v + topBorder, leftBorder, (j == yPasses ? remainderHeight : fillerHeight));
            drawTexturedModalRect(x + leftBorder + canvasWidth, y + topBorder + (j * fillerHeight), u + leftBorder + fillerWidth, v + topBorder, rightBorder, (j == yPasses ? remainderHeight : fillerHeight));
        });
    }
    
    /**
     * Draws image and scales picture (without cutting it)
     * @param xPos
     * @param yPos
     * @param u
     * @param v
     * @param imageWidth
     * @param imageHeight
     * @param width
     * @param height
     * @param zLevel
     */
    public static void drawTexturedModalRect(int xPos, int yPos, int u, int v, int imageWidth, int imageHeight, int width, int height, float zLevel) {
        float f = 1F / width;
        float f1 = 1F / height;
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV(xPos,yPos + imageHeight, zLevel, u * f, (v + imageHeight) * f1);
        tessellator.addVertexWithUV(xPos + imageWidth, yPos + imageHeight, zLevel, (u + imageWidth) * f, (v + imageHeight) * f1);
        tessellator.addVertexWithUV(xPos + imageWidth, yPos, zLevel, (u + imageWidth) * f, v * f1);
        tessellator.addVertexWithUV(xPos, yPos, zLevel, u * f, v * f1);
        tessellator.draw();
    }
}
