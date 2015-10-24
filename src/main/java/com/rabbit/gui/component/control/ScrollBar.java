package com.rabbit.gui.component.control;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import com.rabbit.gui.component.GuiWidget;
import com.rabbit.gui.render.Renderer;
import com.rabbit.gui.utils.GeometryUtils;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;


public class ScrollBar extends GuiWidget {

    protected int xPos;
    
    protected int yPos;
    
    protected int width;
    
    protected int height;

    protected float scrolled = 0;
    
    protected int scrollerSize;
    
    protected boolean isScrolling = false;
    
    protected OnProgressChanged progressChangedListener = (bar) -> {};
    
    public ScrollBar(int xPos, int yPos, int width, int height, int scrollerSize){
        this.xPos = xPos;
        this.yPos = yPos;
        this.width = width;
        this.height = height;
        this.scrollerSize = scrollerSize;
    }

    @Override
    public void onDraw(int mouseX, int mouseY, float partialTicks) {
        super.onDraw(mouseX, mouseY, partialTicks);
        calculateScroller(mouseY);
        Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation("textures/gui/container/creative_inventory/tab_items.png"));
        Renderer.drawContinuousTexturedBox(xPos, yPos, 174 - 1, 17 - 1, width, height, 14 + 2, 112 + 2, 2, 2, 2, 2);
        int scrollerHeight = (int)(this.yPos + 2 + this.scrolled * (this.height - 4 - this.scrollerSize));
        drawScroller(this.xPos + 2, scrollerHeight, this.width - 4, this.scrollerSize);
    }
    
    private void drawScroller(int xPos, int yPos, int width, int height){
        Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation("textures/gui/container/creative_inventory/tabs.png"));
        Renderer.drawContinuousTexturedBox(xPos, yPos, isScrolling() ? 244 : 232, 0, width, height, 12, 15, 1, 2, 2, 2);
    }
    
    /**
     * Calculates scroller progress based on mouse y pos
     * 
     * @param mouseY
     */
    private void calculateScroller(int mouseY){
        if(isScrolling) {
            float magic = ((float)(mouseY - this.yPos + 2) - 10F) / ((float)(this.yPos + this.height - (this.yPos + 2)) - 15.0F);
            System.out.println(magic);
            setProgressWithNotify(magic);
        }
    }
    
    @Override
    public void onMouseClicked(int posX, int posY, int mouseButtonIndex) {
        super.onMouseClicked(posX, posY, mouseButtonIndex);
        this.isScrolling = GeometryUtils.isDotInArea(this.xPos + 2, (int)(this.yPos + 2 + this.scrolled * (this.height - this.scrollerSize)), this.width - 4, this.scrollerSize, posX, posY);
    }
    
    @Override
    public void onMouseRelease(int mouseX, int mouseY) {
        super.onMouseRelease(mouseX, mouseY);
        this.isScrolling = false;
    }

    @Override
    public void onMouseInput() {
        super.onMouseInput();
        double delta = Mouse.getDWheel();
        if(delta < 0) setProgressWithNotify(this.scrolled + 0.10F);
        if(delta > 0) setProgressWithNotify(this.scrolled - 0.10F);
    }
    
    private void revalidateScroller(){
        if(this.scrolled < 0) this.scrolled = 0;
        if(this.scrolled > 1) this.scrolled = 1; 
    }
    
    
    public ScrollBar setProgress(float scroll){
        this.scrolled = scroll;
        revalidateScroller();
        return this;
    }
    
    public void setProgressWithNotify(float scroll){
        setProgress(scroll);
        getProgressChangedListener().onProgressChanged(this);
    }
    
    public OnProgressChanged getProgressChangedListener() {
        return progressChangedListener;
    }

    public ScrollBar setProgressChangedListener(OnProgressChanged progressChangedListener) {
        this.progressChangedListener = progressChangedListener;
        return this;
    }

    public int getX() {
        return xPos;
    }

    public int getY() {
        return yPos;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public boolean isScrolling() {
        return isScrolling;
    }

    /**
     * Returns a float value between 0 and 1,
     */
    public float getProgress(){
        return this.scrolled;
    }
    
    public static interface OnProgressChanged{
        void onProgressChanged(ScrollBar bar);
    }
    
}
