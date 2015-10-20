package com.rabbit.gui.component.table;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import org.lwjgl.util.Rectangle;

import com.rabbit.gui.component.GuiComponent;
import com.rabbit.gui.component.control.Button;
import com.rabbit.gui.render.Renderer;
import com.rabbit.gui.render.TextRenderer;

import net.minecraft.util.EnumChatFormatting;

public class Table extends GuiComponent {

    protected int xPos = 0;
    
    protected int yPos = 0;
    
    protected int width = 100;
    
    protected int height = 20;
    
    protected List<Row> rows;
    
    protected boolean isVisible = true;
    protected boolean isEnabled = true;
    
    protected boolean verticalLines = true;
    protected boolean horizontalLines = true;
    
    protected boolean drawBackground = true;
    
    public Table(int xPos, int yPos, int width, int height, Row ... rows){
        this.xPos = xPos;
        this.yPos = yPos;
        this.width = width;
        this.height = height;
        this.rows = new ArrayList<Row>(Arrays.asList(rows));
    }
    
    @Override
    public void onDraw(int mouseX, int mouseY, float partialTicks) {
        if(isVisible()){
            if(shouldDrawBackground()){
                drawBackground();
            }
            int oneRowWidth = getWidth() / getRows().size();
            int oneLineHeight = getHeight() / getLongestRow().getContent().size();
            for(int i = 0; i < getRows().size(); i++){
                drawRow(getX() + oneRowWidth * i, getY(), oneRowWidth, getHeight(), oneLineHeight, getRows().get(i));
                if(i + 1 != getRows().size() && drawVerticalLines()){
                    Renderer.drawRect(getX() + oneRowWidth * i + oneRowWidth - 1, getY() + 5, getX() + oneRowWidth * i + oneRowWidth, getY() + getHeight() - 5, -6250336);
                }
            }
        }
    }

    private void drawRow(int xPos, int yPos, int width, int height, int oneLineHeight, Row row){
        TextRenderer.renderCenteredString(xPos + width / 2, yPos + 5, EnumChatFormatting.UNDERLINE + row.getName());
        List<String> lines = row.getStringContent();
        for(int i = 0; i < row.getContent().size(); i++){
            TextRenderer.renderCenteredString(xPos + width / 2, yPos + oneLineHeight / 2 + (oneLineHeight * i), lines.get(i));
            if(i + 1 != row.getContent().size() && drawHorizontalLines()){
                Renderer.drawRect(xPos + 5, yPos + oneLineHeight * i + oneLineHeight, xPos + width - 5, yPos + oneLineHeight * i + oneLineHeight + 1, -6250336);
            }
        }
    }
    
    public Table setDrawBackground(boolean flag){
        this.drawBackground = flag;
        return this;
    }
    
    public boolean shouldDrawBackground(){
        return drawBackground;
    }

    private Row getLongestRow(){
        Row row = null;
        for(Row r : getRows()){
            if(row == null || row.getContent().size() < r.getContent().size()){
                row = r;
            }
        }
        return row;
    }
    private void drawBackground(){
        Renderer.drawRect(getX() - 1, getY() - 1, getX() + getWidth() + 1, getY() + getHeight() + 1, -6250336);
        Renderer.drawRect(getX(), getY(), getX() + getWidth(), getY() + getHeight(), -16777216);
    }
    
    public Table setDrawVerticalLines(boolean flag){
        this.verticalLines = flag;
        return this;
    }
    
    public Table setDrawHorizontalLines(boolean flag){
        this.horizontalLines = flag;
        return this;
    }
    
    public boolean drawVerticalLines(){
        return verticalLines;
    }
    
    public boolean drawHorizontalLines(){
        return horizontalLines;
    }
    
    public Table addRow(Row row){
        this.rows.add(row);
        return this;
    }
    
    public boolean isVisible() {
        return isVisible;
    }

    public Table setIsVisible(boolean isVisible) {
        this.isVisible = isVisible;
        return this;
    }
    
    public List<Row> getRows(){
        return rows;
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

    @Override
    public Table setId(String id) {
        assignId(id);
        return this;
    }

}
