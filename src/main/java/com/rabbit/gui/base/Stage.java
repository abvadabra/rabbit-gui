package com.rabbit.gui.base;

import com.rabbit.gui.component.IGui;
import com.rabbit.gui.show.IShow;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import org.lwjgl.input.Keyboard;

import java.util.Stack;

public class Stage extends GuiScreen{

    /**
     * Currently displayed show
     */
    protected IShow show;

    /**
     * Contains all opened shows (including current)
     */
    private Stack<IShow> showHistory = new Stack<>();

    /**
     * Will create an empty show <br>
     * Note: If you try to render empty stage the crash may occur
     */
    public Stage(){}

    /**
     * Creates stage and places the given show on it
     * @param show - displayed show
     */
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
     * @param forceInit - if <code>true</code> show#onInit() will be called event if it's been already initialized
     */
    public void reinitShow(boolean forceInit){
        show.setSize(width, height);
        if(show instanceof WidgetContainer) ((WidgetContainer)show).getComponentsList().clear();
        if (!show.hasBeenInitialized() || forceInit) {
            show.onInit();
        }
        show.setup();
        if(show instanceof WidgetContainer) ((WidgetContainer)show).getComponentsList().forEach(IGui::setup);
    }

    /**
     * Puts the given show on stage
     * @param show - show to display
     */
    public void display(IShow show){
        setShow(show);
        show.setStage(this);
        reinitShow(true);
        pushHistory(this.show);
    }

    /**
     * Setter for show field, if you want to display show use {@link #display(IShow)} instead
     * @param show - new show
     * @return current instance of Stage
     */
    public Stage setShow(IShow show){
        this.show = show;
        return this;
    }

    /**
     * Returns currently displayed show
     * @return currently display show
     */
    public IShow getShow(){
        return show;
    }

    /**
     * Updated stage's history and adds the given show <br>
     * If given show already in the history it will be moved to the start
     * @param show - show which must be placed in history
     */
    private void pushHistory(IShow show){
        if(this.showHistory.contains(show)){
            this.showHistory.remove(show);
        }
        this.showHistory.push(show);
    }

    /**
     * Displays previously opened show <br>
     * If current show is the only opened show this stage will be closed <br>
     * If history is empty nothing will happen
     */
    public void displayPrevious(){
        if(getShowHistory().size() != 0){
            if(getShowHistory().size() == 1){
                close();
            } else {
                getShowHistory().pop(); //remove current
                display(getShowHistory().pop()); //remove and open previous
            }
        }
    }

    /**
     * @return This stage history
     */
    public Stack<IShow> getShowHistory(){
        return this.showHistory;
    }

    public void close(){
        Minecraft.getMinecraft().setIngameFocus();
    }

    /**
     * Wrapper for vanilla method
     */
    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        show.onDraw(mouseX, mouseY, partialTicks);
    }

    /**
     * Wrapper for vanilla method
     */
    @Override
    protected void keyTyped(char typedChar, int typedKeyIndex) {
        show.onKeyTyped(typedChar, typedKeyIndex);
        if(typedKeyIndex == Keyboard.KEY_ESCAPE){
            Minecraft.getMinecraft().setIngameFocus();
        }
    }

    /**
     * Wrapper for vanilla method
     */
    @Override
    public void mouseClicked(int clickX, int clickY, int mouseIndex) {
        show.onMouseClicked(clickX, clickY, mouseIndex, false);
    }

    /**
     * Wrapper for vanilla method
     */
    @Override
    public void handleMouseInput(){
        super.handleMouseInput();
        show.onMouseInput();
    }

    /**
     * Wrapper for vanilla method
     */
    @Override
    protected void mouseMovedOrUp(int mouseX, int mouseY, int type) {
        super.mouseMovedOrUp(mouseX, mouseY, type);
        if(type == 0 || type == 1){
            show.onMouseRelease(mouseX, mouseY);
        }
    }

    /**
     * Wrapper for vanilla method
     */
    @Override
    public final void initGui() {
        if(this.show != null) {
            reinitShow();
        }
    }

    /**
     * Wrapper for vanilla method
     */
    @Override
    public void updateScreen() {
        show.onUpdate();
    }

    /**
     * Wrapper for vanilla method
     */
    @Override
    public void onGuiClosed() {
        show.onClose();
    }

    /**
     * Wrapper for vanilla method
     */
    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }
}
