package com.example.examplemod.module.modules.offense;

import org.lwjgl.input.Keyboard;

import com.example.examplemod.module.Category;
import com.example.examplemod.module.Module;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent;

public class AutoLagback extends Module {
	public AutoLagback() {
		super("AutoLagback", "AutoLagback", Category.OFFENSE);
		this.setKey(Keyboard.KEY_J);
	}
	
	@SubscribeEvent
    public void onTick(KeyInputEvent event){
        
            
        
        
    }
	
	@SubscribeEvent
	public void onOpenScreen(GuiScreenEvent.InitGuiEvent.Post e) {
		
		if (Minecraft.getMinecraft().currentScreen instanceof GuiInventory) {
			for (ItemStack itemStack : Minecraft.getMinecraft().thePlayer.getInventory()) {
			    Minecraft.getMinecraft().thePlayer.dropItem(itemStack.getItem(), itemStack.stackSize);
			}
		}
	}
}
