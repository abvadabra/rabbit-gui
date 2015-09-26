package ru.redenergy.gui;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.logging.log4j.core.Logger;
import org.lwjgl.input.Keyboard;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import net.minecraft.client.Minecraft;
import ru.redenergy.gui.example.ExampleGuiPane;
import skymine.redenergy.core.Core;

@Mod(modid = "gfoundation", name = "GuiFoundation Library by RedEnergy")
public class GuiFoundation {

	@Mod.EventHandler
	public void postLoad(FMLPostInitializationEvent event){
		FMLLog.info("GuiFoundation has been successfuly initialized");
		Core.getKeyboardHandler().registerKeyBinding(Keyboard.KEY_L, "s", (ev) -> {
			Minecraft.getMinecraft().displayGuiScreen(new ExampleGuiPane());
		});
	}
	
}
