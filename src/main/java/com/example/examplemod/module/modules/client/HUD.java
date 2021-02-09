package com.example.examplemod.module.modules.client;

import org.lwjgl.input.Keyboard;

import com.example.examplemod.module.Category;
import com.example.examplemod.module.Module;

import io.github.elliottleow.kabbalah.ExampleMod;
import net.minecraft.client.Minecraft;

public class HUD extends Module {
	
	public HUD() {
		super("HUD", "Displays active modules", Category.CLIENT);
		this.setKey(Keyboard.KEY_COMMA);
	}
	
	public static boolean enabled = false;
	
	@Override
	public void onEnable() {
		super.onEnable();
		enabled = true;
	}
	
	@Override
	public void onDisable() {
		super.onDisable();
		enabled = false;
	}
}
