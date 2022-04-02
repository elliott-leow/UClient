package me.elliottleow.kabbalah.module.modules.defense;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import me.elliottleow.kabbalah.module.Category;
import me.elliottleow.kabbalah.module.Module;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

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
