package me.elliottleow.kabbalah.command.commands;

import javax.security.auth.login.LoginException;

import me.elliottleow.kabbalah.command.Command;
import me.elliottleow.kabbalah.command.CommandManager;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

public class test extends Command {

	public test() {
		super("test", "Kabbalah test command", "test", "te");
	}

	@Override
	public void onCommand(String[] args, String command) throws LoginException, InterruptedException {
		String msg = msgPrefix + " test command has been run... args: ";
		for (String arg : args) msg += arg + " ";
		Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.DARK_AQUA + msg));
		
	}
}
