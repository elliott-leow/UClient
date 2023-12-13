package me.elliottleow.kabbalah.module.modules.visual;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import me.elliottleow.kabbalah.Kabbalah;
import me.elliottleow.kabbalah.module.Category;
import me.elliottleow.kabbalah.module.Module;
import me.elliottleow.kabbalah.settings.Setting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class Chams extends Module {
	public Chams() {
		super("Chams", "See players through blocks", Category.Visual);
		Kabbalah.settingsManager.rSetting(new Setting("Red", this, 100, 0, 100, true));
		Kabbalah.settingsManager.rSetting(new Setting("Green", this, 100, 0, 100, true));
		Kabbalah.settingsManager.rSetting(new Setting("Blue", this, 100, 0, 100, true));
		Kabbalah.settingsManager.rSetting(new Setting("Transparency", this, 50, 0, 100, true));
		Kabbalah.settingsManager.rSetting(new Setting("Lighting", this, true));
		Kabbalah.settingsManager.rSetting(new Setting("Experimental", this, false));
		Kabbalah.settingsManager.rSetting(new Setting("Work on self", this, false));
		this.setKey(Keyboard.KEY_Z);
	}
		
	@SubscribeEvent
	public void onPreRenderPlayer(RenderPlayerEvent.Pre event) {
		Entity e = (Entity)event.entity;
		if (e instanceof EntityOtherPlayerMP || e instanceof EntityPlayerMP || e instanceof EntityPlayerSP) {
			if (e.getUniqueID()==Minecraft.getMinecraft().thePlayer.getUniqueID() && !Kabbalah.settingsManager.getSettingByName(this, "Work on self").getValBoolean()) return;
	        if (!Kabbalah.settingsManager.getSettingByName(this, "Lighting").getValBoolean()) RenderHelper.disableStandardItemLighting();
			float colorRed = (float)(Kabbalah.settingsManager.getSettingByName(this, "Red").getValDouble()/100);
			float colorGreen = (float)(Kabbalah.settingsManager.getSettingByName(this, "Green").getValDouble()/100);
			float colorBlue = (float)(Kabbalah.settingsManager.getSettingByName(this, "Blue").getValDouble()/100);
			float transparency = (float)(Kabbalah.settingsManager.getSettingByName(this, "Transparency").getValDouble()/100);
			GL11.glEnable(GL11.GL_BLEND);
	        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			if (Kabbalah.settingsManager.getSettingByName(this, "Experimental").getValBoolean()) {
				GL11.glDisable(GL11.GL_DEPTH_TEST);
				//GL11.glClear(GL11.GL_DEPTH_BUFFER_BIT);
				//GL11.glDisable(GL11.GL_DEPTH_WRITEMASK);
			}
			GlStateManager.color(colorRed*2.55f, colorGreen*2.55f, colorBlue*2.55f, transparency);
	        GL11.glEnable(GL11.GL_POLYGON_OFFSET_FILL);
	        GlStateManager.enablePolygonOffset();
	        GlStateManager.doPolygonOffset(1.0F, -1000000);
		    }
	}

	  @SubscribeEvent
	  public void onPostRenderPlayer(RenderPlayerEvent.Post event) {
		  Entity e = (Entity)event.entity;
			if (e instanceof EntityOtherPlayerMP || e instanceof EntityPlayerMP || e instanceof EntityPlayerSP) {
				if (e.getUniqueID()==Minecraft.getMinecraft().thePlayer.getUniqueID() && !Kabbalah.settingsManager.getSettingByName(this, "Work on self").getValBoolean()) return;
				GL11.glDisable(GL11.GL_BLEND);
				if (Kabbalah.settingsManager.getSettingByName(this, "Experimental").getValBoolean()) {
					GL11.glEnable(GL11.GL_DEPTH_TEST);
				}
				GL11.glDisable(GL11.GL_POLYGON_OFFSET_FILL);
				GlStateManager.doPolygonOffset(1.0F, 1000000);
				GlStateManager.disablePolygonOffset();
		        if (!Kabbalah.settingsManager.getSettingByName(this, "Lighting").getValBoolean()) RenderHelper.enableStandardItemLighting();
			}
	  } 

}
