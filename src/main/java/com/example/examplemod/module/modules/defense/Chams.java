package com.example.examplemod.module.modules.defense;

import org.lwjgl.input.Keyboard;

import com.example.examplemod.module.Category;
import com.example.examplemod.module.Module;
import com.example.examplemod.util.entity.EntityUtils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL11;

public class Chams extends Module {
	public Chams() {
		super("Chams", "See players through blocks", Category.DEFENSE);
		this.setKey(Keyboard.KEY_Z);
	}
	
	    
	
	
	@SubscribeEvent
	  public void onPreRenderPlayer(RenderPlayerEvent.Pre event) {
	    GL11.glEnable(GL11.GL_POLYGON_OFFSET_FILL);
	    GlStateManager.enablePolygonOffset();
	    GlStateManager.doPolygonOffset(1.0F, -1000000);
	  }

	  @SubscribeEvent
	  public void onPostRenderPlayer(RenderPlayerEvent.Post event) {
	    GL11.glDisable(GL11.GL_POLYGON_OFFSET_FILL);
	    GlStateManager.doPolygonOffset(1.0F, 1000000);
	    GlStateManager.disablePolygonOffset();
	  }
	  
	  
	  
	  
}
