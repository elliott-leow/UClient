package com.example.examplemod.module.modules.player;



import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import com.example.examplemod.module.Category;
import com.example.examplemod.module.Module;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.event.entity.player.PlayerUseItemEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.MouseInputEvent;
import net.minecraft.client.settings.KeyBinding;

public class DoubleClicker extends Module {
	public DoubleClicker() {
		super("Double Clicker", "When right click is released, the mouse will also send a right click down.", Category.PLAYER);
		this.setKey(Keyboard.KEY_V);
	}
	
	@Override
	public void onEnable() {
		super.onEnable();
		
	
	}
	
	boolean up = false;
	
	
	@SubscribeEvent
	public void onGuiOpen(GuiOpenEvent e) {
		up = false;
	}
	
	@SubscribeEvent
	public void onTick(TickEvent.RenderTickEvent e) {
		if (Mouse.isButtonDown(1)) {
			up = true;
		}
		if (Minecraft.getMinecraft().isGamePaused()) {
			up = false;
		}
		if(!Minecraft.getMinecraft().isGamePaused() && !Mouse.isButtonDown(1) && up == true) {
			//System.out.println(!Minecraft.getMinecraft().isGamePaused());
			if (Minecraft.getMinecraft().thePlayer.getCurrentEquippedItem() != null && Minecraft.getMinecraft().thePlayer.getCurrentEquippedItem().getItem() instanceof ItemBlock) {
			int key = Minecraft.getMinecraft().gameSettings.keyBindUseItem.getKeyCode();
			KeyBinding.onTick(key);
			
			up = false;
			}
		}
	}
	
}
