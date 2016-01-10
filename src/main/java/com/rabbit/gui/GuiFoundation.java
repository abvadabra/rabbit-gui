package com.rabbit.gui;

import com.rabbit.gui.base.Stage;
import com.rabbit.gui.show.IShow;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import net.minecraft.client.Minecraft;

@Mod(modid = "rabbit-gui", name = "Rabbit Gui Library", version = "v1.3.0")
public class GuiFoundation {

    @Mod.EventHandler
    public void postLoad(FMLPostInitializationEvent event) {
        FMLLog.info("Rabbit Gui has been successfully initialized");
    }

    /**
     * If there are any currently opened Stage it will display given show in it <br>
     * Otherwise will create new Stage
     * @param show
     */
    public static void display(IShow show){
        Stage current = getCurrentStage();
        if(current != null){
            current.setShow(show);
            current.reinitShow();
        } else {
            Minecraft.getMinecraft().displayGuiScreen(new Stage());
            getCurrentStage().display(show);
        }
    }

    /**
     * Returns currently opened Stage, may be null
     */
    public static Stage getCurrentStage(){
       return Minecraft.getMinecraft().currentScreen instanceof Stage ? (Stage)Minecraft.getMinecraft().currentScreen : null;
    }

}
