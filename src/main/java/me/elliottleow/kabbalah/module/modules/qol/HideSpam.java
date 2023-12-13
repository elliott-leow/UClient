package me.elliottleow.kabbalah.module.modules.qol;

import me.elliottleow.kabbalah.module.Category;
import me.elliottleow.kabbalah.module.Module;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class HideSpam extends Module {

	public HideSpam() {
		super("Hide Spam", "Removes annoying lobby messages", Category.QoL);
	}

	@SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onClientChatReceived(ClientChatReceivedEvent event) {
		String msg = EnumChatFormatting.getTextWithoutFormattingCodes(event.message.getUnformattedText().toString());
		if (msg.contains("/animations") ) {
			event.setCanceled(true);
		}
		else if (msg.contains(" joined the lobby!")) {
			event.setCanceled(true);
		}
		else if (msg.contains(" found a") && msg.contains("Mystery Box")) {
			event.setCanceled(true);
		}
		else if (msg.contains("You must wait ")) {
			event.setCanceled(true);
		}
		else if (msg.contains("Please wait ")) {
			event.setCanceled(true);
		}
		else if (msg.contains("is not allowed")) {
			event.setCanceled(true);
		}
		else if (msg.contains("[WATCHDOG ANNOUNCEMENT]")) {
			event.setCanceled(true);
		}
		else if (msg.contains("Watchdog has banned")) {
			event.setCanceled(true);
		}
		else if (msg.contains("Staff have banned")) {
			event.setCanceled(true);
		}
		else if (msg.contains("Blacklisted modifications are a bannable offense!")) {
			event.setCanceled(true);
		}
		else if (msg.contains("(Time Played)")) {
			event.setCanceled(true);
		}
		else if (msg.contains("You don't have enough ") ) {
			event.setCanceled(true);
		}
		else if (msg.contains("You can't break blocks here!") ) {
			event.setCanceled(true);
		}
		else if (msg.contains("You are AFK.") ) {
			event.setCanceled(true);
		}
		else if (msg.contains("You can't place blocks here!") ) {
			event.setCanceled(true);
		}
		else if (msg.contains("If you get disconnected use /rejoin") ) {
			event.setCanceled(true);
		}
	}
}