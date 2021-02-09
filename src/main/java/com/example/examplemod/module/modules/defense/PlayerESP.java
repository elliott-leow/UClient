package com.example.examplemod.module.modules.defense;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import com.example.examplemod.module.Category;
import com.example.examplemod.module.Module;

import net.minecraft.util.AxisAlignedBB;

public class PlayerESP extends Module {
	
	private transient int BOX = 0;
	public PlayerESP() {
		super("PlayerESP", "See outlines of players.", Category.OFFENSE);
		this.setKey(Keyboard.KEY_L);
	}
	
	
}
