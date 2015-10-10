package ru.redenergy.gui.component.base;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import ru.redenergy.gui.api.IGuiComponent;
import ru.redenergy.gui.api.IShow;

public class Stage extends GuiScreen {
    
    protected IShow show;
    protected boolean hasBeenInitialized;
    
    public Stage(){}
    
    public Stage(IShow show){
        this.show = show;
    }
    
    @Override
    public final void drawScreen(int p_73863_1_, int p_73863_2_, float p_73863_3_) {
        show.onDraw(p_73863_1_, p_73863_2_, p_73863_3_);
    }

    @Override
    protected final void keyTyped(char p_73869_1_, int p_73869_2_) {
        show.onKeyTyped(p_73869_1_, p_73869_2_);
        if(p_73869_2_ == Keyboard.KEY_ESCAPE){
            Minecraft.getMinecraft().setIngameFocus();
        }
    }

    @Override
    protected final void mouseClicked(int p_73864_1_, int p_73864_2_, int p_73864_3_) {
        show.onMouseClicked(p_73864_1_, p_73864_2_, p_73864_3_);
    }

    @Override
    public final void initGui() {
        show.setSize(width, height);
        show.getComponentsList().clear();
        if (!hasBeenInitialized) {
            show.onInit();
            hasBeenInitialized = true;
        }
        show.setup();
        show.getComponentsList().forEach(component -> component.setup());
    }

    @Override
    public final void updateScreen() {
        show.onUpdate();
    }

    @Override
    public final void onGuiClosed() {
        show.onClose();
    }

    @Override
    public final boolean doesGuiPauseGame() {
        return false;
    }
    
    public Stage setShow(IShow show){
        this.show = show;
        return this;
    }
    
}
