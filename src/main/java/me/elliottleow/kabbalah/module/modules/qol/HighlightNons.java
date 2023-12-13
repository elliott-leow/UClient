package me.elliottleow.kabbalah.module.modules.qol;

import me.elliottleow.kabbalah.Kabbalah;
import me.elliottleow.kabbalah.module.Category;
import me.elliottleow.kabbalah.module.Module;
import me.elliottleow.kabbalah.settings.Setting;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class HighlightNons extends Module {

	public HighlightNons() {
		super("Highlight Nons", "Turn nons chat color white", Category.QoL);
		Kabbalah.settingsManager.rSetting(new Setting("Full msg", this, false));
	}

	@SubscribeEvent()
    public void onClientChatReceived(ClientChatReceivedEvent event) {
		String msg = event.message.getUnformattedText().toString();
		ChatComponentText message = null;
		if (msg.contains("7: ") && msg.length()>3) {
			if (Kabbalah.settingsManager.getSettingByName(this, "Full msg").getValBoolean()) {
				if (msg.contains("] ")) {
					message = new ChatComponentText((msg.split("] ")[0]) + "] " + EnumChatFormatting.WHITE + EnumChatFormatting.getTextWithoutFormattingCodes(msg).split("] ")[1]);
				}
				else if (!msg.startsWith("7", 1) && msg.contains("r ")) {
					message = new ChatComponentText(msg.split("r ", 1)[0] + EnumChatFormatting.WHITE + EnumChatFormatting.getTextWithoutFormattingCodes(msg.split("r", 1)[1]));
				}
			}
			else {
				if (!msg.startsWith("7", 1) && msg.contains("r ")) {
					message = new ChatComponentText(msg.split("r ", 1)[0] + EnumChatFormatting.getTextWithoutFormattingCodes(msg.split("r", 1)[1]).split(": ")[0] + EnumChatFormatting.WHITE + ": " + EnumChatFormatting.getTextWithoutFormattingCodes(msg.split("r", 1)[1]).split(": ")[1]);
				}
				else {
					message = new ChatComponentText((msg.split("7: ")[0]).substring(0, (msg.split("7: ")[0]).length() - 1) + EnumChatFormatting.WHITE + ": " + msg.split("7: ")[1]);
				}
			}
			Minecraft.getMinecraft().thePlayer.addChatComponentMessage(message.setChatStyle(event.message.getChatStyle()));
			event.setCanceled(true);
		}
	}
}