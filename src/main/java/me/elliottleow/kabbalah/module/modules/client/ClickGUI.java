package me.elliottleow.kabbalah.module.modules.client;

import org.lwjgl.input.Keyboard;

import me.elliottleow.kabbalah.Kabbalah;
import me.elliottleow.kabbalah.module.Category;
import me.elliottleow.kabbalah.module.Module;
import net.minecraft.client.Minecraft;

public class ClickGUI extends Module {
	
	public ClickGUI() {
		super("Click GUI", "Allows you to enable and disable modules", Category.CLIENT);
		this.setKey(Keyboard.KEY_RSHIFT);
	}
	
	@Override
	public void onEnable() {
		super.onEnable();
		Minecraft.getMinecraft().displayGuiScreen(Kabbalah.clickGui);
		this.setToggled(false);
	}
}
