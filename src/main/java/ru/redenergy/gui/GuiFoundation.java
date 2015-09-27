package ru.redenergy.gui;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;

@Mod(modid = "gfoundation", name = "Daozi Gui Library by RedEnergy")
public class GuiFoundation {

	@Mod.EventHandler
	public void postLoad(FMLPostInitializationEvent event){
		FMLLog.info("GuiFoundation has been successfuly initialized");
	}
	
}
