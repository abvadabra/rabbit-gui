package com.rabbit.example.ui;

import com.rabbit.gui.component.display.graph.PieChart;
import com.rabbit.gui.show.Show;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Example #3
 * This example demonstrates usage of pie graph, how to provide data and titles for it
 *
 * As a result of this code you will get a show with pie graph in the center which displays different data every time you open it
 */
public class PieChartExample extends Show {

    /**
     * Override #setup method as always to register your components
     */
    @Override
    public void setup() {
        super.setup();

        //generate three random values in range 0 - 50 which would be displayed
        double first = ThreadLocalRandom.current().nextDouble(0, 50);
        double second = ThreadLocalRandom.current().nextDouble(0, 50);
        double third = ThreadLocalRandom.current().nextDouble(0, 50);

        //generate titles for our data slices
        String firstTitle = String.valueOf(Math.floor(first));
        String secondTitle = String.valueOf(Math.floor(second));
        String thirdTitle = String.valueOf(Math.floor(third));

        //Creating new PieChart object with coordinates on the screen as two first argument and it's size as the third agument
        //In two last arguments we provide data and titles
        //Note: last argument (with titles) is optional and can be discarded
        registerComponent(new PieChart(this.width / 2 - 100, this.height / 2 - 100, 200,
                new double[]{first, second, third},
                new String[]{firstTitle, secondTitle, thirdTitle}));
    }
}
