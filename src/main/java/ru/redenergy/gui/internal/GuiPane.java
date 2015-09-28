package ru.redenergy.gui.internal;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.gui.GuiScreen;
import ru.redenergy.gui.api.IGuiComponent;
import ru.redenergy.gui.api.IGuiPane;

public abstract class GuiPane extends GuiScreen implements IGuiPane {

    private List<IGuiComponent> components = new ArrayList();
    private boolean hasBeenInitialized = false;

    @Override
    public void setupPane() {
        getComponentsList().clear();
    }

    @Override
    public void onDraw(int mouseX, int mouseY, float partialTicks) {
        getComponentsList().forEach(component -> component.onDraw(mouseX, mouseY, partialTicks));
    }

    @Override
    public void onKeyTyped(char typedChar, int typedIndex) {
        getComponentsList().forEach(component -> component.onKeyTyped(typedChar, typedIndex));
        if (typedIndex == Keyboard.KEY_ESCAPE) {
            mc.setIngameFocus();
        }
    }

    @Override
    public void onMouseClicked(int posX, int posY, int mouseButtonIndex) {
        getComponentsList().forEach(component -> component.onMouseClicked(posX, posY, mouseButtonIndex));
    }

    @Override
    public void onUpdate() {
        getComponentsList().forEach(component -> component.onUpdate());
    }

    @Override
    public void onClose() {
        getComponentsList().forEach(component -> component.onClose());
    }

    @Override
    public void registerComponent(IGuiComponent component) {
        this.components.add(component);
        component.onRegistered(this);
    }

    @Override
    public List<IGuiComponent> getComponentsList() {
        return components;
    }

    /** VANILLA CALLS */
    
    @Override
    public final void drawScreen(int p_73863_1_, int p_73863_2_, float p_73863_3_) {
        onDraw(p_73863_1_, p_73863_2_, p_73863_3_);
    }

    @Override
    protected final void keyTyped(char p_73869_1_, int p_73869_2_) {
        onKeyTyped(p_73869_1_, p_73869_2_);
    }

    @Override
    protected final void mouseClicked(int p_73864_1_, int p_73864_2_, int p_73864_3_) {
        onMouseClicked(p_73864_1_, p_73864_2_, p_73864_3_);
    }

    @Override
    public final void initGui() {
        if (!hasBeenInitialized) {
            onInit();
        }
        setupPane();
    }

    @Override
    public final void updateScreen() {
        onUpdate();
    }

    @Override
    public final void onGuiClosed() {
        onClose();
    }

    @Override
    public final boolean doesGuiPauseGame() {
        return false;
    }

}
