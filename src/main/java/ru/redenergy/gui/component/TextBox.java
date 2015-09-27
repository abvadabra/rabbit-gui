package ru.redenergy.gui.component;

import javax.xml.soap.Text;

import net.minecraft.client.gui.Gui;
import net.minecraft.util.ChatAllowedCharacters;
import ru.redenergy.gui.api.IGuiComponent;
import ru.redenergy.gui.render.Renderer;
import ru.redenergy.gui.render.TextRenderer;
import scala.Char;

public class TextBox implements IGuiComponent{

	private int xPos;
	private int yPos;
	private int width;
	private int height;
	private boolean visibleBackground = true;
	private boolean isVisible = true;
	private boolean isEnabled = true;
	private boolean isFocused;
	private String text;
	private int cursorPos;
	private int scrollOffset;
	private long cursorCounter = 0L;
    private int selectionEnd;
    private int maxStringLenght = 100;
    private int enabledColor = 14737632;
    private int disabledColor = 7368816;
	
	public static final int BACKGROUND_GRAY_COLOR = -6250336;
	public static final int BACKGROUND_DARK_COLOR = -16777216;
	public static final int CURSOR_COLOR = -3092272;
	
	public TextBox(int xPos, int yPos, int width, int height){
		this(xPos, yPos, width, height, "");
	}
	
	public TextBox(int xPos, int yPos, int width, int height, String initialText){
		this.xPos = xPos;
		this.yPos = yPos;
		this.width = width;
		this.height = height;
		this.text = initialText;
		this.setCursorPosition(this.text.length());
	}
	
	

	@Override
	public void onDraw(int mouseX, int mouseY, float partialTicks) {
		if(isVisible()){
			if(isBackgroundVisible()){
				drawTextBoxBackground();
			}
			int textColor = isEnabled() ? getEnabledColor() : getDisabledColor();
            int cursorPosWithOffset = getCursorPosition() - this.scrollOffset;
            int selEnd = this.selectionEnd - this.scrollOffset;
            String text = TextRenderer.getFontRenderer().trimStringToWidth(this.text.substring(this.scrollOffset), isBackgroundVisible() ? this.width - 8 : this.width);
            boolean isCursorVisible = cursorPosWithOffset >= 0 && cursorPosWithOffset <= text.length();
            boolean shouldRenderCursor = isFocused() && this.cursorCounter / 6 % 2 == 0 && isCursorVisible;
            int firstTextX = isBackgroundVisible() ? this.xPos + 4 : this.xPos;
            int textY = isBackgroundVisible() ? this.yPos + (this.height - 8) / 2 : this.yPos;
            int secondTextX = firstTextX;

            if (selEnd > text.length()) selEnd = text.length();

            if (text.length() > 0){
                String firstText = isCursorVisible ? text.substring(0, cursorPosWithOffset) : text;
                secondTextX = TextRenderer.getFontRenderer().drawStringWithShadow(firstText, firstTextX, textY, textColor);
            }

            boolean isCursorInText = getCursorPosition() < getText().length() || getText().length() >= getMaxLength();
            int cursorX = secondTextX;

            if (!isCursorVisible){
                cursorX = cursorPosWithOffset > 0 ? firstTextX + this.width : firstTextX;
            } else if (isCursorInText){
                cursorX = --secondTextX;
            }

            if (text.length() > 0 && isCursorVisible && cursorPosWithOffset < text.length()){
                TextRenderer.getFontRenderer().drawStringWithShadow(text.substring(cursorPosWithOffset), secondTextX, textY, textColor);
            }

            if (shouldRenderCursor){
                if (isCursorInText){
                    Renderer.drawRect(cursorX, textY - 1, cursorX + 1, textY + 1 + TextRenderer.getFontRenderer().FONT_HEIGHT, CURSOR_COLOR);
                } else {
                	TextRenderer.getFontRenderer().drawStringWithShadow("_", cursorX, textY, textColor);
                }
            }

            if (selEnd != cursorPosWithOffset){
                int finishX = firstTextX + TextRenderer.getFontRenderer().getStringWidth(text.substring(0, selEnd));
                Renderer.drawRect(cursorX, textY - 1, finishX - 1, textY + 1 + TextRenderer.getFontRenderer().FONT_HEIGHT, 0x0000FF);
            }
			
		}
	}
	
	private void drawCursor(int xPos, int yPos){
		Renderer.drawRect(xPos, yPos, xPos + 2, yPos + TextRenderer.getFontRenderer().FONT_HEIGHT, -3092272);
	}
	
	private void drawTextBoxBackground(){
		Renderer.drawRect(this.xPos - 1, this.yPos - 1, this.xPos + this.width + 1, this.yPos + this.height + 1, BACKGROUND_GRAY_COLOR);
		Renderer.drawRect(this.xPos, this.yPos, this.xPos + this.width, this.yPos + this.height, BACKGROUND_DARK_COLOR);
	}

	public void pushText(String text){
		String result = "";
		String filtered = ChatAllowedCharacters.filerAllowedCharacters(text);
		int i = this.getCursorPosition() < this.selectionEnd ? this.getCursorPosition() : this.selectionEnd;
		int j = this.getCursorPosition() < this.selectionEnd ? this.selectionEnd : this.getCursorPosition();
		int k = this.getMaxLength() - getText().length() - (i - this.selectionEnd);

		if(getText().length() > 0){
			result += this.getText().substring(0, i);
		}
		int end = 0;
		if(k < filtered.length()){
			result = result + filtered.substring(0, k);
			end = k;
		} else {
			result = result + filtered;
			end = filtered.length();
		}
		if(this.getText().length() > 0 && j < this.getText().length()){
			result = result + this.getText().substring(j);
		}
		this.text = result;
		this.setCursorPosition(this.selectionEnd + (i - this.selectionEnd + end));
	}
	
	
	@Override
	public void onKeyTyped(char typedChar, int typedIndex) {
		if(isFocused()){
			pushText(String.valueOf(typedChar));
		}
	}

	@Override
	public void onMouseClicked(int posX, int posY, int mouseButtonIndex) {
		setIsFocused(isTextBoxUnderMouse(posX, posY));
		if(isFocused() && mouseButtonIndex == 0){
			int lenght = posX - this.xPos;
			String temp = TextRenderer.getFontRenderer().trimStringToWidth(this.text.substring(this.scrollOffset), this.width);
			this.setCursorPosition(TextRenderer.getFontRenderer().trimStringToWidth(temp, lenght).length() + this.scrollOffset);
		}
	}

	public boolean isTextBoxUnderMouse(int mouseX, int mouseY){
		return mouseX >= this.xPos && mouseX <= this.xPos + this.width &&
				mouseY >= this.yPos && mouseY <= this.yPos + this.height;
	}
	
	public TextBox setSelectionPos(int pos){
		if(pos < 0) pos = 0;
		
		if(pos > this.getText().length()) pos = this.getText().length();
		
		this.selectionEnd = pos;
		
		if(this.scrollOffset > this.getText().length()) this.scrollOffset = this.getText().length();
		
		String trimmed = TextRenderer.getFontRenderer().trimStringToWidth(this.getText().substring(this.scrollOffset), this.width);
		int length = trimmed.length() + this.scrollOffset;
		
		if(pos == this.scrollOffset){
			this.scrollOffset -= TextRenderer.getFontRenderer().trimStringToWidth(this.getText(), this.width, true).length();
		}
		
		if(pos > length) {
			this.scrollOffset += pos - length;
		} else if(pos <= this.scrollOffset){
			this.scrollOffset -= this.scrollOffset - pos;
		}
		
		if(this.scrollOffset < 0)this.scrollOffset = 0;
		
		if(scrollOffset > this.getText().length()) this.scrollOffset = this.getText().length();
		
		return this;
	}
	
	@Override
	public void onUpdate() {
		this.cursorCounter++;
	}

	public boolean isBackgroundVisible(){
		return visibleBackground;
	}
	
	public TextBox setBackgroundVisibility(boolean visibleBackground){
		this.visibleBackground = visibleBackground;
		return this;
	}
	
	public boolean isVisible(){
		return isVisible;
	}
	
	public TextBox setIsVisible(boolean isVisible){
		this.isVisible = isVisible;
		return this;
	}
	
	public boolean isEnabled(){
		return isEnabled;
	}
	
	public TextBox setIsEnabled(boolean isEnabled){
		this.isEnabled = isEnabled;
		return this;
	}
	
	public boolean isFocused(){
		return this.isFocused;
	}
	
	public TextBox setIsFocused(boolean isFocused){
		this.isFocused = isFocused;
		return this;
	}
	
	public int getCursorPosition(){
		return this.cursorPos;
	}
	
	public TextBox setCursorPosition(int pos){
		this.cursorPos = pos;
		if(this.getCursorPosition() < 0) this.cursorPos = 0;
		if(this.getCursorPosition() > this.getText().length()) this.cursorPos = this.getText().length();
		
		this.setSelectionPos(this.getCursorPosition());
		return this;
	}
	
	public int getMaxLength(){
		return this.maxStringLenght;
	}
	
	public TextBox setMaxLenght(int max){
		this.maxStringLenght = max;
		return this;
	}
	
	public String getText(){
		return text;
	}
	
	public TextBox setEnabledColor(int color){
		this.enabledColor = color;
		return this;
	}
	
	public TextBox setDisabledColor(int color){
		this.disabledColor = color;
		return this;
	}
	
	public int getEnabledColor(){
		return enabledColor;
	}
	
	public int getDisabledColor(){
		return disabledColor;
	}
	
	@Override
	public void onClose() {}
	@Override
	public void onCreate() {}
	
	
	
}
