package com.example.examplemod.module.modules.offense;

import org.lwjgl.input.Keyboard;

import com.example.examplemod.module.Category;
import com.example.examplemod.module.Module;

public class PrebowAim extends Module {
	public PrebowAim() {
		super("Prebow Aim", "Gives Prebow Aim Suggestion.", Category.OFFENSE);
		this.setKey(Keyboard.KEY_X);
	}
}
