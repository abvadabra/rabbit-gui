package ru.redenergy.gui.layout;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.objecthunter.exp4j.function.Function;
import ru.redenergy.gui.base.Stage;
import ru.redenergy.gui.show.IShow;

public class LayoutFunctions {

    public static final Function width = new Function("width", 0){
        public double apply(double... args) {
            IShow currentShow = getCurrentlyOpenedShow();
            return currentShow != null ? currentShow.getWidth() : 0;
        }
    };
    
    public static final Function height = new Function("height", 0){
        public double apply(double... args) {
            IShow currentShow = getCurrentlyOpenedShow();
            return currentShow != null ? currentShow.getHeight() : 0;
        }
    };
    
    public static IShow getCurrentlyOpenedShow(){
        GuiScreen currentScreen = Minecraft.getMinecraft().currentScreen;
        if(currentScreen != null && currentScreen instanceof Stage){
            return ((Stage) currentScreen).getShow();
        }
        return null;
    }
}
