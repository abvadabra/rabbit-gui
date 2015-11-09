package com.rabbit.gui.component.display;

import org.lwjgl.opengl.GL11;

import com.rabbit.gui.component.GuiWidget;

import net.minecraft.entity.Entity;

public class EntityModelDisplay extends GuiWidget {

    
    protected Entity displayEntity;
    
    protected int xPos;
    protected int yPos;
    protected int width;
    protected int height;
    
    public EntityModelDisplay(Entity entity, int xPos, int yPos, int width, int height){
        this.displayEntity = entity;
        this.xPos = xPos;
        this.yPos = yPos;
        this.width = width;
        this.height = height;
    }
    
    @Override
    public void onDraw(int mouseX, int mouseY, float partialTicks) {
        super.onDraw(mouseX, mouseY, partialTicks);
        
    }
    
    private void prepareGl(){
      GL11.glEnable(GL11.GL_COLOR_MATERIAL);
      GL11.glPushMatrix();
      GL11.glTranslatef((float)getX(), (float)getY(), 50.0F);
      GL11.glScalef((float)(-getY()), (float)getY(), (float)getY());
      GL11.glRotatef(-((float)Math.atan((double)(getY() / 40.0F))) * 20.0F, 1.0F, 0.0F, 0.0F);
    }
    
    private void drawModel(){
        
    }

    public Entity getDisplayEntity() {
        return displayEntity;
    }

    public int getX() {
        return xPos;
    }

    public int getY() {
        return yPos;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
    
    
    
    
    
//    public static void func_147046_a(int p_147046_0_, int p_147046_1_, int p_147046_2_, float p_147046_3_, float p_147046_4_, EntityLivingBase p_147046_5_)
//    {
//        GL11.glEnable(GL11.GL_COLOR_MATERIAL);
//        GL11.glPushMatrix();
//        GL11.glTranslatef((float)p_147046_0_, (float)p_147046_1_, 50.0F);
//        GL11.glScalef((float)(-p_147046_2_), (float)p_147046_2_, (float)p_147046_2_);
//        GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
//        float f2 = p_147046_5_.renderYawOffset;
//        float f3 = p_147046_5_.rotationYaw;
//        float f4 = p_147046_5_.rotationPitch;
//        float f5 = p_147046_5_.prevRotationYawHead;
//        float f6 = p_147046_5_.rotationYawHead;
//        GL11.glRotatef(135.0F, 0.0F, 1.0F, 0.0F);
//        RenderHelper.enableStandardItemLighting();
//        GL11.glRotatef(-135.0F, 0.0F, 1.0F, 0.0F);
//        GL11.glRotatef(-((float)Math.atan((double)(p_147046_4_ / 40.0F))) * 20.0F, 1.0F, 0.0F, 0.0F);
//        p_147046_5_.renderYawOffset = (float)Math.atan((double)(p_147046_3_ / 40.0F)) * 20.0F;
//        p_147046_5_.rotationYaw = (float)Math.atan((double)(p_147046_3_ / 40.0F)) * 40.0F;
//        p_147046_5_.rotationPitch = -((float)Math.atan((double)(p_147046_4_ / 40.0F))) * 20.0F;
//        p_147046_5_.rotationYawHead = p_147046_5_.rotationYaw;
//        p_147046_5_.prevRotationYawHead = p_147046_5_.rotationYaw;
//        GL11.glTranslatef(0.0F, p_147046_5_.yOffset, 0.0F);
//        RenderManager.instance.playerViewY = 180.0F;
//        RenderManager.instance.renderEntityWithPosYaw(p_147046_5_, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F);
//        p_147046_5_.renderYawOffset = f2;
//        p_147046_5_.rotationYaw = f3;
//        p_147046_5_.rotationPitch = f4;
//        p_147046_5_.prevRotationYawHead = f5;
//        p_147046_5_.rotationYawHead = f6;
//        GL11.glPopMatrix();
//        RenderHelper.disableStandardItemLighting();
//        GL11.glDisable(GL12.GL_RESCALE_NORMAL);
//        OpenGlHelper.setActiveTexture(OpenGlHelper.lightmapTexUnit);
//        GL11.glDisable(GL11.GL_TEXTURE_2D);
//        OpenGlHelper.setActiveTexture(OpenGlHelper.defaultTexUnit);
//    }
}
