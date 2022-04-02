package me.elliottleow.kabbalah.module.modules.player;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import me.elliottleow.kabbalah.Kabbalah;
import me.elliottleow.kabbalah.module.Category;
import me.elliottleow.kabbalah.module.Module;
import me.elliottleow.kabbalah.settings.Setting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class DoubleClicker extends Module {
	public DoubleClicker() {
		super("Double Clicker", "When right click is released, the mouse will also send a right click down.", Category.OFFENSE);
		this.setKey(Keyboard.KEY_F);
		Kabbalah.settingsManager.rSetting(new Setting("RightMouse", this, true));
		Kabbalah.settingsManager.rSetting(new Setting("LeftMouse", this, false));
	}
	
	@Override
	public void onEnable() {
		super.onEnable();
		
	
	}
	
	boolean upRightMouse = false;
	boolean upLeftMouse = false;
	
	
	@SubscribeEvent
	public void onGuiOpen(GuiOpenEvent e) {
		upRightMouse = false;
		upLeftMouse = false;
	}
	
	@SubscribeEvent
	public void onTick(TickEvent.ClientTickEvent e) throws NullPointerException {
		if (Minecraft.getMinecraft() == null || (Minecraft.getMinecraft().currentScreen instanceof GuiMainMenu) || Minecraft.getMinecraft().thePlayer == null) return;
		if (Mouse.isButtonDown(1)) {
			upRightMouse = true;
			
		}
		if (Mouse.isButtonDown(0)) {
			upLeftMouse = true;
			
		}
		if (Minecraft.getMinecraft().isGamePaused()) {
			upRightMouse = false;
			upLeftMouse = false;
		}
		if(!Minecraft.getMinecraft().isGamePaused() && !Mouse.isButtonDown(1) && upRightMouse == true && Kabbalah.settingsManager.getSettingByName(this, "RightMouse").getValBoolean()) {
			
			if (Minecraft.getMinecraft().thePlayer.getCurrentEquippedItem() != null && Minecraft.getMinecraft().thePlayer.getCurrentEquippedItem().getItem() instanceof ItemBlock) {
			int key = Minecraft.getMinecraft().gameSettings.keyBindUseItem.getKeyCode();
			
			KeyBinding.onTick(key);
			
			upRightMouse = false;
			}
		}
		if(!Minecraft.getMinecraft().isGamePaused() && !Mouse.isButtonDown(0) && upLeftMouse == true && Kabbalah.settingsManager.getSettingByName(this, "LeftMouse").getValBoolean()) {
			
			if (Minecraft.getMinecraft().thePlayer.getCurrentEquippedItem() != null) {
				// && Minecraft.getMinecraft().thePlayer.getCurrentEquippedItem().getItem() instanceof ItemSword
			int key = Minecraft.getMinecraft().gameSettings.keyBindAttack.getKeyCode();
			
			KeyBinding.onTick(key);
			
			upLeftMouse = false;
			}
		}
	}
}
