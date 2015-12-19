package com.rabbit.example.ui;

import com.rabbit.gui.component.control.Button;
import com.rabbit.gui.component.control.TextBox;
import com.rabbit.gui.show.Show;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;

/**
 * Example #2
 * This example demonstrates usage of components ids, how to define them and request component by id
 *
 * As a result of this code you will get the screen with text box and button centered in the middle of a screen,
 * after clicking on a button text from text box will be printed in chat
 */
public class WorkingWithIDs extends Show {

    /**
     * Override #setup method as always to register your components
     */
    @Override
    public void setup() {
        //don't forget to call parent method
        super.setup();

        //Registering text box centered in the middle with width 200px, height 20px and default text "Some important text..."
        //After creating new button we are using #setId method to define id for this component, in our case id equal to "input_field"
        registerComponent(new TextBox(this.width / 2 - 100, this.height / 3, 200, 20, "Some important text...")
                            .setId("input_field"));

        //Registering new button in the center of the screen below text box
        //In the click listener we are calling #prinMessage method
        registerComponent(new Button(this.width / 2 - 100, this.height / 3 + 22, 200, 20, "Print message")
                            .setClickListener(btn -> printMessage()));
    }

    /**
     * In this message we're getting text from text box and printing it in chat
     */
    private void printMessage(){
        //Finding text box by id, note that it can be null if no component with such id has been registered
        TextBox box = (TextBox) findComponentById("input_field");
        //Here we get the text from text field
        String text = box.getText();
        //Printing message in chat
        Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(text));
    }
}
