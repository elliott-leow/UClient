package com.example.examplemod.module.modules.player;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.lwjgl.input.Keyboard;

import com.example.examplemod.module.Category;
import com.example.examplemod.module.Module;
import com.google.common.collect.Ordering;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiPlayerTabOverlay;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class Testing extends Module {
	public Testing() {
		super("Testing", "Testing random functions pls ignore", Category.PLAYER);
		
		this.setKey(Keyboard.KEY_O);
	}
	
	@Override
	public void onEnable() {
		super.onEnable();
		
		StringBuffer sb = new StringBuffer();
		final List<EntityPlayer> playerList = Minecraft.getMinecraft().theWorld.playerEntities;
		for (EntityPlayer s : playerList) {
	         sb.append(s.getDisplayNameString());
	         sb.append(" ");
	      }
		Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(sb.toString()));
		
		Collection<NetworkPlayerInfo> players = Minecraft.getMinecraft().getNetHandler().getPlayerInfoMap();
		StringBuffer sb1 = new StringBuffer();
		for (NetworkPlayerInfo s : players) {
	         sb1.append(s.getGameProfile().getName());
	         sb1.append(" ");
	      }
		Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("\n\n" + sb1.toString()));
	}
	@SubscribeEvent
	public void onChat(ClientChatReceivedEvent e) throws IOException {
		final String message = e.message.getUnformattedText();
		//if (message.startsWith(Minecraft.getMinecraft().thePlayer.getName()))
	}
	
	
	private List<EntityPlayer> getTabPlayerList() {
		final List<EntityPlayer> list;
		(list = new ArrayList<EntityPlayer>()).clear();
		Ordering<NetworkPlayerInfo> field_175252_a = field_175252_a();
		if (field_175252_a == null) {
			return list;
		}
		final List players = field_175252_a.sortedCopy(Minecraft.getMinecraft().thePlayer.sendQueue.getPlayerInfoMap());
		for (final Object o : players) {
			final NetworkPlayerInfo info = (NetworkPlayerInfo) o;
			if (info == null) {
				continue;
			}
			list.add(Minecraft.getMinecraft().theWorld.getPlayerEntityByName(info.getGameProfile().getName()));
		}
		return list;
	}
	
	private Ordering<NetworkPlayerInfo> field_175252_a() {
		try {
			final Class<GuiPlayerTabOverlay> c = GuiPlayerTabOverlay.class;
			final Field f = c.getField("field_175252_a");
			f.setAccessible(true);
			return (Ordering<NetworkPlayerInfo>)f.get(GuiPlayerTabOverlay.class);
		} catch (Exception e) {
			return null;
		}
	}
}
