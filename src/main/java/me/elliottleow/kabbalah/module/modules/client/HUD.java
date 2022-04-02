package me.elliottleow.kabbalah.module.modules.client;

import org.lwjgl.input.Keyboard;

import me.elliottleow.kabbalah.module.Category;
import me.elliottleow.kabbalah.module.Module;

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
