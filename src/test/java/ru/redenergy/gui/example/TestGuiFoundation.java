package ru.redenergy.gui.example;

import org.lwjgl.input.Keyboard;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import net.minecraft.client.Minecraft;
import skymine.redenergy.core.Core;

@Mod(modid = "testgfoundation")
public class TestGuiFoundation {

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        Core.getKeyboardHandler().registerKeyBinding(Keyboard.KEY_L, "",
                (kevent) -> Minecraft.getMinecraft().displayGuiScreen(new ExampleGuiPane()));
    }
}
