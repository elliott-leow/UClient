package me.elliottleow.kabbalah.module.modules.visual;

import org.lwjgl.input.Keyboard;

import me.elliottleow.kabbalah.module.Category;
import me.elliottleow.kabbalah.module.Module;

public class HeadHitterESP extends Module {
	public HeadHitterESP() {
		super("HeadHitterESP", "Outlines headhitters", Category.Visual);
		this.setKey(Keyboard.KEY_H);
	}

}
