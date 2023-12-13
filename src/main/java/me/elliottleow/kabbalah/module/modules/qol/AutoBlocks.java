package me.elliottleow.kabbalah.module.modules.qol;

import org.lwjgl.input.Keyboard;

import me.elliottleow.kabbalah.Kabbalah;
import me.elliottleow.kabbalah.module.Category;
import me.elliottleow.kabbalah.module.Module;
import me.elliottleow.kabbalah.settings.Setting;
import net.minecraft.block.BlockChest;
import net.minecraft.block.BlockEnderChest;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class AutoBlocks extends Module {

	Minecraft mc = Minecraft.getMinecraft();
	
	public AutoBlocks() {
		super("Auto Blocks", "Automatically switches to the blocks on right click", Category.QoL);
		Kabbalah.settingsManager.rSetting(new Setting("Output", this, true));
		Kabbalah.settingsManager.rSetting(new Setting("Only Wool", this, false));
		Kabbalah.settingsManager.rSetting(new Setting("Only Clay", this, false));
		this.setKey(Keyboard.KEY_U);
	}
	
	public void equipblocks() {
		for (int slot = 0; slot < 9; slot++) {
			if (mc.thePlayer.inventory.getStackInSlot(slot)!=null) {
				if (mc.thePlayer.inventory.getStackInSlot(slot).getItem() instanceof ItemBlock && !mc.thePlayer.inventory.getStackInSlot(slot).getDisplayName().contains("chest")) {
					if (Kabbalah.settingsManager.getSettingByName(this, "Only Wool").getValBoolean() && 
					mc.thePlayer.inventory.getStackInSlot(slot).getItem().getUnlocalizedName().contains("cloth")){
						mc.thePlayer.inventory.setCurrentItem(mc.thePlayer.inventory.getStackInSlot(slot).getItem(), slot, false, true);
						if (Kabbalah.settingsManager.getSettingByName(this, "Output").getValBoolean()) {
							mc.thePlayer.addChatMessage(new ChatComponentText("Switched to " + mc.thePlayer.getHeldItem().getDisplayName() + " at slot " + (slot + 1)));
						}
						break;
					}
					if (Kabbalah.settingsManager.getSettingByName(this, "Only Clay").getValBoolean() && 
					mc.thePlayer.inventory.getStackInSlot(slot).getItem().getUnlocalizedName().contains("clay")){
						mc.thePlayer.inventory.setCurrentItem(mc.thePlayer.inventory.getStackInSlot(slot).getItem(), slot, false, true);
						if (Kabbalah.settingsManager.getSettingByName(this, "Output").getValBoolean()) {
							mc.thePlayer.addChatMessage(new ChatComponentText("Switched to " + mc.thePlayer.getHeldItem().getDisplayName() + " at slot " + (slot + 1)));
						}
						break;
					}
					else if (!Kabbalah.settingsManager.getSettingByName(this, "Only Clay").getValBoolean() && 
					!Kabbalah.settingsManager.getSettingByName(this, "Only Wool").getValBoolean()){
						mc.thePlayer.inventory.setCurrentItem(mc.thePlayer.inventory.getStackInSlot(slot).getItem(), slot, false, true);
						if (Kabbalah.settingsManager.getSettingByName(this, "Output").getValBoolean()) {
							mc.thePlayer.addChatMessage(new ChatComponentText("Switched to " + mc.thePlayer.getHeldItem().getDisplayName() + " at slot " + (slot + 1)));
						}
						break;
					}
				}
			}
		}
	}
	
	@SubscribeEvent
	public void onRightClick(PlayerInteractEvent event) throws NullPointerException {
		if (mc.thePlayer!=null && event.action.equals(PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK)) {
			if (mc.theWorld.getBlockState(event.pos).getBlock() instanceof BlockChest || mc.theWorld.getBlockState(event.pos).getBlock() instanceof BlockEnderChest) return;
			if (mc.thePlayer.getHeldItem()==null) {
				equipblocks();
			}
			else if (!(mc.thePlayer.getHeldItem().getItem() instanceof ItemBlock)) {
				equipblocks();
			}
		}
	}
}
