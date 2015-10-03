package ru.redenergy.gui.component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.IntStream;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.Rectangle;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import ru.redenergy.gui.render.Renderer;
import ru.redenergy.gui.render.TextRenderer;

public class DropDown<T> extends GuiComponent {

    protected Map<String, DropDownElement<T>> content = new TreeMap<String , DropDownElement<T>>();
    
    protected Rectangle shape;
    
    protected String text;
    
    protected String selected;
    
    protected String hovered;
    
    protected boolean isUnrolled = false;
    
    protected boolean isVisible = true;
    
    protected boolean isEnabled = true;
    
    protected ResourceLocation texture = new ResourceLocation("textures/gui/widgets.png");
    
    public DropDown(int xPos, int yPos, int width){
        this(xPos, yPos, width, "");
    }
    
    public DropDown(int xPos, int yPos, int width, String text){
        this(new Rectangle(xPos, yPos, width, 12), text);
    }
    
    public DropDown(Rectangle shape){
        this(shape, "");
    }
    
    public DropDown(Rectangle shape, String text) {
        this.shape = shape;
        if(shape.getHeight() >= 12) shape.setHeight(12);
        this.text = text;
    }
    
    public DropDown<T> addItem(T value){
        return this.addItem(String.valueOf(value), value);
    }
    
    public DropDown<T> addItem(String key, T value){
        getContent().put(key, new DropDownElement<T>(getContent().size(), value, key));
        return this;
    }
    
    public DropDown<T> addItems(T ... values){
        return addItems(Arrays.asList(values));
    }
    
    public DropDown<T> addItems(Collection<? extends T> values){
        values.forEach(this::addItem);
        return this;
    }
    
    public DropDown<T> addItemAndSetDefault(T value){
        return addItemAndSetDefault(String.valueOf(value), value);
    }
    
    public DropDown<T> addItemAndSetDefault(String name, T value){
        addItem(name, value);
        setDefaultItem(name);
        return this;
    }
    
    private void setDefaultItem(String name){
        if(getContent().containsKey(name)){
            this.selected = name;
        }
    }
    
    @Override
    public void onDraw(int mouseX, int mouseY, float partialTicks) {
        if(isEmpty()) {
           setIsEnabled(false);
        }
        
        if(isVisible()){
            boolean hover = underMouse(mouseX, mouseY);
            
            Renderer.drawRect(getX() - 1, getY() - 1, getX() + getWidth() + 1, getY() + getHeight() + 1, -6250336);
            Renderer.drawRect(getX(), getY(), getX() + getWidth() - 13, getY() + getHeight(), -16777216);
            
            GL11.glPushMatrix();
            GL11.glColor4f(1F, 1F, 1F, 1F);
            
            Minecraft.getMinecraft().renderEngine.bindTexture(texture);
            int yLocTop = isEnabled() ? hover ? 86 : 66 : 46;
            int yLocBot = isEnabled() ? hover ? 100 : 80 : 60;
            Renderer.drawTexturedModalRect(getX() + getWidth() - 12, getY(), 0, yLocTop, 6, 6);
            Renderer.drawTexturedModalRect(getX() + getWidth() - 6, getY(), 194, yLocTop, 6, 6);
            Renderer.drawTexturedModalRect(getX() + getWidth() - 12, getY() + 6, 0, yLocBot, 6, 6);
            Renderer.drawTexturedModalRect(getX() + getWidth() - 6, getY() + 6, 194, yLocBot, 6, 6);
            
            GL11.glTranslated(0, 0, 0);
            GL11.glRotatef(0, 0, 1, 0);
            
            TextRenderer.renderCenteredString(getX() + getWidth() - 6, getY() + getHeight() / 8, isUnrolled ? "<" : ">");
            GL11.glPopMatrix();
            
            if(isUnrolled){
                drawUnrolledContent(mouseX, mouseY, partialTicks);
            }
            
            if(this.selected != null){
                String value = TextRenderer.getFontRenderer().trimStringToWidth(selected, getWidth() - 14);
                TextRenderer.renderString(getX() + 2, getY() + getHeight() / 8, value);
            }
        }
        
        
    }
    
    private void drawUnrolledContent(int mouseX, int mouseY, float partialTicks){
        List<String> keys = new ArrayList(getContent().keySet());
        
        int unrollHeight = keys.size() * getHeight();
        Renderer.drawRect(getX() - 1, getY() + getHeight(), getX() + getWidth() + 1, getY() + getHeight() + unrollHeight + 1, -6250336);
        Renderer.drawRect(getX(), getY() + getHeight() + 1, getX() + getWidth(), getY() + getHeight() + unrollHeight, -16777216);
        
        boolean hoverUnrolledList = mouseX >= getX() && mouseX <= getX() + getWidth() && mouseY >= getY() && mouseY <= getY() + getHeight() + unrollHeight + 1;
        
        for(int index = 0; index < keys.size(); index++){
            String item = keys.get(index);
            int yPos = getY() + getHeight() + (getHeight() / 8) + (index * 12);
            
            boolean hoverItem = mouseX >= getX() && mouseX <= getX() + getWidth() && mouseY >= yPos && mouseY <= yPos + 12;
            if(hoverItem){
                Renderer.drawRect(getX(), yPos, getX() + getWidth(), yPos + 11, 0xFFFFFFFF);
                TextRenderer.renderString(getX() + 2, yPos + (11 / 8), TextRenderer.getFontRenderer().trimStringToWidth(item, getWidth() - 2), 0); //black text
            } else {
                TextRenderer.renderString(getX() + 2, yPos + (11 / 8), TextRenderer.getFontRenderer().trimStringToWidth(item, getWidth() - 2));
                if(!hoverUnrolledList && this.selected != null && this.selected.equals(item)){
                    Renderer.drawRect(getX(), yPos, getX() + getWidth(), yPos + 11, 0xFFFFFFFF);
                    TextRenderer.renderString(getX() + 2, yPos + (11 / 8), TextRenderer.getFontRenderer().trimStringToWidth(item, getWidth() - 2), 0);
                }
            }
        }
    }

    @Override
    public void onMouseClicked(int posX, int posY, int mouseButtonIndex) {
        if(isEnabled()){
            boolean hovered = this.isUnrolled ? contentUnderMouse(posX, posY) : underMouse(posX, posY);
            
            if(!hovered){
                this.isUnrolled = false;
            }
            
            if(underMouse(posX, posY) && !isEmpty()){
                this.isUnrolled = !this.isUnrolled;
            }
            
            if(this.isUnrolled){
                List<String> contentKeys = new ArrayList(getContent().keySet());
                for(int index = 0; index < contentKeys.size(); index++) {
                   int yPos = getY() + getHeight() + (getHeight() / 8) + (index * 12);
                   boolean hoverItem = posX >= getX() && posX <= getX() + getWidth() && posY >= yPos && posY <= yPos + 12;
                   if(hoverItem){
                       this.selected = contentKeys.get(index);
                       this.isUnrolled = false;
                   }
                }
            }
        }
    }

    private boolean underMouse(int x, int y){
        return x >= getX() && x <= getX() + getWidth() && y >= getY() && y <= getY() + getHeight();
    }
    
    private boolean contentUnderMouse(int mouseX, int mouseY){
        return mouseX >= getX() - 1 && mouseX < getX() + getWidth() + 1 && mouseY >= getY() - 1 && mouseY < getY() + getHeight() + (getContent().size() * 12) - 1;
    }

    public Map<String, DropDownElement<T>> getContent(){
        return this.content;
    }
    
    public boolean isEnabled(){
        return isEnabled;
    }
    
    public DropDown<T> setIsEnabled(boolean isEnabled){
        this.isEnabled = isEnabled;
        return this;
    }
    
    public boolean isVisible(){
        return isVisible;
    }
    
    public DropDown<T> setIsVisible(boolean isVisible){
        this.isVisible = isVisible;
        return this;
    }
    
    public boolean isEmpty(){
        return this.content != null ? content.isEmpty() : true;
    }
    
    public Rectangle getShape(){
        return shape;
    }
    
    public int getX(){
        return getShape().getX();
    }
    
    public int getY(){
        return getShape().getY();
    }

    public int getWidth(){
        return getShape().getWidth();
    }

    public int getHeight(){
        return getShape().getHeight();
    }
    
    public class DropDownElement<K> {
        
        private final int itemIndex;
        private final K itemValue;
        private final String itemName;
        
        public DropDownElement(int itemIndex, K itemValue, String itemName){
            this.itemIndex = itemIndex;
            this.itemValue = itemValue;
            this.itemName = itemName;
        }
        
        public K getValue(){
            return this.itemValue;
        }
        
        public int getItemIndex(){
            return this.itemIndex;
        }
        
        public String getItemName(){
            return this.itemName;
        }
    }

}
