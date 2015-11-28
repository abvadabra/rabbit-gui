package com.rabbit.gui.base;

import org.lwjgl.input.Keyboard;

import com.rabbit.gui.show.IShow;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;

import java.util.*;

public class Stage extends GuiScreen{
    
    protected IShow show;
    protected boolean hasBeenInitialized = false;
    private Stack<IShow> showHistory = new Stack<>();
    
    public Stage(){}
    
    public Stage(IShow show){
        display(show);
    }

    /**
     * Shortcut for #reinitShow(false), provided for backward compatibility
     */
    public void reinitShow(){
        reinitShow(false);
    }

    /**
     * Reinitialized currently opened shows, updates it's resolution and re-setups it. <br>
     * If <code>forceInit</code> is <code>true</code> show#onInit() will be called even if it's been already initialized
     * @param forceInit
     */
    public void reinitShow(boolean forceInit){
        show.setSize(width, height);
        if(show instanceof WidgetContainer) ((WidgetContainer)show).getComponentsList().clear();
        if (!show.hasBeenInitialized() || forceInit) {
            show.onInit();
        }
        show.setup();
        if(show instanceof WidgetContainer) ((WidgetContainer)show).getComponentsList().forEach(component -> component.setup());
    }

    /**
     * Puts the given show on stage
     * @param show
     */
    public void display(IShow show){
        setShow(show);
        show.setStage(this);
        pushHistory(show);
        reinitShow();
    }

    public Stage setShow(IShow show){
        this.show = show;
        return this;
    }

    /**
     * Updated stage's history and adds the given show <br>
     * If given show already in the history it will be moved to the start
     * @param show
     */
    private void pushHistory(IShow show){
        if(this.showHistory.contains(show)){
            this.showHistory.remove(show);
        }
        this.showHistory.push(show);
    }

    public Stack<IShow> getShowHistory(){
        return this.showHistory;
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
    
    public IShow getShow(){
        return show;
    }
    
}
