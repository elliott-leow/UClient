package me.elliottleow.kabbalah.module.modules.visual;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.Project;

import me.elliottleow.kabbalah.module.Category;
import me.elliottleow.kabbalah.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.client.event.RenderHandEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent;

public class SovereignSword extends Module {

	public SovereignSword() {
		super("Sovereign Sword", "Adds inspect held item from CS:GO and Valorant", Category.Visual);
		this.setKey(Keyboard.KEY_Z);
	}
	
	protected static final Minecraft mc = Minecraft.getMinecraft();
	boolean inspect = false;
	int timer=0;
	float pticks;
	
	@SubscribeEvent
	public void keyDown(KeyInputEvent event) {
		if (mc.thePlayer!=null && mc.theWorld!=null) {
			if (FMLClientHandler.instance().isGUIOpen(GuiChat.class) || mc.currentScreen != null) return;
			if (Keyboard.isKeyDown(Keyboard.KEY_Y)) {
				if (inspect) {
					timer=0;
					inspect=false;
				}
				if (!inspect) inspect=true;
			}
		}
	}
	
	@SubscribeEvent
	public void onAttack(AttackEntityEvent event) {
		if (mc.thePlayer!=null && mc.theWorld!=null) {
			inspect=false;
		}
	}
	
	@SubscribeEvent
	public void renderHand(RenderHandEvent e) {
		if (!inspect) {
			timer=0;
		}
		boolean flag = Minecraft.getMinecraft().getRenderViewEntity() instanceof EntityLivingBase && ((EntityLivingBase)Minecraft.getMinecraft().getRenderViewEntity()).isPlayerSleeping();
		if (Minecraft.getMinecraft().thePlayer.getCurrentEquippedItem() == null) {
			inspect=false;
		}
		if (Minecraft.getMinecraft().gameSettings.thirdPersonView == 0 && !flag && !Minecraft.getMinecraft().gameSettings.hideGUI  && !Minecraft.getMinecraft().playerController.isSpectator()) {
			pticks = e.partialTicks;
			GlStateManager.clear(256);
			GlStateManager.matrixMode(5889);
	        GlStateManager.loadIdentity();
	        Project.gluPerspective(0.0F, (float)Minecraft.getMinecraft().displayWidth / (float)Minecraft.getMinecraft().displayHeight, 0.05F, (Minecraft.getMinecraft().gameSettings.renderDistanceChunks * 16) * 2.0F);
	        GlStateManager.matrixMode(5888);
	        GlStateManager.loadIdentity();
	        GlStateManager.pushMatrix();
	        mc.entityRenderer.enableLightmap();
	        //Rest Position
	        GL11.glScalef(1.9f, 2f, -1.9f);
	        GL11.glTranslatef(0.1f, 0.3f, 0.6f);
	        GL11.glRotatef(35f, -1f, 3f, 1f);
	        GL11.glRotatef(-10f, 0f, 0f, 1f);
	        GL11.glRotatef(-5f, 1f, 0f, 0f);
	        if (inspect) {
			    if(timer >= 0 && timer < 2000){
			    	//Inspect 1
			        GL11.glTranslatef(-0.25f, 0.5f, 0.0f);
			        GL11.glRotatef(-30f, 0.1f, 0f, -0.0f);
			    }
			    if (timer >= 1000){// && timer < 2000) {
			    	//Inspect 2
			    	//NOT DONE WITH THIS YET TRYING TO CUT DOWN ON ROTATIONS
			    	GL11.glTranslatef(-0.0f, 0.0f, 0.0f);
			        GL11.glRotatef(0f, 0.1f, 0.1f, 1f);
			    }
		        /*if (timer >= 2000) {
		        	inspect=false;
			        timer=0;
		        }*/
			}
	        Minecraft.getMinecraft().getItemRenderer().renderItemInFirstPerson(pticks);
		    mc.entityRenderer.disableLightmap();
	        GlStateManager.popMatrix();
			timer++;
			e.setCanceled(true);
			
	        //Dagger position
	        /*
	        GL11.glTranslatef(0.53f, 0.3f, 0.6f);
	        GL11.glRotatef(35f, -1f, 3f, 1f);
	        GL11.glRotatef(-10f, 0f, 0f, 1f);
	        GL11.glRotatef(-5f, 1f, 0f, 0f);
	        
	        //Inspect 1
	        GL11.glTranslatef(-0.07f, 0.55f, 0.07f);
	        GL11.glRotatef(-50f, 1f, 0f, 0f);
	        GL11.glRotatef(50f, -1f, 10f, 1f);
	        */
	        
	        
	        //GL11.glTranslated(1d, 0d, 1d);
	        //GL11.glRotatef(90f, 0f, 1f, 0f);
	        //GL11.glRotatef(-120f, 1f, 1f, 1f);
	        //GL11.glRotatef(45f, 1f, 0f, 1f);
	        
	        //GL11.glRotatef(20.0f, 21.0f, 2.0f, -20.0f);

	        //GlStateManager.rotate(1 * -20.0F, 0.0F, 1.0F, 0.0F);
	        //GlStateManager.rotate(1 * -20.0F, 0.0F, 0.0F, 1.0F);
	        //GlStateManager.rotate(1 * -80.0F, 1.0F, 0.0F, 0.0F);
	        //GlStateManager.scale(0.4F, 0.4F, 0.4F);
	        //GL11.glRotatef(0f, 0f, 1f, 1f);
	        //GL11.glRotatef(-40f, 0.0f, 0.5f, 0.005f);
	        //GL11.glTranslatef(-0.31f, 0.0f, -1.0f);
	        //GL11.glRotatef(10.0f, 1.0f, 0f, 10.0f);
	        
	        //GL11.glRotatef(-30.0f, 0.0f, 10f, 10.0f);
	        //GL11.glTranslatef(-1.0f, -0.0f, 0.0f);

	        //GL11.glTranslatef(-0.3f, 0.0f, 0.04f);
	        //GL11.glRotatef(0.0f, 0.0f, 0.0f, 1.0f);
	        //GL11.glRotatef(0.0f, 0.0f, 0.0f, 0.0f);
	        //GL11.glTranslatef(-0.63f, 0.2f, 0.0f);
	        //GL11.glRotatef(0.0f, 0.0f, 0.0f, 0.0f);
			//GL11.glRotatef(20F, 0F, 0F, 1F);
			//GL11.glTranslatef(0.25F, 0F, 0F);
			//GL11.glScalef(1F, 1F, 1F);
			//GL11.glRotatef(90F, -10F, 0F, 0F);
	        
        }
		else {
			inspect=false;
		}
	}
	
	/*
	@SubscribeEvent
    public void onPlayerRender(final RenderPlayerEvent.Specials.Pre event) {
        final ItemStack heldItem = event.entityPlayer.getHeldItem();
        for (int slot = 0; slot < 9; ++slot) {
            if (event.entityPlayer.inventory.getStackInSlot(slot) != null && event.entityPlayer.inventory.getStackInSlot(slot).getItem() instanceof ItemSword && (heldItem == null || heldItem.getItem() != event.entityPlayer.inventory.getStackInSlot(slot).getItem())) {
                GL11.glPushMatrix();
                if (!event.entityPlayer.isSneaking()) {
                    if (event.entityPlayer.inventory.armorItemInSlot(2) != null || event.entityPlayer.inventory.armorItemInSlot(1) != null) {
                        GL11.glTranslatef(0.34f, 0.8f, -0.22f);
                    }
                    else {
                        GL11.glTranslatef(0.3f, 0.8f, -0.22f);
                    }
                    GL11.glRotatef(96.0f, -2.0f, -90.0f, 1.0f);
                    GL11.glRotatef(180.0f, 90.0f, -12.0f, 0.0f);
                }
                else if (event.entityPlayer.isSneaking()) {
                    if (event.entityPlayer.inventory.armorItemInSlot(2) != null || event.entityPlayer.inventory.armorItemInSlot(1) != null) {
                        GL11.glTranslatef(0.34f, 0.84f, 0.04f);
                    }
                    else {
                        GL11.glTranslatef(0.3f, 0.84f, 0.04f);
                    }
                    GL11.glRotatef(96.0f, -2.0f, -90.0f, 1.0f);
                    GL11.glRotatef(180.0f, 35.0f, -12.0f, 0.0f);
                }
                GL11.glEnable(3042);
                GL11.glBlendFunc(770, 771);
                renderItem(event.entityPlayer.inventory.getStackInSlot(slot));
                GL11.glDisable(3042);
                GL11.glPopMatrix();
                break;
            }
        }
    }
    
    public static void renderItem(final ItemStack stack) {
        final TextureManager textureManager = Minecraft.getMinecraft().getTextureManager();
        if (textureManager == null) {
            return;
        }
        final Item item = stack.getItem();
        GL11.glPushMatrix();
        final Tessellator tessellator = Tessellator.getInstance();
        GL11.glEnable(32826);
        GL11.glRotatef(180.0f, 0.0f, 0.0f, 1.0f);
        GL11.glTranslatef(-0.5f, -0.5f, 0.03125f);
        if (stack.hasEffect(0)) {
            GL11.glDepthFunc(514);
            GL11.glDisable(2896);
            Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("textures/misc/enchanted_item_glint.png"));
            GL11.glEnable(3042);
            GL11.glBlendFunc(768, 1);
            GL11.glColor4f(0.38f, 0.608f, 0.608f, 1.0f);
            GL11.glMatrixMode(5890);
            GL11.glPushMatrix();
            GL11.glScalef(0.125f, 0.125f, 0.125f);
            GL11.glTranslatef(Minecraft.getSystemTime() % 3000L / 3000.0f * 8.0f, 0.0f, 0.0f);
            GL11.glRotatef(-50.0f, 0.0f, 0.0f, 1.0f);
            ItemRenderer.renderItemIn2D(tessellator, 0.0f, 0.0f, 1.0f, 1.0f, 256, 256, 0.0625f);
            GL11.glPopMatrix();
            GL11.glPushMatrix();
            GL11.glScalef(0.125f, 0.125f, 0.125f);
            GL11.glTranslatef(-Minecraft.getSystemTime() % 4873L / 4873.0f * 8.0f, 0.0f, 0.0f);
            GL11.glRotatef(10.0f, 0.0f, 0.0f, 1.0f);
            ItemRenderer.renderItemIn2D(tessellator, 0.0f, 0.0f, 1.0f, 1.0f, 256, 256, 0.0625f);
            GL11.glPopMatrix();
            GL11.glMatrixMode(5888);
            GL11.glDisable(3042);
            GL11.glEnable(2896);
            GL11.glDepthFunc(515);
        }
        GL11.glDisable(32826);
        GL11.glPopMatrix();
    }*/

}
