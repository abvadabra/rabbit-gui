package com.rabbit.gui.component.display;

import com.rabbit.gui.component.control.ScrollBar;
import com.rabbit.gui.render.TextAlignment;
import com.rabbit.gui.render.TextRenderer;
import com.rabbit.gui.utils.Geometry;
import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.GL11;

import java.util.List;

public class ScrollTextLabel extends TextLabel {

    private ScrollBar scrollBar;

    public ScrollTextLabel(int xPos, int yPos, int width, String text) {
        super(xPos, yPos, width, text);
    }

    public ScrollTextLabel(int xPos, int yPos, int width, int height, String text) {
        super(xPos, yPos, width, height, text);
    }

    public ScrollTextLabel(int xPos, int yPos, int width, int height, String text, TextAlignment align) {
        super(xPos, yPos, width, height, text, align);
    }

    @Override
    public void setup() {
        super.setup();
        if(isMultilined()) {
            int scrollerSize = Math.max(this.height / getLines().size(), 10);
            scrollBar = new ScrollBar(getX() + this.width - 10, getY(), 10, this.height, scrollerSize).setHandleMouseWheel(false);
            registerComponent(scrollBar);
        }
    }

    @Override
    protected void drawMultilined() {
        scrollBar.setVisiblie(!canFit());
        List<String> displayLines = getLines();
        int scale = Geometry.computeScaleFactor();
        for(int i = 0; i < displayLines.size(); i++){
            String line = displayLines.get(i);
            int lineY = ((getY() + i * 10) - (int) ((10 * scrollBar.getProgress() * displayLines.size()) - ((this.height - 10) * (scrollBar.getProgress())/ 1)));
            GL11.glEnable(GL11.GL_SCISSOR_TEST);
            GL11.glScissor(getX() * scale, Minecraft.getMinecraft().displayHeight - (getY() + getHeight()) * scale, getWidth() * scale, getHeight() * scale);
            drawAlignedLine(getX(), lineY, getWidth() - 10, line, this.alignment);
            GL11.glDisable(GL11.GL_SCISSOR_TEST);
        }

    }

    /**
     * Evaluates if current content of textlabel can fit into it's height <br/>
     * Used to determine if scrollbar should be vissble
     */
    private boolean canFit(){
        int content = getLines().size() * 10;
        return content < this.height; //10 - height of one symbol
    }

    private List<String> getLines(){
        return TextRenderer.getFontRenderer().listFormattedStringToWidth(this.getText(), this.width - 10);
    }
}
