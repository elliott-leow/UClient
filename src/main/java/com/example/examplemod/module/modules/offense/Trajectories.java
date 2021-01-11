package com.example.examplemod.module.modules.offense;


import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import com.example.examplemod.module.Category;
import com.example.examplemod.module.Module;


import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemEgg;
import net.minecraft.item.ItemEnderPearl;
import net.minecraft.item.ItemFishingRod;

import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemSnowball;

import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.client.renderer.GlStateManager;

public class Trajectories extends Module {
	
	private transient int BOX = 0;
	public Trajectories() {
		super("Trajectories", "Shows the trajectory of arrows.", Category.OFFENSE);
		this.setKey(Keyboard.KEY_L);
	}
	
	
	
}
