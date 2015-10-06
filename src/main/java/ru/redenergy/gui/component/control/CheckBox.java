package ru.redenergy.gui.component.control;

import org.lwjgl.util.Rectangle;

import ru.redenergy.gui.render.Renderer;
import ru.redenergy.gui.render.TextRenderer;

public class CheckBox extends Button{

    protected static final int WIDTH = 11;
    protected static final int HEIGHT = 11;

    protected boolean isChecked;
    
    public CheckBox(int xPos, int yPos, String title, boolean checked) {
        super(xPos, yPos, WIDTH, HEIGHT, title);
        this.isChecked = checked;
    }
    

    public CheckBox(Rectangle rect, String title) {
        super(rect, title);
        if(getRect().getWidth() != WIDTH) getRect().setWidth(WIDTH);
        if(getRect().getHeight() != HEIGHT) getRect().setHeight(HEIGHT);
    }

    @Override
    public void onDraw(int mouseX, int mouseY, float partialTicks) {
        if (isVisible()) {
            prepareRender();
            drawButton(IDLE_STATE);
            
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
    
    protected void drawButton(int state) {
        Renderer.drawContinuousTexturedBox(getRect().getX(), getRect().getY(), 0, 46, getRect().getWidth(), getRect().getHeight(), 200, 20, 2, 3, 2, 2); 
    }

    @Override
    public void onMouseClicked(int posX, int posY, int mouseButtonIndex) {
        if (isButtonUnderMouse(posX, posY) && isEnabled()) {
            if (getClickListener() != null){
                getClickListener().onClick(this);
            }
            setIsChecked(!isChecked());
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

    @Override
    public CheckBox setId(String id) {
        assignId(id);
        return this;
    }
    
}
