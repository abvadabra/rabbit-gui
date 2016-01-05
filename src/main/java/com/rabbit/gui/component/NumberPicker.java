package com.rabbit.gui.component;

import com.rabbit.gui.component.control.Button;
import com.rabbit.gui.render.TextAlignment;
import com.rabbit.gui.render.TextRenderer;

public class NumberPicker extends GuiWidget {

    protected int value = 0;
    protected int minValue = Integer.MIN_VALUE;
    protected int maxValue = Integer.MAX_VALUE;

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
        if(value < maxValue){
            value++;
        }
    }

    private void decrease(){
        if(value > minValue){
            value--;
        }
    }

    public NumberPicker setMinValue(int minValue){
        this.minValue = minValue;
        return this;
    }

    public NumberPicker setMaxValue(int maxValue){
        this.maxValue = maxValue;
        return this;
    }

    public int getValue(){
        return value;
    }

    public void setValue(int value){
        this.value = value;
    }
}
