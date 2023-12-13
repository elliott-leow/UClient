package me.elliottleow.kabbalah.module.modules.combat;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;

import me.elliottleow.kabbalah.Kabbalah;
import me.elliottleow.kabbalah.module.Category;
import me.elliottleow.kabbalah.module.Module;
import me.elliottleow.kabbalah.settings.Setting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemSword;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;

public class Autoclicker extends Module {
	
	public Autoclicker() {
		super("Auto Clicker", "Autoclicks left, right, smart, and hold", Category.Combat);
		this.setKey(Keyboard.KEY_F);
		ArrayList<String> Options = new ArrayList<String>();
		Options.add("Hold");
		Options.add("Smart");
		Options.add("LeftMouse");
		Options.add("RightMouse");
		Kabbalah.settingsManager.rSetting(new Setting("Mode", this, "Hold", Options));
		Kabbalah.settingsManager.rSetting(new Setting("Variability", this, 0, 0, 100, true));
		Kabbalah.settingsManager.rSetting(new Setting("Left Max CPS", this, 16, 15, 20, true));
		Kabbalah.settingsManager.rSetting(new Setting("Right Max CPS", this, 16, 15, 20, true));
	}
	
	public static int lcounter = 0;
	public static int rcounter = 0;
	public static int lticks = 1;
	public static int rticks = 1;
	public static int lcps = 0;
	public static int rcps = 0;
	int lmaxticks = 0;
	int rmaxticks = 0;
	int lticksskipped=0;
	int rticksskipped=0;
	
	Minecraft mc = Minecraft.getMinecraft();
	
	public void attack() {
		int lmax = (int)Kabbalah.settingsManager.getSettingByName(this, "Left Max CPS").getValDouble();
		if (Kabbalah.settingsManager.getSettingByName(this, "Variability").getValDouble()!=0) {
			double random = Math.random();
			double variability = Kabbalah.settingsManager.getSettingByName(this, "Variability").getValDouble()/200;
			if (variability > random) {
				return;
			}
		}
		if(lmax!=20 && ((lmaxticks)%(lmax/(20-lmax)) == 0) && (lticksskipped<(20-lmax))) {
			lticksskipped++;
			return;
		}
		KeyBinding.onTick(mc.gameSettings.keyBindAttack.getKeyCode());
		lcounter++;
	}
	
	public void use() {
		int rmax = (int)Kabbalah.settingsManager.getSettingByName(this, "Right Max CPS").getValDouble();
		if (Kabbalah.settingsManager.getSettingByName(this, "Variability").getValDouble()!=0) {
			double random = Math.random();
			double variability = Kabbalah.settingsManager.getSettingByName(this, "Variability").getValDouble()/200;
			if (variability > random) {
				return;
			}
		}
		if(rmax!=20 && ((rmaxticks)%(rmax/(20-rmax)) == 0) && (rticksskipped<(20-rmax))) {
			rticksskipped++;
			return;
		}
		KeyBinding.onTick(mc.gameSettings.keyBindUseItem.getKeyCode());
		rcounter++;
	}
	
    @SubscribeEvent
    public void onLeftTick(TickEvent.ClientTickEvent event) {
    	if(event.phase != Phase.END) return; 
    	if (FMLClientHandler.instance().isGUIOpen(GuiChat.class) || mc.currentScreen != null) return;
    	lmaxticks++;
    	if (mc.theWorld!=null && mc.thePlayer!=null) {
	    	if(Kabbalah.settingsManager.getSettingByName(this, "Mode").getValString().equals("LeftMouse")) attack();
	    	if(Kabbalah.settingsManager.getSettingByName(this, "Mode").getValString().equals("Smart")) {
	    		if (mc.thePlayer.getCurrentEquippedItem()!=null && mc.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemSword) attack();
	    	}
	    	if(Kabbalah.settingsManager.getSettingByName(this, "Mode").getValString().equals("Hold")) {
	    		if (Minecraft.getMinecraft().gameSettings.keyBindAttack.isKeyDown()) attack();
	    	}
    	}
    	if (lmaxticks>=21) {
    		lticksskipped=0;
    		lmaxticks=1;
    	}
    }
    
    @SubscribeEvent
    public void onRightTick(TickEvent.ClientTickEvent event) {
    	if(event.phase != Phase.END) return;
    	if (FMLClientHandler.instance().isGUIOpen(GuiChat.class) || mc.currentScreen != null) return;
    	rmaxticks++;
    	if (mc.theWorld!=null && mc.thePlayer!=null) {
	    	if(Kabbalah.settingsManager.getSettingByName(this, "Mode").getValString().equals("RightMouse")) use();
	    	if(Kabbalah.settingsManager.getSettingByName(this, "Mode").getValString().equals("Smart")) {
	    		if (mc.thePlayer.getCurrentEquippedItem()!=null && mc.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemBlock) use();
	    	}
	    	if(Kabbalah.settingsManager.getSettingByName(this, "Mode").getValString().equals("Hold")) {
	    		if (Minecraft.getMinecraft().gameSettings.keyBindUseItem.isKeyDown()) use();
	    	}
    	}
    	if (rmaxticks>=21) {
    		rticksskipped=0;
    		rmaxticks=1;
    	}
    }
    
    @Override
	public void onEnable() {
		super.onEnable();
		lcounter = 0;
		rcounter = 0;
		lticks = 1;
		rticks = 1;
		lcps = 0;
		rcps = 0;
		lmaxticks = 0;
		rmaxticks = 0;
		lticksskipped=0;
		rticksskipped=0;
    }
    
    @Override
    public void onDisable() {
    	super.onDisable();
    	lcounter = 0;
		rcounter = 0;
		lticks = 1;
		rticks = 1;
		lcps = 0;
		rcps = 0;
		lmaxticks = 0;
		rmaxticks = 0;
		lticksskipped=0;
		rticksskipped=0;
    }
    
}
