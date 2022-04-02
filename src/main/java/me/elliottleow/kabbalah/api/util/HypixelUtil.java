package me.elliottleow.kabbalah.api.util;

import java.io.IOException;
import java.net.URL;
import java.util.Scanner;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
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
	
	public static String getJsonName(String name) throws IOException {
		//https://api.mojang.com/users/profiles/minecraft/quirple
		URL url1 = new URL("https://api.mojang.com/users/profiles/minecraft/"+name);
		Scanner sc = new Scanner(url1.openStream());
	    StringBuffer sb = new StringBuffer();
	    while(sc.hasNext()) {
	       sb.append(sc.next());
	    }
	    String result = sb.toString();
	    result = result.replaceAll("<[^>]*>", "");
	    sc.close();
	    JsonObject json;
		
		json = new JsonParser().parse(result).getAsJsonObject();
		return getJsonUUID(json.get("id").getAsString()).toString();
	}
	
	public static String getJsonUUID(String uuid) throws IOException {
		if (Cache.playerCache.containsKey(uuid)) return Cache.playerCache.get(uuid)[0];
		URL url1 = new URL("https://api.hypixel.net/player?key=5668d932-38bf-4611-a98c-d3690338bcc6&uuid="+uuid);
		Scanner sc = new Scanner(url1.openStream());
	    StringBuffer sb = new StringBuffer();
	    while(sc.hasNext()) {
	       sb.append(sc.next());
	    }
	    String result = sb.toString();
	    result = result.replaceAll("<[^>]*>", "");
	    sc.close();
	    String[] value ={result, Long.toString(System.currentTimeMillis())};
	    Cache.playerCache.put(uuid, value);
	    return result;
	}
}
