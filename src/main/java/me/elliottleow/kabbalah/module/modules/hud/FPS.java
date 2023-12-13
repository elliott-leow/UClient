package me.elliottleow.kabbalah.module.modules.hud;

import me.elliottleow.kabbalah.Kabbalah;
import me.elliottleow.kabbalah.module.Category;
import me.elliottleow.kabbalah.module.Module;
import me.elliottleow.kabbalah.settings.Setting;
import net.minecraft.client.Minecraft;

public class FPS extends Module {

	public FPS() {
		super("FPS", "Displays frames per second", Category.HUD);
		Kabbalah.settingsManager.rSetting(new Setting("Spoof FPS", this, 0, 0, 10000, true));
		Kabbalah.settingsManager.rSetting(new Setting("Spoof FPS Multiplier", this, 1, 1, 100, true));
	}
	
	Minecraft mc = Minecraft.getMinecraft();
	
	public static int getFrames() {
		return (int) (Kabbalah.settingsManager.getSettingByName(Kabbalah.moduleManager.getModule("FPS"), "Spoof FPS Multiplier").getValDouble()*(Minecraft.getDebugFPS() + Kabbalah.settingsManager.getSettingByName(Kabbalah.moduleManager.getModule("FPS"), "Spoof FPS").getValDouble()));
	}
	
}
