package com.example.examplemod.module.modules.player;

import com.example.examplemod.module.Category;
import com.example.examplemod.module.Module;

import io.github.elliottleow.kabbalah.Globals;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class Sprint extends Module {
	
	public Sprint() {
		super("Sprint", "Holds down sprint key automatically", Category.PLAYER);
		
		this.setKey(Keyboard.KEY_K);
	}
	
	@SubscribeEvent
	public void onPlayerTick(TickEvent.PlayerTickEvent e) {
			KeyBinding.setKeyBindState(Minecraft.getMinecraft().gameSettings.keyBindSprint.getKeyCode(), true);

	}
	
	@Override 
	public void onDisable() {
		super.onDisable();
		KeyBinding.setKeyBindState(Minecraft.getMinecraft().gameSettings.keyBindSprint.getKeyCode(), false);
	}
	
}
