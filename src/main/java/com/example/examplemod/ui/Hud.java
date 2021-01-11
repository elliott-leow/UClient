package com.example.examplemod.ui;

import java.awt.Color;
import java.util.Collections;
import java.util.Comparator;

import com.example.examplemod.module.Module;
import com.example.examplemod.module.ModuleManager;
import com.example.examplemod.module.modules.client.HUD;
import com.example.examplemod.util.Reference;

import io.github.elliottleow.kabbalah.ExampleMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class Hud extends Gui {
	
	private Minecraft mc = Minecraft.getMinecraft();
	
	public static class ModuleComparator implements Comparator<Module> {
		
		@Override
		public int compare(Module arg0, Module arg1) {
			if(Minecraft.getMinecraft().fontRendererObj.getStringWidth(arg0.getName()) > Minecraft.getMinecraft().fontRendererObj.getStringWidth(arg1.getName())) {
				return -1;
				
			}
			if(Minecraft.getMinecraft().fontRendererObj.getStringWidth(arg0.getName()) > Minecraft.getMinecraft().fontRendererObj.getStringWidth(arg1.getName())) {
				return 1;
			}
			return 0;
		}
	}
	
	@SubscribeEvent
	public void renderOverlay(RenderGameOverlayEvent event) {
		if (!HUD.enabled) return;
		Collections.sort(ExampleMod.moduleManager.modules, new ModuleComparator());
		
		ScaledResolution sr = new ScaledResolution(mc);
		FontRenderer fr = mc.fontRendererObj;
		
		if(event.type == RenderGameOverlayEvent.ElementType.TEXT) {
			fr.drawStringWithShadow("Kabbalah Client" + " v" + Reference.VERSION, 2, 1, 0xffffffff);
		}
		
		if(event.type == RenderGameOverlayEvent.ElementType.TEXT) {
			int y = 2;
			final int[] counter = {1};
			for(Module mod : ExampleMod.moduleManager.getModuleList()) {
				if (!mod.getName().equalsIgnoreCase("") && mod.isToggled()) {
					fr.drawStringWithShadow(mod.getName(), sr.getScaledWidth() -fr.getStringWidth(mod.getName()) - 2, y, rainbow(counter[0]*300));
					y += fr.FONT_HEIGHT;
					counter[0]++;
				}
			}
		}
	}
		
		public static int rainbow(int delay) {
			double rainbowState = Math.ceil((System.currentTimeMillis() + delay) / 20.0);
			rainbowState %= 360;
			return Color.getHSBColor((float) (rainbowState / 360.0f), 0.5f, 1f).getRGB();
		
	}
}
