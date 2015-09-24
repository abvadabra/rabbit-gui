package ru.redenergy.gui;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.logging.log4j.core.Logger;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;

@Mod(modid = "gfoundation", name = "GuiFoundation Library by RedEnergy")
public class GuiFoundation {

	@Mod.EventHandler
	public void postLoad(FMLPostInitializationEvent event){
		FMLLog.info("GuiFoundation has been successfuly initialized");
	}
	
}
