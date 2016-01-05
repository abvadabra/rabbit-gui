package com.rabbit.gui.component;

import com.rabbit.gui.component.control.Button;
import com.rabbit.gui.render.TextAlignment;
import com.rabbit.gui.render.TextRenderer;
import org.lwjgl.input.Keyboard;

public class NumberPicker extends GuiWidget {

    protected int jumpValue = 10;
    protected int value = 0;
    protected int minValue = Integer.MIN_VALUE;
    protected int maxValue = Integer.MAX_VALUE;
    protected NumberChangeListener listener = (p, v) -> {};

    public NumberPicker() {}

    public NumberPicker(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    public NumberPicker(int x, int y, int width, int height, int value) {
        this(x, y, width, height);
        this.value = value;
    }

    @Override
    public void setup() {
        super.setup();
        registerComponent(new Button(this.getX(), getY(), getWidth(), getHeight() / 3, "+")
                .setClickListener(btn -> increase()));
        registerComponent(new Button(this.getX(), getY() + getHeight() / 3 * 2, getWidth(), getHeight() / 3, "-")
                .setClickListener(btn -> decrease()));
    }

    @Override
    public void onDraw(int mouseX, int mouseY, float partialTicks) {
        super.onDraw(mouseX, mouseY, partialTicks);
        TextRenderer.renderString(getX() + getWidth() / 2, getY() + getHeight() / 2 - 5, String.valueOf(value), TextAlignment.CENTER);
    }

    private void increase(){
        int newValue = Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) ? value + jumpValue : value + 1;
        if(newValue < maxValue)
            value = newValue;
        else
            value = maxValue;
        if(getListener() != null) getListener().onNumberChange(this, value);
    }

    private void decrease(){
        int newValue = Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) ? value - jumpValue : value - 1;
        if(newValue > minValue)
            value = newValue;
        else
            value = minValue;
        if(getListener() != null) getListener().onNumberChange(this, value);
    }

    public NumberPicker setMinValue(int minValue){
        this.minValue = minValue;
        return this;
    }

    public NumberPicker setMaxValue(int maxValue){
        this.maxValue = maxValue;
        return this;
    }

    public NumberPicker setJumpValue(int jumpValue){
        this.jumpValue = jumpValue;
        return this;
    }

    public NumberChangeListener getListener() {
        return listener;
    }

    public NumberPicker setListener(NumberChangeListener listener) {
        this.listener = listener;
        return this;
    }

    public int getValue(){
        return value;
    }

    public void setValue(int value){
        this.value = value;
    }

    public static interface NumberChangeListener {
        void onNumberChange(NumberPicker picker, int value);
    }
}
