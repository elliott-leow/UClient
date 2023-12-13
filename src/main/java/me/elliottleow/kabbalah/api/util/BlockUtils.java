package me.elliottleow.kabbalah.api.util;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.util.BlockPos;

public class BlockUtils {
	
	public static Block getBlock(BlockPos pos) {
		return Minecraft.getMinecraft().theWorld.getBlockState(new BlockPos(pos.getX(), pos.getY(), pos.getZ())).getBlock();
	}

	public static Block getBlock(double x, double y, double z) {
		return Minecraft.getMinecraft().theWorld.getBlockState(new BlockPos(x, y, z)).getBlock();
	}
	
	public static Block getBlock(int x, int y, int z) {
		return Minecraft.getMinecraft().theWorld.getBlockState(new BlockPos(x, y, z)).getBlock();
	}
	
	
}
