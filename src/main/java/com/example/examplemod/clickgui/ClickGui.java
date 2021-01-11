package com.example.examplemod.clickgui;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import com.example.examplemod.clickgui.component.Component;
import com.example.examplemod.clickgui.component.Frame;
import com.example.examplemod.module.Category;
import com.example.examplemod.module.Module;
import com.example.examplemod.module.modules.client.HUD;
import com.example.examplemod.ui.Hud.ModuleComparator;
import com.example.examplemod.util.Reference;

import io.github.elliottleow.kabbalah.ExampleMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ClickGui extends GuiScreen implements Comparator<Module> {

	public static ArrayList<Frame> frames;
	public static int color = 6908265;
	
	public ClickGui() {
		this.frames = new ArrayList<Frame>();
		int frameX = 5;
		for(Category category : Category.values()) {
			Frame frame = new Frame(category);
			frame.setX(frameX);
			frames.add(frame);
			frameX += frame.getWidth() + 1;
		}
	}
	
	@Override
	public void initGui() {
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		this.drawDefaultBackground();
		for(Frame frame : frames) {
			frame.renderFrame(this.fontRendererObj);
			frame.updatePosition(mouseX, mouseY);
			for(Component comp : frame.getComponents()) {
				comp.updateComponent(mouseX, mouseY);
			}
		}
		renderhud();
	}
	
	@Override
    protected void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) throws IOException {
		for(Frame frame : frames) {
			if(frame.isWithinHeader(mouseX, mouseY) && mouseButton == 0) {
				frame.setDrag(true);
				frame.dragX = mouseX - frame.getX();
				frame.dragY = mouseY - frame.getY();
			}
			if(frame.isWithinHeader(mouseX, mouseY) && mouseButton == 1) {
				frame.setOpen(!frame.isOpen());
			}
			if(frame.isOpen()) {
				if(!frame.getComponents().isEmpty()) {
					for(Component component : frame.getComponents()) {
						component.mouseClicked(mouseX, mouseY, mouseButton);
					}
				}
			}
		}
	}
	
	@Override
	protected void keyTyped(char typedChar, int keyCode) {
		for(Frame frame : frames) {
			if(frame.isOpen() && keyCode != 1) {
				if(!frame.getComponents().isEmpty()) {
					for(Component component : frame.getComponents()) {
						component.keyTyped(typedChar, keyCode);
					}
				}
			}
		}
		if (keyCode == 1) {
            this.mc.displayGuiScreen(null);
        }
	}

	
	@Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
		for(Frame frame : frames) {
			frame.setDrag(false);
		}
		for(Frame frame : frames) {
			if(frame.isOpen()) {
				if(!frame.getComponents().isEmpty()) {
					for(Component component : frame.getComponents()) {
						component.mouseReleased(mouseX, mouseY, state);
					}
				}
			}
		}
		renderhud();
	}
	
	@Override
	public boolean doesGuiPauseGame() {
		return true;
	}
	
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
	
	public void renderhud() {
		
		Collections.sort(ExampleMod.moduleManager.modules, new ModuleComparator());
		
		ScaledResolution sr = new ScaledResolution(mc);
		FontRenderer fr = mc.fontRendererObj;
		
		
			fr.drawStringWithShadow("Kabbalah Client" + " v" + Reference.VERSION, 2, 1, 0xffffffff);
		
	
		
			int y = 2;
			final int[] counter = {1};
			for(Module mod : ExampleMod.moduleManager.getModuleList()) {
				if (!mod.getName().equalsIgnoreCase("") && mod.isToggled()) {
					fr.drawStringWithShadow(mod.getName(), sr.getScaledWidth() -fr.getStringWidth(mod.getName()) - 2, y, 0xffffffff);
					y += fr.FONT_HEIGHT;
					counter[0]++;
				}
			}
		
	}
	
	
}
