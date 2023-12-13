package me.elliottleow.kabbalah.api.util;

import com.mojang.authlib.GameProfile;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.network.NetworkManager;

public class NetHandler extends NetHandlerPlayClient {

	public NetHandler(Minecraft mcIn, GuiScreen p_i46300_2_, NetworkManager p_i46300_3_, GameProfile p_i46300_4_) {
		super(mcIn, p_i46300_2_, p_i46300_3_, p_i46300_4_);
	}

}
