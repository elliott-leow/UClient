package me.elliottleow.kabbalah.module.modules.offense;


import org.lwjgl.input.Keyboard;

import me.elliottleow.kabbalah.Kabbalah;
import me.elliottleow.kabbalah.module.Category;
import me.elliottleow.kabbalah.module.Module;
import me.elliottleow.kabbalah.settings.Setting;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.event.entity.player.PlayerEvent.BreakSpeed;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class AutoTool extends Module {
	public AutoTool() {
		super("Auto Tool", "Automatically switches to the best tool when mining certain blocks", Category.OFFENSE);
		Kabbalah.settingsManager.rSetting(new Setting("Output", this, true));
		this.setKey(Keyboard.KEY_U);
	}
	
	
	protected static Minecraft mc = Minecraft.getMinecraft();
	
	public void equiptool(String toolname) {
		ItemStack item = mc.thePlayer.getHeldItem();
		if (item!=null) {
			if (item.getDisplayName().toString().contains(toolname)){
				return;
			}
			else {
				for (int slot = 0; slot < 9; slot++) {
					if (mc.thePlayer.inventory.getStackInSlot(slot)!=null) {
						if (mc.thePlayer.inventory.getStackInSlot(slot).getItem().toString().contains(toolname)) {
							mc.thePlayer.inventory.setCurrentItem(mc.thePlayer.inventory.getStackInSlot(slot).getItem(), slot, false, true);
							int itemslot = slot +1;
							System.out.println("Switched to " + toolname + " at slot " + itemslot);
							if (Kabbalah.settingsManager.getSettingByName(this, "Output").getValBoolean()) {
								mc.thePlayer.addChatMessage(new ChatComponentText("Switched to " + toolname + " at slot " + itemslot));
							}
							break;
						}
						else {
							return;
						}
					}
				}
			}
		}
		else {
			for (int slot = 0; slot < 9; slot++) {
				if (mc.thePlayer.inventory.getStackInSlot(slot)!=null) {
					if (mc.thePlayer.inventory.getStackInSlot(slot).getItem().toString().contains(toolname)) {
						mc.thePlayer.inventory.setCurrentItem(mc.thePlayer.inventory.getStackInSlot(slot).getItem(), slot, false, true);
						int itemslot = slot +1;
						System.out.println("Switched to " + toolname + " at slot " + itemslot);
						mc.thePlayer.addChatMessage(new ChatComponentText("Switched to " + toolname + " at slot " + itemslot));
						break;
					}
					else {
						return;
					}
				}
			}
		}
	}
	
	@SubscribeEvent
	public void onBreaking(BreakSpeed event) {
		if (event.entityPlayer.getUniqueID().equals(mc.getSession().getProfile().getId())) {
			if (mc.theWorld.getBlockState(event.pos).getBlock()==Blocks.end_stone || mc.theWorld.getBlockState(event.pos).getBlock()==Blocks.hardened_clay || mc.theWorld.getBlockState(event.pos).getBlock()==Blocks.stained_hardened_clay || mc.theWorld.getBlockState(event.pos).getBlock()==Blocks.obsidian || mc.theWorld.getBlockState(event.pos).getBlock()==Blocks.cobblestone || mc.theWorld.getBlockState(event.pos).getBlock()==Blocks.stone || mc.theWorld.getBlockState(event.pos).getBlock()==Blocks.sandstone) {
				equiptool("Pickaxe");
			}
			else if (mc.theWorld.getBlockState(event.pos).getBlock()==Blocks.wool) {
				equiptool("Shears");
			}
			else if (mc.theWorld.getBlockState(event.pos).getBlock()==Blocks.planks || mc.theWorld.getBlockState(event.pos).getBlock()==Blocks.log || mc.theWorld.getBlockState(event.pos).getBlock()==Blocks.log2 || mc.theWorld.getBlockState(event.pos).getBlock()==Blocks.ladder) {
				equiptool("Axe");
			}
			else if (mc.theWorld.getBlockState(event.pos).getBlock()==Blocks.dirt || mc.theWorld.getBlockState(event.pos).getBlock()==Blocks.grass || mc.theWorld.getBlockState(event.pos).getBlock()==Blocks.sand || mc.theWorld.getBlockState(event.pos).getBlock()==Blocks.gravel) {
				equiptool("Shovel");
			}
			else {
			}
		}
	}
}