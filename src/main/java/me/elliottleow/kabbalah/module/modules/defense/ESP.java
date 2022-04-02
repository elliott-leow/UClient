package me.elliottleow.kabbalah.module.modules.defense;

import org.lwjgl.input.Keyboard;

import me.elliottleow.kabbalah.Kabbalah;
import me.elliottleow.kabbalah.api.util.RenderUtils;
import me.elliottleow.kabbalah.module.Category;
import me.elliottleow.kabbalah.module.Module;
import me.elliottleow.kabbalah.settings.Setting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ESP extends Module {
	public ESP() {
		super("ESP", "Outlines entities with a box", Category.DEFENSE);
		this.setKey(Keyboard.KEY_Z);
		Kabbalah.settingsManager.rSetting(new Setting("Mobs", this, false));
		Kabbalah.settingsManager.rSetting(new Setting("Players", this, true));
		Kabbalah.settingsManager.rSetting(new Setting("Items", this, false));
		Kabbalah.settingsManager.rSetting(new Setting("Red", this, 100, 0, 100, true));
		Kabbalah.settingsManager.rSetting(new Setting("Green", this, 0, 0, 100, true));
		Kabbalah.settingsManager.rSetting(new Setting("Blue", this, 0, 0, 100, true));
		Kabbalah.settingsManager.rSetting(new Setting("Line Width", this, 5, 1, 40, true));
	}
	
	protected static final Minecraft mc = Minecraft.getMinecraft();
	
	@SubscribeEvent
	public void onRender(final RenderWorldLastEvent event) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
		for(Object entity: Minecraft.getMinecraft().theWorld.loadedEntityList) {
			 Entity e = (Entity)entity;
			 if ((e instanceof EntityOtherPlayerMP || e instanceof EntityPlayerMP || e instanceof EntityPlayerSP) && e.getUniqueID()!=mc.thePlayer.getUniqueID() && Kabbalah.settingsManager.getSettingByName(this, "Players").getValBoolean()) {
				 RenderUtils.entityESPBox((Entity) e, 
				 (Kabbalah.settingsManager.getSettingByName(this, "Red").getValDouble()/100), 
				 (Kabbalah.settingsManager.getSettingByName(this, "Green").getValDouble()/100), 
				 (Kabbalah.settingsManager.getSettingByName(this, "Blue").getValDouble()/100),
				 (Kabbalah.settingsManager.getSettingByName(this, "Line Width").getValDouble()/10));
			 }
			 
			 if (e instanceof EntityMob && Kabbalah.settingsManager.getSettingByName(this, "Mobs").getValBoolean()) {
				 RenderUtils.entityESPBox((Entity) e, 
				 (Kabbalah.settingsManager.getSettingByName(this, "Red").getValDouble()/100), 
				 (Kabbalah.settingsManager.getSettingByName(this, "Green").getValDouble()/100), 
				 (Kabbalah.settingsManager.getSettingByName(this, "Blue").getValDouble()/100),
				 (Kabbalah.settingsManager.getSettingByName(this, "Line Width").getValDouble()/10));
			 }
			 
			 if (e instanceof EntityItem && Kabbalah.settingsManager.getSettingByName(this, "Items").getValBoolean()) {
				 RenderUtils.entityESPBox((Entity) e, 
				 (Kabbalah.settingsManager.getSettingByName(this, "Red").getValDouble()/100), 
				 (Kabbalah.settingsManager.getSettingByName(this, "Green").getValDouble()/100), 
				 (Kabbalah.settingsManager.getSettingByName(this, "Blue").getValDouble()/100),
				 (Kabbalah.settingsManager.getSettingByName(this, "Line Width").getValDouble()/10));
			 }
		 }
	 }
}
