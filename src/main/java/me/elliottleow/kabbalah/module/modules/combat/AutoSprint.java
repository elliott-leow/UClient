package me.elliottleow.kabbalah.module.modules.combat;

import org.lwjgl.input.Keyboard;

import me.elliottleow.kabbalah.Kabbalah;
import me.elliottleow.kabbalah.module.Category;
import me.elliottleow.kabbalah.module.Module;
import me.elliottleow.kabbalah.settings.Setting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;

public class AutoSprint extends Module {

	public AutoSprint() {
		super("Auto Sprint", "Keeps you sprinting", Category.Combat);
		Kabbalah.settingsManager.rSetting(new Setting("Auto Run", this, false));
		Kabbalah.settingsManager.rSetting(new Setting("S Tap", this, false));
		Kabbalah.settingsManager.rSetting(new Setting("Tap Severity", this, 1, 1, 10, true));
		this.setKey(Keyboard.KEY_I);
	}

	public static Minecraft mc = Minecraft.getMinecraft();
	int ticks=0;
	
	@SubscribeEvent
	public void onTick(TickEvent.PlayerTickEvent event) {
		if (event.phase != Phase.START) return;
		if (mc.theWorld!=null && mc.thePlayer!=null) {
			if (FMLClientHandler.instance().isGUIOpen(GuiChat.class) || mc.currentScreen != null) {
				KeyBinding.setKeyBindState(mc.gameSettings.keyBindForward.getKeyCode(), false);
				KeyBinding.setKeyBindState(mc.gameSettings.keyBindSprint.getKeyCode(), false);
				return;
			}
			else {
				if (Kabbalah.settingsManager.getSettingByName(this, "Auto Run").getValBoolean() && !Keyboard.isKeyDown(mc.gameSettings.keyBindBack.getKeyCode())) {
					KeyBinding.setKeyBindState(mc.gameSettings.keyBindForward.getKeyCode(), true);
				}
			}
			if (Keyboard.isKeyDown(mc.gameSettings.keyBindBack.getKeyCode())) {
				KeyBinding.setKeyBindState(mc.gameSettings.keyBindForward.getKeyCode(), false);
			}
			float severity = (float) Kabbalah.settingsManager.getSettingByName(this, "Tap Severity").getValDouble();
			KeyBinding.setKeyBindState(mc.gameSettings.keyBindSprint.getKeyCode(), true);
			if (Kabbalah.settingsManager.getSettingByName(this, "S Tap").getValBoolean()) {
				if(ticks>=severity) {
					if (Keyboard.isKeyDown(mc.gameSettings.keyBindForward.getKeyCode())) {
						KeyBinding.setKeyBindState(mc.gameSettings.keyBindBack.getKeyCode(), false);
						KeyBinding.setKeyBindState(mc.gameSettings.keyBindSprint.getKeyCode(), true);
						KeyBinding.setKeyBindState(mc.gameSettings.keyBindForward.getKeyCode(), true);
					}
				}
				ticks+=1;
			}
		}
	}
	
	@SubscribeEvent
	public void onAttack(AttackEntityEvent event) {
		KeyBinding.setKeyBindState(mc.gameSettings.keyBindSprint.getKeyCode(), true);
		if (Kabbalah.settingsManager.getSettingByName(this, "S Tap").getValBoolean() && Keyboard.isKeyDown(mc.gameSettings.keyBindForward.getKeyCode())) {
			ticks=0;
			KeyBinding.setKeyBindState(mc.gameSettings.keyBindForward.getKeyCode(), false);
			KeyBinding.setKeyBindState(mc.gameSettings.keyBindSprint.getKeyCode(), false);
			KeyBinding.setKeyBindState(mc.gameSettings.keyBindBack.getKeyCode(), true);
		}
	}
	
}
