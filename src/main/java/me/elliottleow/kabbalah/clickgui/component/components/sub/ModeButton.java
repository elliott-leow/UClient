package me.elliottleow.kabbalah.clickgui.component.components.sub;

import org.lwjgl.opengl.GL11;

import me.elliottleow.kabbalah.clickgui.component.Component;
import me.elliottleow.kabbalah.clickgui.component.components.Button;
import me.elliottleow.kabbalah.module.Module;
import me.elliottleow.kabbalah.settings.Setting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

public class ModeButton extends Component {

	private boolean hovered;
	private Button parent;
	private Setting set;
	private int offset;
	private int x;
	private int y;
	@SuppressWarnings("unused")
	private Module mod;

	private int modeIndex;
	
	public ModeButton(Setting set, Button button, Module mod, int offset) {
		this.set = set;
		this.parent = button;
		this.mod = mod;
		this.x = button.parent.getX() + button.parent.getWidth();
		this.y = button.parent.getY() + button.offset;
		this.offset = offset;
		this.modeIndex = 0;
	}
	
	@Override
	public void setOff(int newOff) {
		offset = newOff;
	}
	
	@Override
	public void renderComponent() {
		Gui.drawRect(parent.parent.getX() + 2, parent.parent.getY() + offset, parent.parent.getX() + (parent.parent.getWidth() * 1), parent.parent.getY() + offset + 12, this.hovered ? 0xFF2A2A2A : 0xFF141414);
		Gui.drawRect(parent.parent.getX(), parent.parent.getY() + offset, parent.parent.getX() + 2, parent.parent.getY() + offset + 12, 0xFF111111);
		GL11.glPushMatrix();
		GL11.glScalef(0.5f,0.5f, 0.5f);
		//Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow("Mode: " + set.getValString(), (parent.parent.getX() + 7) * 2, (parent.parent.getY() + offset + 2) * 2 + 5, 0xC9C9C9);
		Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow("Mode: ", (parent.parent.getX() + 7) * 2, (parent.parent.getY() + offset + 2) * 2 + 5, 0xC9C9C9);
		Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow(set.getValString(), (parent.parent.getX() + 22) * 2, (parent.parent.getY() + offset + 2) * 2 + 5, 0x82F6FF);
		GL11.glPopMatrix();
	}
	
	@Override
	public void updateComponent(int mouseX, int mouseY) {
		this.hovered = isMouseOnButton(mouseX, mouseY);
		this.y = parent.parent.getY() + offset;
		this.x = parent.parent.getX();
	}
	
	@Override
	public void mouseClicked(int mouseX, int mouseY, int button) {
		if(isMouseOnButton(mouseX, mouseY) && button == 0 && this.parent.open) {
			int maxIndex = set.getOptions().size();

			if(modeIndex+1 >= maxIndex) {
				modeIndex = 0;
			}
			else {
				modeIndex++;
			}
			System.out.println("Mode: " + set.getOptions().get(modeIndex));
			set.setValString(set.getOptions().get(modeIndex));
		}
	}
	
	public boolean isMouseOnButton(int x, int y) {
		if(x > this.x && x < this.x + 88 && y > this.y && y < this.y + 12) {
			return true;
		}
		return false;
	}
}
