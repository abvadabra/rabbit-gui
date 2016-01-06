package com.rabbit.example;

import com.rabbit.example.ui.HelloWorldShow;
import com.rabbit.gui.GuiFoundation;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent;
import net.minecraft.client.settings.KeyBinding;
import org.lwjgl.input.Keyboard;


/**
 * This is mode with some usage examples of Rabbit Gui library <br>
 * All UIs (we prefer to call them 'Show') a located in the com.rabbit.examples.ui package
 *
 * You also can see some demonstration of key handling and displaying of new ui
 */
@Mod(modid = "gui-examples")
public class GuiExampleMod {

    private static final KeyBinding exampleKeyBind = new KeyBinding("Example", Keyboard.KEY_L, "Opens some show");

    @Mod.Instance("gui-example")
    public static GuiExampleMod instance;

    @Mod.EventHandler
    public void init(FMLInitializationEvent event){
        ClientRegistry.registerKeyBinding(exampleKeyBind);
        FMLCommonHandler.instance().bus().register(this);
    }

    @SubscribeEvent
    public void onKeyPress(InputEvent.KeyInputEvent event){
        if(exampleKeyBind.isPressed()){
            GuiFoundation.display(new HelloWorldShow());
//            GuiFoundation.display(new WorkingWithIDs());
//            GuiFoundation.display(new PieChartExample());
        }
    }

}
