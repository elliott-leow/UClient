package me.elliottleow.kabbalah.module.modules.offense;

import org.lwjgl.input.Keyboard;

import me.elliottleow.kabbalah.Kabbalah;
import me.elliottleow.kabbalah.module.Category;
import me.elliottleow.kabbalah.module.Module;
import me.elliottleow.kabbalah.settings.Setting;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class AutoBlocks extends Module {

	protected static final Minecraft mc = Minecraft.getMinecraft();
	
	public AutoBlocks() {
		super("Auto Blocks", "Automatically switches to the blocks on right click", Category.OFFENSE);
		Kabbalah.settingsManager.rSetting(new Setting("Output", this, true));
		this.setKey(Keyboard.KEY_U);
	}
	
	public void equipblocks() {
		for (int slot = 0; slot < 9; slot++) {
			if (mc.thePlayer.inventory.getStackInSlot(slot)==null) {
			}
			else if (mc.thePlayer.inventory.getStackInSlot(slot).getItem() instanceof ItemBlock) {
				mc.thePlayer.inventory.setCurrentItem(mc.thePlayer.inventory.getStackInSlot(slot).getItem(), slot, false, true);
				int itemslot = slot +1;
				System.out.println("Switched to " + mc.thePlayer.getHeldItem().getDisplayName() + " at slot " + itemslot);
				if (Kabbalah.settingsManager.getSettingByName(this, "Output").getValBoolean()) {
					mc.thePlayer.addChatMessage(new ChatComponentText("Switched to " + mc.thePlayer.getHeldItem().getDisplayName() + " at slot " + itemslot));
				}
				break;
			}
		}
	}
	
	@SubscribeEvent
	public void onRightClick(PlayerInteractEvent event) throws NullPointerException {
		if (event.entityPlayer.getUniqueID().equals(mc.getSession().getProfile().getId()) && event.action.equals(PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK)) {
			equipblocks();
		}
	}
}
