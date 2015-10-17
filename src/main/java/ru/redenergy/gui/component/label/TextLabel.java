package ru.redenergy.gui.component.label;

import java.util.List;
import java.util.stream.IntStream;

import ru.redenergy.gui.component.GuiComponent;
import ru.redenergy.gui.layout.LayoutComponent;
import ru.redenergy.gui.render.TextRenderer;

@LayoutComponent
public class TextLabel extends GuiComponent {

    @LayoutComponent
    protected String text;

    @LayoutComponent
    protected int xPos = 0;
    
    @LayoutComponent
    protected int yPos = 0;
    
    @LayoutComponent
    protected int width = 100;
    
    @LayoutComponent
    protected int height = 9;
    
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
        this.xPos = xPos;
        this.yPos = yPos;
        this.width = width;
        this.height = height;
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
            int y = yPos + i * TextRenderer.getFontRenderer().FONT_HEIGHT;
            if(y >= this.yPos + this.height) break;
            if(isCentered()){
                TextRenderer.renderCenteredString(this.xPos, y, displayLine);
            } else {
                TextRenderer.renderString(this.xPos, y, displayLine);
            }
        }
    }
    
    private void drawOneLined(){
        String displayText = TextRenderer.getFontRenderer().trimStringToWidth(text, width);
        if(isCentered()){
            TextRenderer.renderCenteredString(this.xPos, this.yPos, displayText);
        } else {
            TextRenderer.renderString(this.xPos, this.yPos, displayText);
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
}


