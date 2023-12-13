package me.elliottleow.kabbalah.module.modules.qol;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import me.elliottleow.kabbalah.module.Category;
import me.elliottleow.kabbalah.module.Module;
import me.elliottleow.kabbalah.module.modules.combat.Autoclicker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class DoubleClicker extends Module {
	public DoubleClicker() {
		super("Double Clicker", "When right click is released, the mouse will also send a right click down.", Category.QoL);
		this.setKey(Keyboard.KEY_F);
	}
	
	boolean upRightMouse = false;
	
	@Override
	public void onEnable() {
		super.onEnable();
		upRightMouse=false;
	}
	
	@SubscribeEvent
	public void onGuiOpen(GuiOpenEvent e) {
		upRightMouse = false;
	}
	
	@SubscribeEvent
	public void onTick(TickEvent.ClientTickEvent e) throws NullPointerException {
		if (Minecraft.getMinecraft() == null || (Minecraft.getMinecraft().currentScreen instanceof GuiMainMenu) || Minecraft.getMinecraft().thePlayer == null || Minecraft.getMinecraft().isGamePaused()) {
			upRightMouse = false;
			return;
		}
		if (Mouse.isButtonDown(1)) {
			upRightMouse = true;
		}
		if(!Mouse.isButtonDown(1) && upRightMouse == true) {
			if (Minecraft.getMinecraft().thePlayer.getCurrentEquippedItem() != null && Minecraft.getMinecraft().thePlayer.getCurrentEquippedItem().getItem() instanceof ItemBlock) {
				KeyBinding.onTick(Minecraft.getMinecraft().gameSettings.keyBindUseItem.getKeyCode());
				Autoclicker.rcounter++;
				upRightMouse = false;
			}
		}
	}
}
