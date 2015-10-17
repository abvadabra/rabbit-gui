package ru.redenergy.gui.component.control;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.Rectangle;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.util.ResourceLocation;
import ru.redenergy.gui.component.GuiComponent;
import ru.redenergy.gui.layout.LayoutComponent;
import ru.redenergy.gui.render.Renderer;
import ru.redenergy.gui.render.TextRenderer;

@LayoutComponent
public class CheckBox extends GuiComponent{

    //width and height of checkbox are hardcoded and can't be changed
    //if you need to change it use glScalef
    
    protected static final int WIDTH = 11;
    protected static final int HEIGHT = 11;

    protected ResourceLocation buttonTexture = new ResourceLocation("textures/gui/widgets.png");
    
    @LayoutComponent
    protected boolean isChecked;
    
    @LayoutComponent
    protected String text;
    
    @LayoutComponent
    protected int xPos = 0;
    
    @LayoutComponent
    protected int yPos = 0;
    
    protected int width = WIDTH;
    protected int height = HEIGHT;

    @LayoutComponent
    protected boolean isVisible = true;
    
    @LayoutComponent
    protected boolean isEnabled = true;
    
    protected CheckBoxStatusChangedListener onStatusChangedListener;
    
    private CheckBox(){}
    
    public CheckBox(int xPos, int yPos, String title, boolean checked) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.width = WIDTH;
        this.height = HEIGHT;
        this.text = title;
        this.isChecked = checked;
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
                TextRenderer.renderCenteredString(getX() + getWidth() / 2 + 1, getY() + 1, "x", color);
            }
            TextRenderer.renderString(getX() + getWidth() + 2, getY() + getHeight() / 2 - 3, getText());
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
        Renderer.drawContinuousTexturedBox(getX(), getY(), 0, 46, getWidth(), getHeight(), 200, 20, 2, 3, 2, 2); 
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
        return mouseX >= getX() && mouseX <= getX() + getWidth() && mouseY >= getY() && mouseY <= getY() + getHeight();
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
