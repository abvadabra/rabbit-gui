package ru.redenergy.gui.component.control;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.Rectangle;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ChatAllowedCharacters;
import ru.redenergy.gui.component.GuiComponent;
import ru.redenergy.gui.component.Shiftable;
import ru.redenergy.gui.layout.LayoutComponent;
import ru.redenergy.gui.render.Renderer;
import ru.redenergy.gui.render.TextRenderer;
import ru.redenergy.gui.utils.ControlCharacters;

@LayoutComponent
public class TextBox extends GuiComponent implements Shiftable {


    public static final int BACKGROUND_GRAY_COLOR = -6250336;
    public static final int BACKGROUND_DARK_COLOR = -0xFFFFFF-1;
    public static final int CURSOR_COLOR = -3092272;
    
    @LayoutComponent
    protected int xPos = 0;
    
    @LayoutComponent
    protected int yPos = 0;
    
    @LayoutComponent
    protected int width = 100;
    
    @LayoutComponent
    protected int height = 20;
    
    @LayoutComponent
    protected boolean visibleBackground = true;
    
    @LayoutComponent
    protected boolean isVisible = true;
    
    @LayoutComponent
    protected boolean isEnabled = true;
    
    @LayoutComponent
    protected boolean isFocused = false;
    
    @LayoutComponent
    protected String text;
    protected int cursorPos;
    protected int scrollOffset;

    @LayoutComponent
    protected int maxStringLenght = 100;
    
    protected int selectionEnd = -1;
    
    @LayoutComponent
    protected int enabledColor = 14737632;
    
    @LayoutComponent
    protected int disabledColor = 7368816;
    
    protected long cursorCounter = 0L;
    
    protected TextChangedListener textChangedListener;

    private TextBox(){}
    
    public TextBox(int xPos, int yPos, int width, int height) {
        this(xPos, yPos, width, height, "");
    }

    public TextBox(int xPos, int yPos, int width, int height, String initialText) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.width = width;
        this.height = height;
        this.text = initialText;
        this.setCursorPosition(this.text.length());
        Keyboard.enableRepeatEvents(true);
    }

    @Override
    public void onDraw(int mouseX, int mouseY, float partialTicks) {
        if (isVisible()) {
            if (isBackgroundVisible()) {
                drawTextBoxBackground();
            }
            int textColor = isEnabled() ? getEnabledColor() : getDisabledColor();
            int cursorPosWithOffset = getCursorPosition() - this.scrollOffset;
            int selEnd = this.selectionEnd - this.scrollOffset;
            String text = TextRenderer.getFontRenderer().trimStringToWidth(this.text.substring(this.scrollOffset),
                    isBackgroundVisible() ?   getWidth() - 8 :  getWidth());
            boolean isCursorVisible = cursorPosWithOffset >= 0 && cursorPosWithOffset <= text.length();
            boolean shouldRenderCursor = isFocused() && this.cursorCounter / 6 % 2 == 0 && isCursorVisible;
            int firstTextX = isBackgroundVisible() ?  getX() + 4 :  getX();
            int textY = isBackgroundVisible() ?  getY() + ( getHeight() - 8) / 2 :  getY();
            int secondTextX = firstTextX;

            if (selEnd > text.length())
                selEnd = text.length();

            if (text.length() > 0) {
                String firstText = isCursorVisible ? text.substring(0, cursorPosWithOffset) : text;
                secondTextX = TextRenderer.getFontRenderer().drawStringWithShadow(firstText, firstTextX, textY,
                        textColor);
            }

            boolean isCursorInText = getCursorPosition() < getText().length() || getText().length() >= getMaxLength();
            int cursorX = secondTextX;

            if (!isCursorVisible) {
                cursorX = cursorPosWithOffset > 0 ? firstTextX +  getWidth() : firstTextX;
            } else if (isCursorInText) {
                cursorX = --secondTextX;
            }

            if (text.length() > 0 && isCursorVisible && cursorPosWithOffset < text.length()) {
                TextRenderer.getFontRenderer().drawStringWithShadow(text.substring(cursorPosWithOffset), secondTextX,
                        textY, textColor);
            }

            if (shouldRenderCursor) {
                if (isCursorInText) {
                    Renderer.drawRect(cursorX, textY - 1, cursorX + 1,
                            textY + 1 + TextRenderer.getFontRenderer().FONT_HEIGHT, CURSOR_COLOR);
                } else {
                    TextRenderer.getFontRenderer().drawStringWithShadow("_", cursorX, textY, textColor);
                }
            }

            if (selEnd != cursorPosWithOffset) {
                int finishX = firstTextX + TextRenderer.getFontRenderer().getStringWidth(text.substring(0, selEnd));
                renderSelectionRect(cursorX, textY - 1, finishX - 1, textY + 1 + TextRenderer.getFontRenderer().FONT_HEIGHT);
            }

        }
    }
    
    private void renderSelectionRect(int xTop, int yTop, int xBot, int yBot){
        Renderer.drawRectWithSpecialGL(xTop, yTop, xBot, yBot, -0x5555FF, () -> {
                    GL11.glColor4f(0.0F, 0.0F, 255.0F, 255.0F);
                    GL11.glDisable(GL11.GL_TEXTURE_2D);
                    GL11.glEnable(GL11.GL_COLOR_LOGIC_OP);
                    GL11.glLogicOp(GL11.GL_OR_REVERSE);
                });
        GL11.glDisable(GL11.GL_COLOR_LOGIC_OP);
    }

    private void drawCursor(int xPos, int yPos) {
        Renderer.drawRect(xPos, yPos, xPos + 2, yPos + TextRenderer.getFontRenderer().FONT_HEIGHT, CURSOR_COLOR);
    }

    private void drawTextBoxBackground() {
        Renderer.drawRect( getX() - 1,  getY() - 1,  getX() +  getWidth() + 1,  getY() +  getHeight() + 1, BACKGROUND_GRAY_COLOR);
        Renderer.drawRect( getX(),  getY(),  getX() +  getWidth(),  getY() +  getHeight(), BACKGROUND_DARK_COLOR);
    }

    public void pushText(String text) {
        String result = "";
        String filtered = ChatAllowedCharacters.filerAllowedCharacters(text);
        int i = this.getCursorPosition() < this.selectionEnd ? this.getCursorPosition() : this.selectionEnd;
        int j = this.getCursorPosition() < this.selectionEnd ? this.selectionEnd : this.getCursorPosition();
        int k = this.getMaxLength() - getText().length() - (i - this.selectionEnd);

        if (getText().length() > 0) {
            result += this.getText().substring(0, i);
        }
        int end = 0;
        if (k < filtered.length()) {
            result = result + filtered.substring(0, k);
            end = k;
        } else {
            result = result + filtered;
            end = filtered.length();
        }
        if (this.getText().length() > 0 && j < this.getText().length()) {
            result = result + this.getText().substring(j);
        }
        this.setTextWithEvent(result);
        this.setCursorPosition(this.selectionEnd + (i - this.selectionEnd + end));
    }

    @Override
    public void onKeyTyped(char typedChar, int typedIndex) {
        if (!isFocused())
            return;
        boolean isSpecialCharCombination = handleSpecialCharComb(typedChar, typedIndex);
        if (!isSpecialCharCombination) {
            handleInput(typedChar, typedIndex);
        }
    }

    private boolean handleInput(char typedChar, int typedKeyIndex) {
        switch (typedKeyIndex) {
        case Keyboard.KEY_BACK:
            if (isEnabled()) {
                if (GuiScreen.isCtrlKeyDown()) {
                    deleteWordsFromCursor(-1);
                } else {
                    deleteTextFromCursor(-1);
                }
            }
            return true;
        case Keyboard.KEY_HOME:
            if (GuiScreen.isShiftKeyDown()) {
                this.setSelectionPos(0);
            } else {
                this.setCursorPosition(0);
            }
            return true;
        case Keyboard.KEY_LEFT:
            handleKeyboardArrow(-1);
            return true;
        case Keyboard.KEY_RIGHT:
            handleKeyboardArrow(1);
            return true;
        case Keyboard.KEY_END:
            if (GuiScreen.isShiftKeyDown()) {
                this.setSelectionPos(getText().length());
            } else {
                this.setCursorPosition(getText().length());
            }
            return true;
        case Keyboard.KEY_DELETE:
            if (isEnabled()) {
                if (GuiScreen.isCtrlKeyDown()) {
                    deleteWordsFromCursor(1);
                } else {
                    deleteTextFromCursor(1);
                }
            }
            return true;
        default:
            if (isEnabled() && ChatAllowedCharacters.isAllowedCharacter(typedChar)) {
                this.pushText(Character.toString(typedChar));
                return true;
            }
        }

        return false;
    }

    public void deleteTextFromCursor(int amount) {
        if (getText().length() != 0) {
            if (this.selectionEnd != getCursorPosition()) {
                this.pushText("");
            } else {
                boolean negative = amount < 0;
                int j = negative ? getCursorPosition() + amount : getCursorPosition();
                int k = negative ? getCursorPosition() : getCursorPosition() + amount;
                String result = "";
                if (j >= 0) {
                    result = getText().substring(0, j);
                }

                if (k < getText().length()) {
                    result += getText().substring(k);
                }

                this.setTextWithEvent(result);

                if (negative) {
                    this.setCursorPosition(this.selectionEnd + amount);
                }
            }
        }
    }

    public void deleteWordsFromCursor(int amount) {
        if (getText().length() != 0) {
            if (this.selectionEnd != getCursorPosition()) {
                this.pushText("");
            } else {
                this.deleteTextFromCursor(
                        this.getAmountOfWordsFromPos(amount, getCursorPosition(), true) - getCursorPosition());
            }
        }
    }

    private void handleKeyboardArrow(int n) {
        if (GuiScreen.isShiftKeyDown()) {
            if (GuiScreen.isCtrlKeyDown()) {
                this.setSelectionPos(getAmountOfWordsFromPos(n, this.selectionEnd, true));
            } else {
                this.setSelectionPos(this.selectionEnd + n);
            }
        } else if (GuiScreen.isCtrlKeyDown()) {
            this.setCursorPosition(this.getAmountOfWordsFromPos(n, getCursorPosition(), true));
        } else {
            this.setCursorPosition(this.selectionEnd + (n));
        }
    }

    private boolean handleSpecialCharComb(char typedChar, int typedIndex) {
        switch (typedChar) {
        case 1:
            this.setCursorPosition(getText().length());
            this.setSelectionPos(0);
            return true;
        case ControlCharacters.CtrlC:
            GuiScreen.setClipboardString(this.getSelectedText());
            return true;
        case ControlCharacters.CtrlV:
            if (isEnabled()) {
                this.pushText(GuiScreen.getClipboardString());
            }
            return true;
        case ControlCharacters.CtrlX:
            GuiScreen.setClipboardString(getSelectedText());
            if (isEnabled()) {
                this.pushText("");
            }
            return true;
        }
        return false;
    }

    public int getAmountOfWordsFromPos(int n, int pos, boolean flag) {
        return getAmountOfWordsFromPos(n < 0, Math.abs(n), pos, flag);
    }

    public int getAmountOfWordsFromPos(boolean negative, int absolute, int pos, boolean flag) {
        int result = pos;
        for (int i = 0; i < absolute; ++i) {
            if (negative) {
                while (flag && result > 0 && getText().charAt(result - 1) == 32) {
                    --result;
                }
                while (result > 0 && getText().charAt(result - 1) != 32) {
                    --result;
                }
            } else {
                result = getText().indexOf(32, result);
                if (result == -1) {
                    result = getText().length();
                } else {
                    while (flag && result < getText().length() && getText().charAt(result) == 32) {
                        ++result;
                    }
                }
            }
        }
        return result;
    }

    @Override
    public void onMouseClicked(int posX, int posY, int mouseButtonIndex) {
        setIsFocused(isTextBoxUnderMouse(posX, posY));
        if (isFocused() && mouseButtonIndex == 0) {
            int lenght = posX -  getX();
            String temp = TextRenderer.getFontRenderer().trimStringToWidth(this.text.substring(this.scrollOffset),  getWidth());
            this.setCursorPosition(TextRenderer.getFontRenderer().trimStringToWidth(temp, lenght).length() + this.scrollOffset);
        }
    }

    public String getSelectedText() {
        int from = Math.min(this.getCursorPosition(), this.selectionEnd);
        int to = Math.max(this.getCursorPosition(), this.selectionEnd);
        return getText().substring(from, to);
    }

    public boolean isTextBoxUnderMouse(int mouseX, int mouseY) {
        return mouseX >=  getX() && mouseX <=  getX() +  getWidth() && mouseY >=  getY() && mouseY <=  getY() +  getHeight();
    }

    public TextBox setSelectionPos(int pos) {
        if (pos < 0)
            pos = 0;

        if (pos > this.getText().length())
            pos = this.getText().length();

        this.selectionEnd = pos;

        if (this.scrollOffset > this.getText().length())
            this.scrollOffset = this.getText().length();

        String trimmed = TextRenderer.getFontRenderer().trimStringToWidth(this.getText().substring(this.scrollOffset),  getWidth());
        int length = trimmed.length() + this.scrollOffset;

        if (pos == this.scrollOffset) {
            this.scrollOffset -= TextRenderer.getFontRenderer().trimStringToWidth(this.getText(),  getWidth(), true).length();
        }

        if (pos > length) {
            this.scrollOffset += pos - length;
        } else if (pos <= this.scrollOffset) {
            this.scrollOffset -= this.scrollOffset - pos;
        }

        if (this.scrollOffset < 0)
            this.scrollOffset = 0;

        if (scrollOffset > this.getText().length())
            this.scrollOffset = this.getText().length();

        return this;
    }

    @Override
    public void onUpdate() {
        this.cursorCounter++;
    }

    public boolean isBackgroundVisible() {
        return visibleBackground;
    }

    public TextBox setBackgroundVisibility(boolean visibleBackground) {
        this.visibleBackground = visibleBackground;
        return this;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public TextBox setIsVisible(boolean isVisible) {
        this.isVisible = isVisible;
        return this;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public TextBox setIsEnabled(boolean isEnabled) {
        this.isEnabled = isEnabled;
        return this;
    }

    public boolean isFocused() {
        return this.isFocused;
    }

    public TextBox setIsFocused(boolean isFocused) {
        this.isFocused = isFocused;
        return this;
    }

    public int getCursorPosition() {
        return this.cursorPos;
    }

    public TextBox setCursorPosition(int pos) {
        this.cursorPos = pos;
        if (this.getCursorPosition() < 0)
            this.cursorPos = 0;
        if (this.getCursorPosition() > this.getText().length())
            this.cursorPos = this.getText().length();

        this.setSelectionPos(this.getCursorPosition());
        return this;
    }

    public int getMaxLength() {
        return this.maxStringLenght;
    }

    public TextBox setMaxLenght(int max) {
        this.maxStringLenght = max;
        return this;
    }

    public String getText() {
        return text;
    }

    public TextBox setEnabledColor(int color) {
        this.enabledColor = color;
        return this;
    }

    public TextBox setDisabledColor(int color) {
        this.disabledColor = color;
        return this;
    }

    public int getEnabledColor() {
        return enabledColor;
    }

    public int getDisabledColor() {
        return disabledColor;
    }

    public TextBox setText(String newText) {
        this.text = newText;
        return this;
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
    
    /**
     * Will set text of textbox and execute TextChangedListener
     * 
     * @param newText
     * @return this
     */
    public TextBox setTextWithEvent(String newText) {
        String previousText = this.text;
        this.setText(newText);
        if (getTextChangedListener() != null)
            getTextChangedListener().onTextChanged(this, previousText);
        return this;
    }

    public TextBox setTextChangedListener(TextChangedListener listener) {
        this.textChangedListener = listener;
        return this;
    }

    public TextChangedListener getTextChangedListener() {
        return textChangedListener;
    }

    @Override
    public TextBox setId(String id) {
        assignId(id);
        return this;
    }
    
    @Override
    public void onClose() {
        Keyboard.enableRepeatEvents(false);
    }

    @FunctionalInterface
    public static interface TextChangedListener {
        void onTextChanged(TextBox textbox, String previousText);
    }

    @Override
    public void shiftX(int x) {
        this.xPos += x;
    }

    @Override
    public void shiftY(int y) {
        this.yPos += y;
    }
}
