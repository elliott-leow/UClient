package me.elliottleow.kabbalah.module;

import me.elliottleow.kabbalah.Kabbalah;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.common.MinecraftForge;

public class Module {
	public String name, description;
	private int key;
	private Category category;
	public boolean toggled;
	//public boolean visible = true;
	
	public Module(String name, String description, Category category) {
		super();
		this.name = name;
		this.description = description;
		this.key = 0;
		this.category = category;
		this.toggled = false;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public int getKey() {
		return key;
	}
	
	public void setKey(int key) {
		this.key = key;
		if (Kabbalah.saveLoad != null) {
			Kabbalah.saveLoad.save();
		}
	}
	
	public boolean isToggled() {
		return toggled;
	}
	
	public void setToggled(boolean toggled) {
		
		this.toggled = toggled;
		
		if(this.toggled) {
			this.onEnable();
		} else {
			this.onDisable();
		}
		if (Kabbalah.saveLoad != null) {
			Kabbalah.saveLoad.save();
		}
	}
	
	public void toggle() {
		this.toggled = !this.toggled;
		if (Kabbalah.settingsManager.getSettingByName(Kabbalah.moduleManager.getModule("Click GUI"), "Output").getValBoolean() && this.getName()!="Click GUI") Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.DARK_AQUA + "[Kabbalah Client]: " + this.getName() + " toggled"));
		if(this.toggled) {
			this.onEnable();
		} else {
			this.onDisable();
		}
		if (Kabbalah.saveLoad != null) {
			Kabbalah.saveLoad.save();
		}
	}
	
	public void onEnable() {
		MinecraftForge.EVENT_BUS.register(this);
	}
	
	public void onDisable() {
		MinecraftForge.EVENT_BUS.unregister(this);
	}
	
	public String getName()	{
		return this.name;
	}
	
	public Category getCategory() {
		return this.category;
	}
	
}
