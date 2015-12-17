package com.rabbit.example.ui;

import com.rabbit.gui.component.control.Button;
import com.rabbit.gui.show.Show;

/**
 * Example #1
 * This example demonstrate very basic usage of Button component and working with click listener
 *
 * As a result of this code you will get the screen with a button centered in the middle
 * After clicking on a button you will see words "Hello World!" printed in the console
 */
public class HelloWorldShow extends Show {

    /**
     * You should override #setup method in order to register component <br>
     * Keep in mind that you can register components only in this method
     */
    @Override
    public void setup(){
        //Always call parent method using super.setup(). It prevents some things from breaking
        super.setup();

        //You must register each component using #registerComponent method
        //In code below we're creating button centered in the middle of a screen, with width 200 and height 20, which displays the message "Click me!"
        //Note: all component are designed to support Fluent Interface (https://en.wikipedia.org/wiki/Fluent_interface) so feel free to use it
        //After initializing new button we're calling method #setClickListener in which we creating new lambda expression
        //which takes button object as an argument and calls System.out.println("Hello World") statement
        registerComponent(new Button(this.width / 2 - 100, this.height / 2, 200, 20, "Click Me!")
                            .setClickListener(btn -> System.out.println("Hello World!")));
    }
}
