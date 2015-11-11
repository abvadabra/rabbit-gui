package com.rabbit.gui.component.label;

import java.util.List;
import java.util.stream.IntStream;

import com.rabbit.gui.component.GuiWidget;
import com.rabbit.gui.component.Shiftable;
import com.rabbit.gui.layout.LayoutComponent;
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

    @Override
    public void shiftX(int x) {
        this.setX(getX() + x);
    }

    @Override
    public void shiftY(int y) {
        this.setY(getY() + y);
    }
}


