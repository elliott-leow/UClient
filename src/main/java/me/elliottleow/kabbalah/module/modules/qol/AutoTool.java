package me.elliottleow.kabbalah.module.modules.qol;

import org.lwjgl.input.Keyboard;

import me.elliottleow.kabbalah.Kabbalah;
import me.elliottleow.kabbalah.module.Category;
import me.elliottleow.kabbalah.module.Module;
import me.elliottleow.kabbalah.settings.Setting;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.BreakSpeed;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class AutoTool extends Module {
	
	Minecraft mc = Minecraft.getMinecraft();
	
	public AutoTool() {
		super("Auto Tool", "Automatically switches to the best tool when mining certain blocks", Category.QoL);
		Kabbalah.settingsManager.rSetting(new Setting("Output", this, true));
		Kabbalah.settingsManager.rSetting(new Setting("Sword", this, true));
		Kabbalah.settingsManager.rSetting(new Setting("Pickaxe", this, true));
		Kabbalah.settingsManager.rSetting(new Setting("Shovel", this, true));
		Kabbalah.settingsManager.rSetting(new Setting("Axe", this, true));
		Kabbalah.settingsManager.rSetting(new Setting("Shears", this, true));
		this.setKey(Keyboard.KEY_U);
	}
	
	public void equiptool(String toolname) {
		for (int slot = 0; slot < 9; slot++) {
			if (mc.thePlayer.inventory.getStackInSlot(slot)!=null) {
				if (mc.thePlayer.inventory.getStackInSlot(slot).getItem().toString().contains(toolname)) {
					mc.thePlayer.inventory.setCurrentItem(mc.thePlayer.inventory.getStackInSlot(slot).getItem(), slot, false, true);
					if (Kabbalah.settingsManager.getSettingByName(this, "Output").getValBoolean()) {
						mc.thePlayer.addChatMessage(new ChatComponentText("Switched to " + toolname + " at slot " + (slot + 1)));
					}
					break;
				}
			}
		}
	}
	
	@SubscribeEvent
	public void onAttack(AttackEntityEvent event) {
		if (mc.thePlayer!=null && mc.theWorld!=null) {
			if (Kabbalah.settingsManager.getSettingByName(this, "Sword").getValBoolean()) {
				if (mc.thePlayer.getHeldItem()==null) {
					//if (event.isCancelable()) {
					//	event.setCanceled(true);
					//}
					equiptool("ItemSword");
					//KeyBinding.onTick(Minecraft.getMinecraft().gameSettings.keyBindAttack.getKeyCode());
				}
				else if (!mc.thePlayer.getHeldItem().getDisplayName().toString().contains("Sword")) {
					//if (event.isCancelable()) {
					//	event.setCanceled(true);
					//}
					equiptool("ItemSword");
					//KeyBinding.onTick(Minecraft.getMinecraft().gameSettings.keyBindAttack.getKeyCode());
				}
			}
		}
	}
	
	@SubscribeEvent
	public void onBreaking(BreakSpeed event) {
		if (mc.thePlayer!=null && mc.theWorld!=null) {
			if (Kabbalah.settingsManager.getSettingByName(this, "Pickaxe").getValBoolean() && 
					mc.theWorld.getBlockState(event.pos).getBlock()==Blocks.end_stone || 
					mc.theWorld.getBlockState(event.pos).getBlock()==Blocks.hardened_clay || 
					mc.theWorld.getBlockState(event.pos).getBlock()==Blocks.stained_hardened_clay || 
					mc.theWorld.getBlockState(event.pos).getBlock()==Blocks.obsidian || 
					mc.theWorld.getBlockState(event.pos).getBlock()==Blocks.cobblestone || 
					mc.theWorld.getBlockState(event.pos).getBlock()==Blocks.stone || 
					mc.theWorld.getBlockState(event.pos).getBlock()==Blocks.sandstone) {
				if (mc.thePlayer.getHeldItem()==null) {
					equiptool("ItemPickaxe");
				}
				else if (!mc.thePlayer.getHeldItem().getDisplayName().toString().contains("Pickaxe")) {
					equiptool("ItemPickaxe");
				}
			}
			if (Kabbalah.settingsManager.getSettingByName(this, "Shears").getValBoolean() && 
					mc.theWorld.getBlockState(event.pos).getBlock()==Blocks.wool) {
				if (mc.thePlayer.getHeldItem()==null) {
					equiptool("Shears");
				}
				else if (!mc.thePlayer.getHeldItem().getDisplayName().toString().contains("Shears")) {
					equiptool("Shears");
				}
			}
			if (Kabbalah.settingsManager.getSettingByName(this, "Axe").getValBoolean() && 
					mc.theWorld.getBlockState(event.pos).getBlock()==Blocks.planks || 
					mc.theWorld.getBlockState(event.pos).getBlock()==Blocks.log || 
					mc.theWorld.getBlockState(event.pos).getBlock()==Blocks.log2 || 
					mc.theWorld.getBlockState(event.pos).getBlock()==Blocks.ladder) {
				if (mc.thePlayer.getHeldItem()==null) {
					equiptool("ItemAxe");
				}
				else if (!mc.thePlayer.getHeldItem().getDisplayName().toString().contains("Axe")) {
					equiptool("ItemAxe");
				}
			}
			if (Kabbalah.settingsManager.getSettingByName(this, "Shovel").getValBoolean() && 
					mc.theWorld.getBlockState(event.pos).getBlock()==Blocks.dirt || 
					mc.theWorld.getBlockState(event.pos).getBlock()==Blocks.grass || 
					mc.theWorld.getBlockState(event.pos).getBlock()==Blocks.sand || 
					mc.theWorld.getBlockState(event.pos).getBlock()==Blocks.gravel) {
				if (mc.thePlayer.getHeldItem()==null) {
					equiptool("ItemSpade");
				}
				else if (!mc.thePlayer.getHeldItem().getDisplayName().toString().contains("Shovel")) {
					equiptool("ItemSpade");
				}
			}
		}
	}
}