package ru.redenergy.gui.component.control;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.Rectangle;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.util.ResourceLocation;
import ru.redenergy.gui.component.GuiComponent;
import ru.redenergy.gui.render.Renderer;
import ru.redenergy.gui.render.TextRenderer;

public class CheckBox extends GuiComponent{

    protected static final int WIDTH = 11;
    protected static final int HEIGHT = 11;

    protected ResourceLocation buttonTexture = new ResourceLocation("textures/gui/widgets.png");
    
    protected boolean isChecked;
    protected String text;
    protected Rectangle shape;

    protected boolean isVisible = true;
    protected boolean isEnabled = true;
    
    protected CheckBoxStatusChangedListener onStatusChangedListener;
    
    public CheckBox(int xPos, int yPos, String title, boolean checked) {
        this(new Rectangle(xPos, yPos, WIDTH, HEIGHT), title, checked);
    }
    

    public CheckBox(Rectangle rect, String title, boolean checked) {
        this.shape = rect;
        this.text = title;
        this.isChecked = checked;
        if(getRect().getWidth() != WIDTH) getRect().setWidth(WIDTH);
        if(getRect().getHeight() != HEIGHT) getRect().setHeight(HEIGHT);
    }
    
    void b(ResourceLocation loc){
        Minecraft.getMinecraft().getTextureManager().getTexture(loc);
    }

    @Override
    public void onDraw(int mouseX, int mouseY, float partialTicks) {
        if (isVisible()) {
            prepareRender();
            drawButton();
            int color;
            if (!isEnabled()) {
                color = 0x7F7F7F;
            } else if (isButtonUnderMouse(mouseX, mouseY)) {
                color = 0xFFFFA0;
            } else {
                color = 0xFFFFFF;
            }
            if(isChecked()){
                TextRenderer.renderCenteredString(getRect().getX() + getRect().getWidth() / 2 + 1, getRect().getY() + 1, "x", color);
            }
            TextRenderer.renderString(getRect().getX() + getRect().getWidth() + 2, getRect().getY() + getRect().getHeight() / 2 - 3, getText());
        }
    }
    
    protected void prepareRender(){
        Minecraft.getMinecraft().getTextureManager().bindTexture(getButtonTexture());
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glEnable(GL11.GL_BLEND);
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
    }
    
    protected void drawButton() {
        Renderer.drawContinuousTexturedBox(getRect().getX(), getRect().getY(), 0, 46, getRect().getWidth(), getRect().getHeight(), 200, 20, 2, 3, 2, 2); 
    }

    @Override
    public void onMouseClicked(int posX, int posY, int mouseButtonIndex) {
        if (isButtonUnderMouse(posX, posY) && isEnabled()) {
            setIsCheckedWithNotify(!isChecked());
            playClickSound();
        }
    }

    public boolean isChecked(){
        return isChecked;
    }
    
    public CheckBox setIsChecked(boolean state){
        this.isChecked = state;
        return this;
    }

    public CheckBox setIsCheckedWithNotify(boolean state){
        setIsChecked(state);
        if(getStatusChangedListener() != null) getStatusChangedListener().onStatusChanged(this);
        return this;
    }
    
    public CheckBox setStatusChangedListener(CheckBoxStatusChangedListener listener){
        this.onStatusChangedListener = listener;
        return this;
    }
    
    public boolean isButtonUnderMouse(int mouseX, int mouseY) {
        return mouseX >= getRect().getX() && mouseX <= getRect().getX() + getRect().getWidth() && mouseY >= getRect().getY() && mouseY <= getRect().getY() + getRect().getHeight();
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

    public CheckBox setIsVisible(boolean isVisible) {
        this.isVisible = isVisible;
        return this;
    }

    public CheckBox setIsEnabled(boolean isEnabled) {
        this.isEnabled = isEnabled;
        return this;
    }

    public ResourceLocation getButtonTexture() {
        return buttonTexture;
    }

    public CheckBox setCustomTexture(ResourceLocation res) {
        this.buttonTexture = res;
        return this;
    }
    
    public CheckBox setText(String text){
        this.text = text;
        return this;
    }
    
    public String getText(){
        return text;
    }

    protected void playClickSound() {
        Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
    }
    
    public Rectangle getRect(){
        return shape;
    }
    
    public CheckBoxStatusChangedListener getStatusChangedListener(){
        return onStatusChangedListener;
    }
    
    @Override
    public CheckBox setId(String id) {
        assignId(id);
        return this;
    }
    
    @FunctionalInterface
    public interface CheckBoxStatusChangedListener{
        void onStatusChanged(CheckBox box);
    }
    
}
