package com.rabbit.gui.base;

import org.lwjgl.input.Keyboard;

import com.rabbit.gui.show.IShow;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;

public class Stage extends GuiScreen{
    
    protected IShow show;
    protected boolean hasBeenInitialized;
    
    public Stage(){}
    
    public Stage(IShow show){
        this.show = show;
        this.show.setStage(this);
    }

    public void reinitShow(){
        show.setSize(width, height);
        if(show instanceof WidgetContainer) ((WidgetContainer)show).getComponentsList().clear();
        if (!hasBeenInitialized) {
            show.onInit();
            hasBeenInitialized = true;
        }
        show.setup();
        if(show instanceof WidgetContainer) ((WidgetContainer)show).getComponentsList().forEach(component -> component.setup());
    }
    
    @Override
    public void drawScreen(int p_73863_1_, int p_73863_2_, float p_73863_3_) {
        show.onDraw(p_73863_1_, p_73863_2_, p_73863_3_);
    }

    @Override
    protected void keyTyped(char p_73869_1_, int p_73869_2_) {
        show.onKeyTyped(p_73869_1_, p_73869_2_);
        if(p_73869_2_ == Keyboard.KEY_ESCAPE){
            Minecraft.getMinecraft().setIngameFocus();
        }
    }

    @Override
    public void mouseClicked(int p_73864_1_, int p_73864_2_, int p_73864_3_) {
        show.onMouseClicked(p_73864_1_, p_73864_2_, p_73864_3_);
    }
    
    @Override
    public void handleMouseInput(){
        super.handleMouseInput();
        show.onMouseInput();
    }

    @Override
    protected void mouseMovedOrUp(int mouseX, int mouseY, int type) {
        super.mouseMovedOrUp(mouseX, mouseY, type);
        if(type == 0 || type == 1){
            show.onMouseRelease(mouseX, mouseY);
        }
    }

    @Override
    public final void initGui() {
        reinitShow();
    }

    @Override
    public void updateScreen() {
        show.onUpdate();
    }

    @Override
    public void onGuiClosed() {
        show.onClose();
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }
    
    public Stage setShow(IShow show){
        this.show = show;
        return this;
    }
    
    public IShow getShow(){
        return show;
    }
    
}
