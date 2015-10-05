package ru.redenergy.gui.example;

import ru.redenergy.gui.component.control.Button;
import ru.redenergy.gui.internal.GuiPane;

public class ExampleGuiPane extends GuiPane {


    @Override
    public void setup() {
        super.setup();
        registerComponent(new Button(this.width / 2 - 300 , this.height / 2 + 20, 500, 10, "Example").setId("btn").setClickListener(button -> button.setIsVisible(!button.isVisible())));
//        registerComponent(new TextBox(this.width / 2, this.height / 2 - 50, 200, 20, "Initial text"));
//        registerComponent(new DropDown(this.width / 2 - 200, this.height / 2, 100)
//                .addItemAndSetDefault("First")
//                .addItems("Second", "Third", "Fourth").setItemSelectedListener((dd, s) -> ((Button)findComponentById("btn")).setText(s)));
//        registerComponent(new Table(this.width / 2 - 200, this.height / 2 - 100, this.width / 2, this.height / 2)
//                  .addRow(new Row("Words", "OMG", "SoDOge", "LOL", "Genius"))
//                  .addRow(new Row("Pokes", "Slowpoke", "Applejack", "Morby"))
//                  .addRow(new Row("Colors", "Red", "Green", "Purple", "Blue", "Gray"))
//                  .setDrawHorizontalLines(false)
//                  .setDrawBackground(false));
    }

}
