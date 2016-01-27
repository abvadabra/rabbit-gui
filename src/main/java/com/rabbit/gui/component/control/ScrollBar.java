package com.rabbit.gui.component.control;

import org.lwjgl.input.Mouse;

import com.rabbit.gui.component.GuiWidget;
import com.rabbit.gui.render.Renderer;
import com.rabbit.gui.utils.Geometry;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;


public class ScrollBar extends GuiWidget {

    protected float scrolled = 0;
    
    protected int scrollerSize;
    
    protected boolean isScrolling = false;
    
    protected boolean visible = true;
    
    protected OnProgressChanged progressChangedListener = (bar, mod) -> {};
    
    protected boolean handleMouseWheel;
    
    public ScrollBar(int xPos, int yPos, int width, int height, int scrollerSize){
        super(xPos, yPos, width, height);
        this.scrollerSize = scrollerSize;
    }

    @Override
    public void onDraw(int mouseX, int mouseY, float partialTicks) {
        super.onDraw(mouseX, mouseY, partialTicks);
        if(isVisible()){
            calculateScroller(mouseY);
            Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation("textures/gui/container/creative_inventory/tab_items.png"));
            Renderer.drawContinuousTexturedBox(getX(), getY(), 174 - 1, 17 - 1, width, height, 14 + 2, 112 + 2, 2, 2, 2, 2);
            int scrollerHeight = (int)(getY() + 2 + this.scrolled * (this.height - 4 - this.scrollerSize));
            drawScroller(getX() + 2, scrollerHeight, this.width - 4, this.scrollerSize);
        }
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
            float magic = ((float)(mouseY - getY() + 2) - 10F) / ((float)(getY() + this.height - (getY() + 2)) - 15.0F);
            updateProgress(magic - this.scrolled);
        }
    }
    
    @Override
    public boolean onMouseClicked(int posX, int posY, int mouseButtonIndex, boolean overlap) {
        super.onMouseClicked(posX, posY, mouseButtonIndex, overlap);
        this.isScrolling = !overlap && Geometry.isDotInArea(getX() + 2, (int)(getY() + 2 + this.scrolled * (this.height - this.scrollerSize)), this.width - 4, this.scrollerSize, posX, posY);
        return isScrolling;
    }
    
    @Override
    public void onMouseRelease(int mouseX, int mouseY) {
        super.onMouseRelease(mouseX, mouseY);
        this.isScrolling = false;
    }

    @Override
    public void onMouseInput() {
        super.onMouseInput();
        if(shouldHandleMouseWheel()){
            handleMouseWheel();
        }
    }

    protected void handleMouseWheel(){
        if(getParent() instanceof IScrollBarSource) {
            ((IScrollBarSource) getParent()).handleMouseWheel(this);
        } else {
            double delta = Mouse.getDWheel();
            if (delta < 0) updateProgress(0.20F);
            if (delta > 0) updateProgress(-0.20F);
        }
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
    
    public ScrollBar setScrollerSize(int size){
        this.scrollerSize = size;
        return this;
    }

    public void updateProgress(float modifier){
        setProgress(this.scrolled + modifier);
        getProgressChangedListener().onProgressChanged(this, modifier);
    }
    

    public OnProgressChanged getProgressChangedListener() {
        return progressChangedListener;
    }

    public ScrollBar setProgressChangedListener(OnProgressChanged progressChangedListener) {
        this.progressChangedListener = progressChangedListener;
        return this;
    }
    
    public boolean isVisible(){
        return visible;
    }
    
    public ScrollBar setVisiblie(boolean visible){
        this.visible = visible;
        return this;
    }

    public boolean isScrolling() {
        return isScrolling;
    }

    public boolean shouldHandleMouseWheel(){
        return handleMouseWheel;
    }
    
    public ScrollBar setHandleMouseWheel(boolean status){
        this.handleMouseWheel = status;
        return this;
    }
    
    /**
     * Returns a float value between 0 and 1,
     */
    public float getProgress(){
        return this.scrolled;
    }
    
    public static interface OnProgressChanged{
        void onProgressChanged(ScrollBar bar, float modifier);
    }
    
}
