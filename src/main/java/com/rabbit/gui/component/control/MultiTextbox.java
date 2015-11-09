package com.rabbit.gui.component.control;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

import com.rabbit.gui.layout.LayoutComponent;
import com.rabbit.gui.render.Renderer;
import com.rabbit.gui.render.TextRenderer;

import net.minecraft.util.MathHelper;

public class MultiTextbox extends TextBox {

    private ScrollBar scrollBar;
    
    protected int maxStringLenght = 1000;
    
    private int listHeight;
    
    public MultiTextbox(int xPos, int yPos, int width, int height, String initialText) {
        super(xPos, yPos, width, height, initialText);
        this.listHeight = height;
    }

    public MultiTextbox(int xPos, int yPos, int width, int height) {
        super(xPos, yPos, width, height);
        this.listHeight = height;
    }

    @Override
    public void setup(){
        registerComponent(scrollBar = new ScrollBar(this.getX() + this.getWidth(), this.getY(), 15, this.getHeight(), 20).setVisiblie(false));
    }
    
    @Override
    protected void drawBox(){
        if (isVisible()) {
            if (isBackgroundVisible()) {
                drawTextBoxBackground();
            }
            int color = 0xFFFFFF;
            boolean renderCursor = this.isFocused() && this.cursorCounter / 6 % 2 == 0;
            int startLine = this.getStartLineY();
            int maxLineAmount = this.height / TextRenderer.getFontRenderer().FONT_HEIGHT + startLine;
            List<String> lines = this.getLines();
            int charCount = 0;
            int lineCount = 0;
            int maxWidth = this.width - 4;
            for(int i = 0; i < lines.size(); ++i) {
               String wholeLine = lines.get(i);
               String line = "";
               char[] chars = wholeLine.toCharArray();
               for(int j = 0; j < chars.length; ++j) {
                  char c = chars[j];
                  if(TextRenderer.getFontRenderer().getStringWidth(line + c) > maxWidth) {
                     if(lineCount >= startLine && lineCount < maxLineAmount) {
                        TextRenderer.getFontRenderer().drawString(line, this.getX() + 4, this.getY() + 4 + (lineCount - startLine) * TextRenderer.getFontRenderer().FONT_HEIGHT, color);
                     }
                     line = "";
                     lineCount++;
                  }
                  if(renderCursor && charCount == this.getCursorPosition() && lineCount >= startLine && lineCount < maxLineAmount) {
                     int cursorX = this.getX() + TextRenderer.getFontRenderer().getStringWidth(line) + 3;
                     int cursorY = this.getY() + (lineCount - startLine) * TextRenderer.getFontRenderer().FONT_HEIGHT + 4;
                     if(this.getText().length() == this.getCursorPosition()) {
                        TextRenderer.getFontRenderer().drawString("_", cursorX, cursorY, color);
                     } else {
                        Renderer.drawRect(cursorX, cursorY, cursorX + 1, cursorY + 10, 0xFFFFFFFF);
                     }
                  }
                  charCount++;
                  line += c;
               }
               if(lineCount >= startLine && lineCount < maxLineAmount) {
                  TextRenderer.getFontRenderer().drawString(line, this.getX() + 4, this.getY() + 4 + (lineCount - startLine) * TextRenderer.getFontRenderer().FONT_HEIGHT, color);
                  if(renderCursor && charCount == this.getCursorPosition()) {
                     int cursorX = this.getX() + TextRenderer.getFontRenderer().getStringWidth(line) + 3;
                     int cursorY = this.getY() + (lineCount - startLine) * TextRenderer.getFontRenderer().FONT_HEIGHT + 4;
                     if(this.getText().length() == this.getCursorPosition()) {
                        TextRenderer.getFontRenderer().drawString("_", cursorX, cursorY, color);
                     } else {
                         Renderer.drawRect(cursorX, cursorY, cursorX + 1, cursorY + TextRenderer.getFontRenderer().FONT_HEIGHT, color);
                     }
                  }
               }
               ++lineCount;
               ++charCount;
            }
            this.listHeight = lineCount * TextRenderer.getFontRenderer().FONT_HEIGHT;
            scrollBar.setVisiblie(this.listHeight > this.height - 4);
            scrollBar.setScrollerSize((int)(getScrollerSize()));
        }
    }
    
    private int getScrollerSize(){
        return (int)(1F * (float)this.height / this.listHeight * (this.height - 4));
    }
    
    @Override
    protected void handleKey(char typedChar, int typedIndex){
        if (!isFocused())
            return;
        String originalText = this.getText();
        if(typedChar == 13 || typedChar == 10) {
           this.setText(originalText.substring(0, this.getCursorPosition()) + typedChar + originalText.substring(this.getCursorPosition()));
        }

        boolean isSpecialCharCombination = handleSpecialCharComb(typedChar, typedIndex);
        if (!isSpecialCharCombination) {
            handleInput(typedChar, typedIndex);
        }
    }

    @Override
    protected void handleMouseClick(int posX, int posY, int mouseButtonIndex){
        setIsFocused(isTextBoxUnderMouse(posX, posY));
        if (isFocused() && mouseButtonIndex == 0) {
            int lenght = posX -  getX();
            String temp = TextRenderer.getFontRenderer().trimStringToWidth(this.text.substring(this.scrollOffset),  getWidth());
            this.setCursorPosition(TextRenderer.getFontRenderer().trimStringToWidth(temp, lenght).length() + this.scrollOffset);
            int x = posX - getX();
            int y = (posY - getY() - 4) / TextRenderer.getFontRenderer().FONT_HEIGHT + this.getStartLineY();
            this.cursorPos = 0;
            List<String> lines = this.getLines();
            int charCount = 0;
            int lineCount = 0;
            int maxWidth = getWidth() - 4;
            for(int i = 0; i < lines.size(); ++i){
                String wholeLine = lines.get(i);
                String line = "";
                char[] chars = wholeLine.toCharArray();
                for(int j = 0; j < chars.length; ++j){
                    char c = chars[j];
                    this.setCursorPosition(charCount);
                    if(TextRenderer.getFontRenderer().getStringWidth(line + c) > maxWidth){
                        lineCount++;
                        line = "";
                        if(y < lineCount){
                            break;
                        }
                    }
                    if(lineCount == y && x <= TextRenderer.getFontRenderer().getStringWidth(line + c)){
                        return;
                    }
                    charCount++;
                    line += c;
                }
                this.setCursorPosition(charCount);
                charCount++;
                lineCount++;
                if(y < lineCount){
                    break;
                }
                
            }
            if(y >= lineCount){
                this.setCursorPosition(this.getText().length());
            }
        }
    }
    
    
    public int getMaxLength() {
        return this.maxStringLenght;
    }
    
    private int getStartLineY(){
        float scrolled = scrollBar.scrolled;
        return MathHelper.ceiling_double_int(scrolled * this.getHeight() / TextRenderer.getFontRenderer().FONT_HEIGHT);
    }
    
    public List<String> getLines(){
        List<String> lines = new ArrayList();
        StringBuffer currentLine = new StringBuffer();
        char[] chars = this.getText().toCharArray();
        for(int i = 0; i < chars.length; i++){
            char symbol = chars[i];
            if(symbol == '\r' || symbol == '\n'){
                lines.add(currentLine.toString());
                currentLine.delete(0, currentLine.length());
            } else {
                currentLine.append(symbol);
            }
        }
        lines.add(currentLine.toString());
        return lines;
    }

    
    
}
