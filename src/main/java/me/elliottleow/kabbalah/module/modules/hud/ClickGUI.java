package me.elliottleow.kabbalah.module.modules.hud;

import org.lwjgl.input.Keyboard;

import me.elliottleow.kabbalah.Kabbalah;
import me.elliottleow.kabbalah.module.Category;
import me.elliottleow.kabbalah.module.Module;
import me.elliottleow.kabbalah.settings.Setting;
import net.minecraft.client.Minecraft;

public class ClickGUI extends Module {
	
	public ClickGUI() {
		super("Click GUI", "Allows you to enable and disable modules", Category.HUD);
		Kabbalah.settingsManager.rSetting(new Setting("Output", this, true));
		this.setKey(Keyboard.KEY_RSHIFT);
	}
	
	@Override
	public void onEnable() {
		super.onEnable();
		Minecraft.getMinecraft().displayGuiScreen(Kabbalah.clickGui);
		this.setToggled(false);
	}
}
