package me.elliottleow.kabbalah.module.modules.defense;

import org.lwjgl.input.Keyboard;

import me.elliottleow.kabbalah.module.Category;
import me.elliottleow.kabbalah.module.Module;

public class HeadHitterESP extends Module {
	public HeadHitterESP() {
		super("HeadHitterESP", "Outlines headhitters", Category.DEFENSE);
		this.setKey(Keyboard.KEY_H);
	}

}
