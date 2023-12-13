package me.elliottleow.kabbalah.api.util;

import net.minecraft.client.Minecraft;
import net.minecraft.util.BlockPos;

public class HypixelUtil {
	
	//should be centered around top block mined
	public static int isTunnel(BlockPos pos) {
		int[][] t = {
			//above
			{0,1,0},
			//sides
			{1,0,0},
			{-1,0,0},
			{0,0,1},
			{0,0,-1},
			{1,-1,0},
			{-1,-1,0},
			{0,-1,1},
			{0,-1,-1},
			//bottom
			{0,-2,0},
			//inside
			{0,0,0},
			{0,-1,0}
					};	
		
		
		//int severity = 0;
		if ((Minecraft.getMinecraft().theWorld.isAirBlock(blockPosCoords(t[0],pos))
				|| Minecraft.getMinecraft().theWorld.isAirBlock(blockPosCoords(t[9],pos)))
				) {
			
			return 0;
		}
		if (!Minecraft.getMinecraft().theWorld.isAirBlock(blockPosCoords(t[11],pos))) return 0;
		return 1;
		
		
		
		
		
		
		
		
		
		
		
	}
	public static BlockPos blockPosCoords(int[] rel, BlockPos pos) {
		return new BlockPos(rel[0]+pos.getX(),rel[1]+pos.getY(),rel[2]+pos.getZ());
	}
}
