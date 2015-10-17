package ru.redenergy.gui.component.control;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.util.ResourceLocation;
import ru.redenergy.gui.component.GuiComponent;
import ru.redenergy.gui.layout.LayoutComponent;
import ru.redenergy.gui.render.Renderer;
import ru.redenergy.gui.render.TextRenderer;

/**
 * Simple button component <br>
 * Supported width: <b> 0 - 400 </b> (due to texture length it can't be larger) <br>
 * Supported height: <b> 5 - INFINITY </b> <br>
 * 
 * Use {@link #setClickListener(ButtonClickListener)} to define action on button pressed
 */
@LayoutComponent
public class Button extends GuiComponent {

    protected static final int DISABLED_STATE = 0;
    protected static final int IDLE_STATE = 1;
    protected static final int HOVER_STATE = 2;
    
    protected ResourceLocation buttonTexture = new ResourceLocation("textures/gui/widgets.png");
    
    @LayoutComponent
    protected String text;

    @LayoutComponent
    protected boolean isVisible = true;
    
    @LayoutComponent
    protected boolean isEnabled = true;

    protected ButtonClickListener onClick;
   
    @LayoutComponent
    protected int xPos = 0;
    
    @LayoutComponent
    protected int yPos = 0;
    
    @LayoutComponent
    protected int width = 200;
    
    @LayoutComponent
    protected int height = 20;
    
    /**Dummy constructor. Used in layout*/
    private Button(){};
    
    public Button(int xPos, int yPos, int width, int height, String title) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.width = width;
        this.height = height;
        this.text = title;
    }

    @Override
    public void onDraw(int mouseX, int mouseY, float partialTicks) {
        if (isVisible()) {
            prepareRender();
            if (!isEnabled()) {
                drawButton(DISABLED_STATE);
            } else if (isButtonUnderMouse(mouseX, mouseY)) {
                drawButton(HOVER_STATE);
            } else {
                drawButton(IDLE_STATE);
            }
            TextRenderer.renderCenteredString(getX() + getWidth() / 2, getY() + getHeight() / 2 - 4, getText());
        }
    }
    
    protected void prepareRender(){
        Minecraft.getMinecraft().getTextureManager().bindTexture(getButtonTexture());
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glEnable(GL11.GL_BLEND);
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
    }

    protected void drawButton(int state) {
        Renderer.drawContinuousTexturedBox(getX(), getY(), 0, 46 + (20 * state), getWidth(), getHeight(), 200, 20, 2, 3, 2, 2); 
    }

    @Override
    public void onMouseClicked(int posX, int posY, int mouseButtonIndex) {
        if (isButtonUnderMouse(posX, posY) && isEnabled()) {
            if (getClickListener() != null){
                getClickListener().onClick(this);
            }
            playClickSound();
        }
    }

    public boolean isButtonUnderMouse(int mouseX, int mouseY) {
        return mouseX >= getX() && mouseX <= getX() + getWidth() && mouseY >= getY() && mouseY <= getY() + getHeight();
    }

    /**
     * Provided listener will be executed by pressing the button
     * @param onClicked listener
     * @return self 
     */
    public Button setClickListener(ButtonClickListener onClicked) {
        this.onClick = onClicked;
        return this;
    }

    public ButtonClickListener getClickListener() {
        return onClick;
    }

    /**
     * @return <code> true </code> if button would be rendered
     */
    public boolean isVisible() {
        return isVisible;
    }

    /**
     * @return <code> true</code> if button can be clicked
     */
    public boolean isEnabled() {
        return isEnabled;
    }

    public Button setIsVisible(boolean isVisible) {
        this.isVisible = isVisible;
        return this;
    }

    public Button setIsEnabled(boolean isEnabled) {
        this.isEnabled = isEnabled;
        return this;
    }

    public ResourceLocation getButtonTexture() {
        return buttonTexture;
    }

    public Button setCustomTexture(ResourceLocation res) {
        this.buttonTexture = res;
        return this;
    }
    
    public Button setText(String text){
        this.text = text;
        return this;
    }
    
    public String getText(){
        return text;
    }

    public int getX(){
        return xPos;
    }
    
    public int getY(){
        return yPos;
    }
    
    public int getWidth(){
        return width;
    }
    
    public int getHeight(){
        return height;
    }
    
    protected void playClickSound() {
        Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
    }
    
    @Override
    public Button setId(String id) {
        assignId(id);
        return this;
    }

    @FunctionalInterface
    public static interface ButtonClickListener {
        void onClick(Button button);
    }
}
