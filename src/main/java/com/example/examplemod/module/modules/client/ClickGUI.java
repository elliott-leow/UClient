package com.example.examplemod.module.modules.client;

import org.lwjgl.input.Keyboard;

import com.example.examplemod.module.Category;
import com.example.examplemod.module.Module;

import io.github.elliottleow.kabbalah.ExampleMod;
import net.minecraft.client.Minecraft;

public class ClickGUI extends Module {
	
	public ClickGUI() {
		super("ClickGUI", "Allows you to enable and disable modules", Category.CLIENT);
		this.setKey(Keyboard.KEY_RSHIFT);
	}
	
	@Override
	public void onEnable() {
		super.onEnable();
		Minecraft.getMinecraft().displayGuiScreen(ExampleMod.clickGui);
		this.setToggled(false);
	}
}
