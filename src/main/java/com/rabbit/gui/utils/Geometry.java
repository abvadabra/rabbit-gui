package com.rabbit.gui.utils;

public class Geometry {

    public static boolean isDotInArea(int areaX, int areaY, int areaWidth, int areaHeight, int dotX, int dotY){
        return dotX >= areaX && dotX <= areaX + areaWidth && dotY >= areaY && dotY <= areaY + areaHeight; 
    }
    
}
