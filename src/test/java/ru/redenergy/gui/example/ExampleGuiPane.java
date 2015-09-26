package ru.redenergy.gui.example;

import ru.redenergy.gui.component.Button;
import ru.redenergy.gui.internal.GuiPane;

public class ExampleGuiPane extends GuiPane {
	
	public void onCreate(){
		super.onCreate();
		registerComponent(new Button(this.width / 2, this.height / 2, 200, 15, "Example").setIsEnabled(false));
	}
}
