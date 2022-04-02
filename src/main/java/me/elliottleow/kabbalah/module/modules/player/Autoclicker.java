package me.elliottleow.kabbalah.module.modules.player;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;

import me.elliottleow.kabbalah.Kabbalah;
import me.elliottleow.kabbalah.module.Category;
import me.elliottleow.kabbalah.module.Module;
import me.elliottleow.kabbalah.settings.Setting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemSword;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;

public class Autoclicker extends Module {
	public Autoclicker() {
		super("Auto Clicker", "Autoclicks left, right, and smart (on blocks right and on sword left only)", Category.OFFENSE);
		this.setKey(Keyboard.KEY_F);
		ArrayList<String> Options = new ArrayList<String>();
		Options.add("LeftMouse");
		Options.add("RightMouse");
		Options.add("Smart");
		Kabbalah.settingsManager.rSetting(new Setting("Mode", this, "RightMouse", Options));
	}
	
    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event)
    {
    	if(event.phase != Phase.START) return;
    	
    	Minecraft mc = Minecraft.getMinecraft();
    	
    	if(Kabbalah.settingsManager.getSettingByName(this, "Mode").getValString().equals("LeftMouse")) {
    		KeyBinding.onTick(mc.gameSettings.keyBindAttack.getKeyCode());
    	}
    	
    	if(Kabbalah.settingsManager.getSettingByName(this, "Mode").getValString().equals("RightMouse")) {
    		KeyBinding.onTick(mc.gameSettings.keyBindUseItem.getKeyCode());
    	}
    	
    	if(Kabbalah.settingsManager.getSettingByName(this, "Mode").getValString().equals("Smart")) {
    		if (mc.thePlayer.getCurrentEquippedItem() != null &&mc.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemSword) {
    			KeyBinding.onTick(mc.gameSettings.keyBindAttack.getKeyCode());
    		}
    		if (mc.thePlayer.getCurrentEquippedItem() != null &&mc.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemBlock) {
    			KeyBinding.onTick(mc.gameSettings.keyBindUseItem.getKeyCode());
    		}
    	}
    }
}
