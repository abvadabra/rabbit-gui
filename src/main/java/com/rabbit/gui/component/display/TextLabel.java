package com.rabbit.gui.component.display;

import java.util.List;

import com.rabbit.gui.component.GuiWidget;
import com.rabbit.gui.component.Shiftable;
import com.rabbit.gui.layout.LayoutComponent;
import com.rabbit.gui.render.Renderer;
import com.rabbit.gui.render.TextRenderer;

@LayoutComponent
public class TextLabel extends GuiWidget implements Shiftable {

    @LayoutComponent
    protected String text;

    @LayoutComponent
    protected boolean isVisible = true;
    
    @LayoutComponent
    protected boolean centered = false;
    
    @LayoutComponent
    protected boolean multiline = false;
    
    @LayoutComponent
    protected boolean drawBackground = false;
    
    private TextLabel(){}
    
    public TextLabel(int xPos, int yPos, int width, String text){
        this(xPos, yPos, width, 9, text);
    }
    
    public TextLabel(int xPos, int yPos, int width, int height, String text){
        super(xPos, yPos, width, height);
        this.text = text;
    }
    
    
    @Override
    public void onDraw(int mouseX, int mouseY, float partialTicks) {
        if(isVisible()){
            if(shouldDrawBackground()){
                drawBackground();
            }
            if(isMultilined()){
                drawMultilined();
            } else {
                drawOneLined();
            }
        }
    }
    
    private void drawMultilined(){
        List<String> displayLines = TextRenderer.getFontRenderer().listFormattedStringToWidth(text, width);
        for(int i = 0; i < displayLines.size(); i++) {
            String displayLine = displayLines.get(i);
            int y = getY() + i * TextRenderer.getFontRenderer().FONT_HEIGHT;
            if(y >= getY() + this.height) break;
            if(isCentered()){
                TextRenderer.renderCenteredString(getX(), y, displayLine);
            } else {
                TextRenderer.renderString(getX(), y, displayLine);
            }
        }
    }
    
    private void drawOneLined(){
        String displayText = TextRenderer.getFontRenderer().trimStringToWidth(text, width);
        if(isCentered()){
            TextRenderer.renderCenteredString(getX(), getY(), displayText);
        } else {
            TextRenderer.renderString(getX(), getY(), displayText);
        }
    }
    
    private void drawBackground(){
        Renderer.drawRect(getX() - 2, getY() - 2, getX() + this.width + 2, getY() + this.height + 3, -6250336);
        Renderer.drawRect(getX() - 1, getY() - 1, getX() + this.width + 1, getY() + this.height + 2, -0xFFFFFF-1);
    }

    @Override
    public TextLabel setId(String id) {
        assignId(id);
        return this;
    }

    public TextLabel setIsVisible(boolean visible){
        this.isVisible = visible;
        return this;
    }
    
    public TextLabel setIsCentered(boolean centered){
        this.centered = centered;
        return this;
    }
    
    public TextLabel setDrawBackground(boolean drawBackground){
        this.drawBackground = drawBackground;
        return this;
    }

    public boolean isCentered(){
        return centered;
    }
    
    public boolean isVisible(){
        return isVisible;
    }
    
    public TextLabel setMultilined(boolean multilined){
        this.multiline = multilined;
        return this;
    }
    
    public boolean isMultilined(){
        return multiline;
    }

    public boolean shouldDrawBackground(){
        return drawBackground;
    }
    
    @Override
    public void shiftX(int x) {
        this.setX(getX() + x);
    }

    @Override
    public void shiftY(int y) {
        this.setY(getY() + y);
    }
}


