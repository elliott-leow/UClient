package com.example.examplemod.module.modules.defense;

import org.lwjgl.input.Keyboard;

import com.example.examplemod.module.Category;
import com.example.examplemod.module.Module;

public class HippoAssist extends Module {
	public HippoAssist() {
		super("Hippo Assist", "Gives Prebow Aim Suggestion.", Category.PLAYER);
		this.setKey(Keyboard.KEY_J);
	}
}