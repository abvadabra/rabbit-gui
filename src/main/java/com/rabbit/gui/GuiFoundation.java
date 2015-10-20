package com.rabbit.gui;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;

@Mod(modid = "rabbit-gui", name = "Rabbit Gui Library by RedEnergy")
public class GuiFoundation {

    @Mod.EventHandler
    public void postLoad(FMLPostInitializationEvent event) {
        FMLLog.info("Rabbit Gui has been successfuly initialized");
    }

}
