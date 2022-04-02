package me.elliottleow.kabbalah.module.modules.player;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.Project;

import me.elliottleow.kabbalah.Kabbalah;
import me.elliottleow.kabbalah.module.Category;
import me.elliottleow.kabbalah.module.Module;
import me.elliottleow.kabbalah.settings.Setting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.client.event.RenderHandEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ViewModel extends Module {
	public ViewModel() {
		super("View Model", "Changes first-person model", Category.PLAYER);
		Kabbalah.settingsManager.rSetting(new Setting("X", this, 0, -200, 200, true));
		Kabbalah.settingsManager.rSetting(new Setting("Y", this, 0, -200, 200, true));
		Kabbalah.settingsManager.rSetting(new Setting("Z", this, 0, -200, 200, true));
		Kabbalah.settingsManager.rSetting(new Setting("Only Items", this, false));
		this.setKey(Keyboard.KEY_V);
	}
	@SubscribeEvent
	public void renderHand(RenderHandEvent e) {
		boolean flag = Minecraft.getMinecraft().getRenderViewEntity() instanceof EntityLivingBase && ((EntityLivingBase)Minecraft.getMinecraft().getRenderViewEntity()).isPlayerSleeping();
		if (Kabbalah.settingsManager.getSettingByName(this, "Only Items").getValBoolean() && Minecraft.getMinecraft().thePlayer.getCurrentEquippedItem() == null) return;
		if (Minecraft.getMinecraft().gameSettings.thirdPersonView == 0 
				&& !flag
				&& !Minecraft.getMinecraft().gameSettings.hideGUI 
				&& !Minecraft.getMinecraft().playerController.isSpectator()) {
			GL11.glDepthRange(0,0.01);
	        GlStateManager.loadIdentity();
	        Project.gluPerspective(0F, (float)Minecraft.getMinecraft().displayWidth / (float)Minecraft.getMinecraft().displayHeight, 0.05F, (Minecraft.getMinecraft().gameSettings.renderDistanceChunks * 16) * 2.0F);
	        GlStateManager.matrixMode(5888);
	        GlStateManager.pushMatrix();
	        GlStateManager.translate(Kabbalah.settingsManager.getSettingByName(this, "X").getValDouble()/100, Kabbalah.settingsManager.getSettingByName(this, "Y").getValDouble()/100, Kabbalah.settingsManager.getSettingByName(this, "Z").getValDouble()/100);
	        Minecraft.getMinecraft().getItemRenderer().renderItemInFirstPerson(e.partialTicks);
	        GlStateManager.popMatrix();
	        GL11.glDepthRange(0.01,1);
			e.setCanceled(true);
        }
		
	}
	
}
