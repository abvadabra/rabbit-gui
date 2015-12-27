package com.rabbit.gui.component.display.graph;

import com.rabbit.gui.component.GuiWidget;
import com.rabbit.gui.render.Renderer;

import java.awt.*;
import java.util.stream.DoubleStream;

public class PieChart extends GuiWidget {

    /**
     * Contains colors which will be used in diagram, by default it's filled with six common colors from java.awt.Color class
     */
    protected Color[] colors = {Color.BLUE, Color.RED, Color.ORANGE, Color.GREEN, Color.MAGENTA, Color.pink};
    /**
     * Width and height of the diagram
     */
    protected int size;
    /**
     * Each data value represents piece of diagram
     */
    protected double[] data = new double[0];
    /**
     * Contains display angle for each data value, usually calculated in constructor
     */
    protected double[] angles = new double[0];
    /**
     * Sum of data, usually calculated in constructor
     */
    protected double total;

    private PieChart() {}

    public PieChart(int x, int y, int size, double[] data) {
        super(x, y, size, size);
        this.size = size;
        this.data = data;
        initialCalculate();
    }

    protected void initialCalculate(){
        angles = new double[data.length];
        total = DoubleStream.of(data).sum();
        for(int i = 0; i < data.length; i++){
            angles[i] = data[i] / total * 360;
        }
    }

    @Override
    public void onDraw(int mouseX, int mouseY, float partialTicks) {
        super.onDraw(mouseX, mouseY, partialTicks);
        double prevAngle = 0;
        for(int i = 0; i < data.length; i++){
            Color color = colors[i % colors.length];
            Renderer.drawFilledArc(this.x + this.width / 2, this.y + this.height / 2, size / 2, prevAngle, angles[i] + prevAngle, color.getRGB());
            prevAngle += angles[i];
        }
    }

    public PieChart setColors(Color[] colors){
        this.colors = colors;
        return this;
    }

    /**
     * Updates data and recalculates angles
     */
    public PieChart setData(double[] data){
        this.data = data;
        initialCalculate();
        return this;
    }

    /**
     * Display angle for each data value
     */
    public double[] getAngles(){
        return angles;
    }

    public double[] getData(){
        return data;
    }
}
