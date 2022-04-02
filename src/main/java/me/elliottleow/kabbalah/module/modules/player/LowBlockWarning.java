package me.elliottleow.kabbalah.module.modules.player;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import me.elliottleow.kabbalah.Kabbalah;
import me.elliottleow.kabbalah.module.Category;
import me.elliottleow.kabbalah.module.Module;
import me.elliottleow.kabbalah.settings.Setting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class LowBlockWarning extends Module {
	
	
	public LowBlockWarning() {
		super("Low Block Warning", "Tells you when you are low on blocks", Category.PLAYER);
		this.setKey(Keyboard.KEY_L);
		Kabbalah.settingsManager.rSetting(new Setting("Width", this, 50, 1, 100, true));
		Kabbalah.settingsManager.rSetting(new Setting("Height", this, 60, 1, 100, true));
		Kabbalah.settingsManager.rSetting(new Setting("Only Clay", this, true));
		Kabbalah.settingsManager.rSetting(new Setting("Min Blocks", this, 16, 1, 64, true));
		
		
	}
	boolean toggled = false;
	
	@Override
	public void onEnable() {
		super.onEnable();
		toggled = true;
	
	}
	
	@Override
	public void onDisable() {
		super.onDisable();
		toggled = false;
	}
	
	@SubscribeEvent
	public void renderOverlay(RenderGameOverlayEvent.Text event) {
		
	
	if(event.type == RenderGameOverlayEvent.ElementType.TEXT && Minecraft.getMinecraft().thePlayer.getCurrentEquippedItem() != null && Minecraft.getMinecraft().thePlayer.getCurrentEquippedItem().getItem() instanceof ItemBlock && Minecraft.getMinecraft().thePlayer.getCurrentEquippedItem().stackSize < (int)Kabbalah.settingsManager.getSettingByName(this, "Min Blocks").getValDouble()) {
		
		ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
		FontRenderer fr = Minecraft.getMinecraft().fontRendererObj;
		if (Kabbalah.settingsManager.getSettingByName(this, "Only Clay").getValBoolean() && Minecraft.getMinecraft().thePlayer.getCurrentEquippedItem().getItem() == Item.getItemFromBlock(Blocks.stained_hardened_clay)) {
			GL11.glPushMatrix();
			fr.drawStringWithShadow("You are almost out of blocks", (int) (sr.getScaledWidth()*(Kabbalah.settingsManager.getSettingByName(this, "Width").getValDouble()/100) - 70), (int) (sr.getScaledHeight()* (Kabbalah.settingsManager.getSettingByName(this, "Height").getValDouble()/100)), 0xff82F6FF);
			GL11.glPopMatrix();
		}
		if (Kabbalah.settingsManager.getSettingByName(this, "Only Clay").getValBoolean() == false) {
			GL11.glPushMatrix();
			fr.drawStringWithShadow("You are almost out of blocks", (int) (sr.getScaledWidth()*(Kabbalah.settingsManager.getSettingByName(this, "Width").getValDouble()/100) - 70), (int) (sr.getScaledHeight()* (Kabbalah.settingsManager.getSettingByName(this, "Height").getValDouble()/100)), 0xff82F6FF);
			//fr.drawStringWithShadow("", 0, 0, 0x00000000);
			//fr.drawStringWithShadow("", 0, 0, 0xffffffff);
			GL11.glPopMatrix();
	}
	
		
		}
}

}
