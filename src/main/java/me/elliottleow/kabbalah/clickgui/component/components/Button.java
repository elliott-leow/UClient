package me.elliottleow.kabbalah.clickgui.component.components;

import java.awt.Color;
import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

import me.elliottleow.kabbalah.Kabbalah;
import me.elliottleow.kabbalah.clickgui.ClickGui;
import me.elliottleow.kabbalah.clickgui.component.Component;
import me.elliottleow.kabbalah.clickgui.component.Frame;
import me.elliottleow.kabbalah.clickgui.component.components.sub.Checkbox;
import me.elliottleow.kabbalah.clickgui.component.components.sub.Keybind;
import me.elliottleow.kabbalah.clickgui.component.components.sub.ModeButton;
import me.elliottleow.kabbalah.clickgui.component.components.sub.Slider;
import me.elliottleow.kabbalah.module.Module;
import me.elliottleow.kabbalah.settings.Setting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

public class Button extends Component{
	public Module mod;
	public Frame parent;
	public int offset;
	private boolean isHovered;
	private ArrayList<Component> subcomponents;
	public boolean open;
	@SuppressWarnings("unused")
	private int height;
	
	public Button(Module mod, Frame parent, int offset) {
		this.mod = mod;
		this.parent = parent;
		this.offset = offset;
		this.subcomponents = new ArrayList<Component>();
		this.open = false;
		height = 12;
		int opY = offset + 12;
		if(Kabbalah.settingsManager.getSettingsByMod(mod) != null) {
			for(Setting s : Kabbalah.settingsManager.getSettingsByMod(mod)){
				if(s.isCombo()){
					this.subcomponents.add(new ModeButton(s, this, mod, opY));
					opY += 12;
				}
				if(s.isSlider()){
					this.subcomponents.add(new Slider(s, this, opY));
					opY += 12;
				}
				if(s.isCheck()){
					this.subcomponents.add(new Checkbox(s, this, opY));
					opY += 12;
				}
			}
		}
		this.subcomponents.add(new Keybind(this, opY));
		//this.subcomponents.add(new VisibleButton(this, mod, opY));
	}
	
	@Override
	public void setOff(int newOff) {
		offset = newOff;
		int opY = offset + 12;
		for(Component comp : this.subcomponents) {
			comp.setOff(opY);
			opY += 12;
		}
	}
	
	@Override
	public void renderComponent() {
		Gui.drawRect(parent.getX(), this.parent.getY() + this.offset, parent.getX() + parent.getWidth(), this.parent.getY() + 12 + this.offset, this.isHovered ? (this.mod.isToggled() ? new Color(0xFF222222).darker().getRGB() : 0xFF222222) : (this.mod.isToggled() ? new Color(14,14,14).getRGB() : 0xFF111111));
		GL11.glPushMatrix();
		GL11.glScalef(0.5f,0.5f, 0.5f);
		Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow(this.mod.getName(), (parent.getX() + 3) * 2, (parent.getY() + offset + 2) * 2 + 4, this.mod.isToggled() ? 0x82F6FF : 0xD7D7D7);
		if(this.subcomponents.size() > 2)
		Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow(this.open ? "-" : "+", (parent.getX() + parent.getWidth() - 10) * 2, (parent.getY() + offset + 2) * 2 + 4, 0x82F6FF);
		GL11.glPopMatrix();
		if(this.open) {
			if(!this.subcomponents.isEmpty()) {
				for(Component comp : this.subcomponents) {
					comp.renderComponent();
				}
				Gui.drawRect(parent.getX() + 2, parent.getY() + this.offset + 12, parent.getX() + 3, parent.getY() + this.offset + ((this.subcomponents.size() + 1) * 12), ClickGui.color);
			}
		}
	}
	
	@Override
	public int getHeight() {
		if(this.open) {
			return (12 * (this.subcomponents.size() + 1));
		}
		return 12;
	}
	
	@Override
	public void updateComponent(int mouseX, int mouseY) {
		this.isHovered = isMouseOnButton(mouseX, mouseY);
		if(!this.subcomponents.isEmpty()) {
			for(Component comp : this.subcomponents) {
				comp.updateComponent(mouseX, mouseY);
			}
		}
	}
	
	@Override
	public void mouseClicked(int mouseX, int mouseY, int button) {
		if(isMouseOnButton(mouseX, mouseY) && button == 0) {
			this.mod.toggle();
		}
		if(isMouseOnButton(mouseX, mouseY) && button == 1) {
			this.open = !this.open;
			this.parent.refresh();
		}
		for(Component comp : this.subcomponents) {
			comp.mouseClicked(mouseX, mouseY, button);
		}
	}
	
	@Override
	public void mouseReleased(int mouseX, int mouseY, int mouseButton) {
		for(Component comp : this.subcomponents) {
			comp.mouseReleased(mouseX, mouseY, mouseButton);
		}
	}
	
	@Override
	public void keyTyped(char typedChar, int key) {
		for(Component comp : this.subcomponents) {
			comp.keyTyped(typedChar, key);
		}
	}
	
	public boolean isMouseOnButton(int x, int y) {
		if(x > parent.getX() && x < parent.getX() + parent.getWidth() && y > this.parent.getY() + this.offset && y < this.parent.getY() + 12 + this.offset) {
			return true;
		}
		return false;
	}
}
