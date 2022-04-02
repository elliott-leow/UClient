package me.elliottleow.kabbalah.command.commands;

import java.util.Map;

import javax.security.auth.login.LoginException;

import me.elliottleow.kabbalah.api.util.Cache;
import me.elliottleow.kabbalah.command.Command;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

public class Debug extends Command {

	public Debug() {
		super("Debug", "Debug commands", "Debug", "deb");
	}

	@Override
	public void onCommand(String[] args, String command) throws LoginException, InterruptedException {
		if (args[0].equals("test")) {
			String msg = msgPrefix + " test command has been run... args: ";
			for (String arg : args) msg += arg + " ";
			Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.DARK_AQUA + msg));
		} else if (args[0].equals("cache")) {
			for (Map.Entry<String,String[]> entry : Cache.playerCache.entrySet())
				Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.DARK_AQUA+"Key = " + entry.getKey() +
	                             ", Value = " + entry.getValue()));
		}
		
	}
}
