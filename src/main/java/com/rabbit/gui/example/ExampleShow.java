package com.rabbit.gui.example;

import com.rabbit.gui.component.control.Button;
import com.rabbit.gui.component.control.DropDown;
import com.rabbit.gui.component.control.MultiTextbox;
import com.rabbit.gui.show.Show;

public class ExampleShow extends Show {

    @Override
    public void setup() {
        super.setup();
        registerComponent(new DropDown<>(this.width / 3, this.height / 3, this.width / 3, "First", "Second", "Third"));
        registerComponent(new Button(this.width / 3, this.height / 3 + 20, this.width /3, 20, "Something")
                .setClickListener( btn -> this.getStage().displayPrevious()));
        registerComponent(new MultiTextbox(this.width / 3, this.height / 3 * 2, this.width / 3, 50));
        registerComponent(new MultiTextbox(this.width / 3, this.height / 3 * 2 + 55, this.width / 3, 50));
    }
}
