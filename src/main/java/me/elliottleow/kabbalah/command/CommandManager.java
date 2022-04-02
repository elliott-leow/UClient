package me.elliottleow.kabbalah.command;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.security.auth.login.LoginException;

import com.mojang.realmsclient.gui.ChatFormatting;

import me.elliottleow.kabbalah.Kabbalah;
import me.elliottleow.kabbalah.command.commands.Debug;
import me.elliottleow.kabbalah.command.commands.StatChecker;
import me.elliottleow.kabbalah.command.commands.test;
import me.elliottleow.kabbalah.module.ModuleManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent;

public class CommandManager { 
	
	public List<Command> commands = new ArrayList<Command>();
	String prefix = ".";
	
	
	public CommandManager() {
		MinecraftForge.EVENT_BUS.register(this);
		commands.add(new test());
		commands.add(new StatChecker());
		commands.add(new Debug());
	}
	
	@SubscribeEvent
	public void onChat(ServerChatEvent event) throws LoginException, InterruptedException {
		
		
		if (!event.username.equals(Minecraft.getMinecraft().thePlayer.getName())
				|| !event.message.startsWith(prefix)) return;
		String[] args = event.message.substring(prefix.length()).split(" ");
		for (Command c : commands) {
			String commandName = args[0];
			if(c.aliases.contains(commandName) || c.name.equalsIgnoreCase(commandName)) {
        		c.onCommand(Arrays.copyOfRange(args, 1, args.length), event.message);
        		event.setCanceled(true);
        		return;
        		}
		}
		Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.DARK_AQUA + Command.msgPrefix + " command does not exist"));
		event.setCanceled(true);
			
		
	}
	
}
