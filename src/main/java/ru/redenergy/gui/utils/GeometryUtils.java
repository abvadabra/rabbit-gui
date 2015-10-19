package ru.redenergy.gui.utils;

public class GeometryUtils {

    public static boolean isDotInArea(int areaX, int areaY, int areaWidth, int areaHeight, int dotX, int dotY){
        return dotX >= areaX && dotX <= areaX + areaWidth && dotY >= areaY && dotY <= areaY + areaHeight; 
    }
    
}
