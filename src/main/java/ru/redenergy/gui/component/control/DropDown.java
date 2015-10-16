package ru.redenergy.gui.component.control;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.IntStream;

import javax.xml.soap.Text;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.Rectangle;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import ru.redenergy.gui.component.GuiComponent;
import ru.redenergy.gui.component.MultipleModel;
import ru.redenergy.gui.layout.LayoutComponent;
import ru.redenergy.gui.render.Renderer;
import ru.redenergy.gui.render.TextRenderer;

@LayoutComponent
public class DropDown<T> extends GuiComponent implements MultipleModel<T> {

    protected Map<String, DropDownElement<T>> content = new TreeMap<String , DropDownElement<T>>();
    
    @LayoutComponent
    protected int xPos = 0;
    
    @LayoutComponent
    protected int yPos = 0;
    
    @LayoutComponent
    protected int width = 100;
    
    protected int height = 12;
    
    @LayoutComponent
    protected String text;
    
    protected String selected;
    
    protected String hovered;
    
    protected boolean isUnrolled = false;
    
    @LayoutComponent
    protected boolean isVisible = true;
    
    @LayoutComponent
    protected boolean isEnabled = true;
    
    protected ResourceLocation texture = new ResourceLocation("textures/gui/widgets.png");
    
    protected ItemSelectedListener<T> itemSelectedListener;
    
    public DropDown(int xPos, int yPos, int width){
        this(xPos, yPos, width, "");
    }
    
    public DropDown(int xPos, int yPos, int width, String text){
        this.xPos = xPos;
        this.yPos = yPos;
        this.width = width;
        this.height = 12;
        this.text = text;
    }
    
    
    @Override
    public DropDown<T> add(T value){
        return this.add(String.valueOf(value), value);
    }
    
    public DropDown<T> add(String key, T value){
        getContent().put(key, new DropDownElement<T>(getContent().size(), value, key));
        return this;
    }

    @Override
    public DropDown<T> addAll(T ... values){
        Arrays.stream(values).forEach(this::add);
        return this;
    }

    public DropDown<T> addAndSetDefault(T value){
        return addItemAndSetDefault(String.valueOf(value), value);
    }
    
    public DropDown<T> addItemAndSetDefault(String name, T value){
        add(name, value);
        setDefaultItem(name);
        return this;
    }
    
    private void setDefaultItem(String name){
        if(getContent().containsKey(name)){
            this.selected = name;
        }
    }
    
    @Override
    public DropDown<T> remove(T object) {
        this.content.remove(String.valueOf(object));
        return this;
    }
    
    @Override
    public void setup(){
        registerComponent(new Button(getX() + getWidth() - 12, getY(), 12, 12, "\u25BC"));
    }
    
    @Override
    public void onDraw(int mouseX, int mouseY, float partialTicks) {
        if(isEmpty()) {
           setIsEnabled(false);
        }
        if(isVisible()){
            boolean hover = underMouse(mouseX, mouseY);
            drawDropDownBackground();
            if(isUnrolled){
                drawExpandedList(mouseX, mouseY, partialTicks);
            }
            
            if(this.selected != null){
                drawSlot(getSelectedIdentifier(), getX(), getY(), getWidth(), getHeight(), false, 14);
            }
        }
        super.onDraw(mouseX, mouseY, partialTicks);
    }
    
    private void drawDropDownBackground(){
        Renderer.drawRect(getX() - 1, getY() - 1, getX() + getWidth() + 1, getY() + getHeight() + 1, -6250336);
        Renderer.drawRect(getX(), getY(), getX() + getWidth() - 13, getY() + getHeight(), -16777216);
    }
    
    private void drawExpandedList(int mouseX, int mouseY, float partialTicks){
        List<String> keys = new ArrayList(getContent().keySet());
        int unrollHeight = keys.size() * getHeight();
        Renderer.drawRect(getX() - 1, getY() + getHeight(), getX() + getWidth() + 1, getY() + getHeight() + unrollHeight + 1, -6250336);
        Renderer.drawRect(getX(), getY() + getHeight() + 1, getX() + getWidth(), getY() + getHeight() + unrollHeight, -16777216);
        boolean hoverUnrolledList = mouseX >= getX() && mouseX <= getX() + getWidth() && mouseY >= getY() && mouseY <= getY() + getHeight() + unrollHeight + 1;
        for(int index = 0; index < keys.size(); index++){
            String itemIdentifier = keys.get(index);
            int yPos = getY() + getHeight() + (getHeight() / 8) + (index * 12);
            boolean hoverSlot = mouseX >= getX() && mouseX <= getX() + getWidth() && mouseY >= yPos && mouseY <= yPos + 12;
            boolean selectedSlot = hoverSlot || (!hoverUnrolledList && itemIdentifier.equalsIgnoreCase(getSelectedIdentifier()));
            drawSlot(itemIdentifier, getX(), yPos, getWidth(), getHeight(), selectedSlot);
        }
    }
    
    private void drawSlot(String item, int xPos, int yPos, int width, int height, boolean background){
        drawSlot(item, xPos, yPos, width, height, background, 2);
    }
    private void drawSlot(String item, int xPos, int yPos, int width, int height, boolean background, int drawOffset){
        String text = TextRenderer.getFontRenderer().trimStringToWidth(item, width - drawOffset);
        int color = 0xFFFFFF;
        if(background){
            Renderer.drawRect(xPos, yPos, xPos + width, yPos + height - height / 8, 0xFFFFFFFF);
            color = 0;
        }
        TextRenderer.renderString(xPos + 2, yPos + (getHeight() / 8), text, color);
    }

    @Override
    public void onMouseClicked(int posX, int posY, int mouseButtonIndex) {
        super.onMouseClicked(posX, posY, mouseButtonIndex);
        if(isEnabled()){
            boolean inArea = this.isUnrolled ? expandedListUnderMouse(posX, posY) : underMouse(posX, posY);
            
            if(!inArea){
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
                       if(getItemSelectedListener() != null) getItemSelectedListener().onItemSelected(this, selected);
                       this.isUnrolled = false;
                   }
                }
            }
        }
    }

    private boolean underMouse(int x, int y){
        return x >= getX() && x <= getX() + getWidth() && y >= getY() && y <= getY() + getHeight();
    }
    
    private boolean expandedListUnderMouse(int mouseX, int mouseY){
        return mouseX >= getX() - 1 && mouseX < getX() + getWidth() + 1 && mouseY >= getY() - 1 && mouseY < getY() + getHeight() + (getContent().size() * 12) - 1;
    }
    
    public ItemSelectedListener<T> getItemSelectedListener(){
        return itemSelectedListener;
    }
    
    public DropDown<T> setItemSelectedListener(ItemSelectedListener<T> listener){
        this.itemSelectedListener = listener;
        return this;
    }

    @Override
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
    
    public DropDown<T> clear(){
        getContent().clear();
        return this;
    }
    
    public String getSelectedIdentifier(){
        return selected;
    }
    
    public DropDownElement<T> getSelectedElement(){
        return getElement(selected);
    }
    
    public DropDownElement<T> getElement(String identifier){
        return getContent().get(identifier);
    }
    
    public int getX(){
        return xPos;
    }
    
    public int getY(){
        return yPos;
    }

    public int getWidth(){
        return width;
    }

    public int getHeight(){
        return height;
    }
    
    @Override
    public DropDown<T> setId(String id) {
        assignId(id);
        return this;
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
    
    @FunctionalInterface
    public interface ItemSelectedListener<T>{
        public void onItemSelected(DropDown<T> dropdown, String selected);
    }
}
