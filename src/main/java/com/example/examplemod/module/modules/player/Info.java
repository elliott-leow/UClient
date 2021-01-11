package com.example.examplemod.module.modules.player;

import java.awt.Color;
import java.util.Collections;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import com.example.examplemod.module.Category;
import com.example.examplemod.module.Module;
import com.example.examplemod.ui.Hud.ModuleComparator;
import com.example.examplemod.util.Reference;

import io.github.elliottleow.kabbalah.ExampleMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class Info extends Module {
	public Info() {
		super("Info", "Gives game info.", Category.PLAYER);
		this.setKey(Keyboard.KEY_B);
	}
	
	@Override
	public void onEnable() {
		super.onEnable();
		
	}
	
	//TextureManager renderEngine = Minecraft.getMinecraft().renderEngine;
	
	@SubscribeEvent
	public void renderOverlay(RenderGameOverlayEvent event) {
		
	}
		
		public static int rainbow(int delay) {
			double rainbowState = Math.ceil((System.currentTimeMillis() + delay) / 20.0);
			rainbowState %= 360;
			return Color.getHSBColor((float) (rainbowState / 360.0f), 0.5f, 1f).getRGB();
		
	}
}
