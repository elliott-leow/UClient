package io.github.elliottleow.kabbalah;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.Entity;

import javax.annotation.Nullable;



public class Helper implements Globals {
	public static Minecraft getMinecraft() {
	    return MC;
	  }
	
	  @Nullable
	  public static WorldClient getWorld() {
	    return MC.theWorld;
	  }
	  
	  public static EntityPlayerSP getLocalPlayer() {
		    return MC.thePlayer;
		  }
	  
	  @Nullable
	  public static Entity getRidingEntity() {
	    if (getLocalPlayer() != null) return getLocalPlayer().ridingEntity;
	    else return null;
	  }
}
