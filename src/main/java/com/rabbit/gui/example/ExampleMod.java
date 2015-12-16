package com.rabbit.gui.example;

import com.rabbit.gui.GuiFoundation;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import org.lwjgl.input.Keyboard;
import skymine.redenergy.core.Core;

@Mod(modid = "example-mod")
public class ExampleMod {
    @Mod.Instance("example-mod")
    public static ExampleMod instance;

    @Mod.EventHandler
    public void onInit(FMLInitializationEvent event){
        Core.getKeyboardHandler().registerKeyBinding(Keyboard.KEY_L, "Example", (keyEvent) -> GuiFoundation.display(new ExampleShow()));
    }
}
