package com.example.examplemod.util.entity;

import java.util.List;
import java.util.Objects;

import javax.annotation.Nullable;

import io.github.elliottleow.kabbalah.Helper;
//import io.github.elliottleow.kabbalah.Helper.getRidingEntity;
import net.minecraft.block.BlockLiquid;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class EntityUtils {
	
	public static boolean isBatsDisabled = false;
	
	public static boolean isLiving(Entity entity) {
	    return entity instanceof EntityLivingBase;
	  }

	  /** If the entity is a player */
	  public static boolean isPlayer(Entity entity) {
	    return entity instanceof EntityPlayer;
	  }

	  public static boolean isLocalPlayer(Entity entity) {
	    return Objects.equals(Helper.getLocalPlayer(), entity);
	  }

	  public static boolean isFakeLocalPlayer(Entity entity) {
	    return entity != null && entity.getEntityId() == -100;
	  }
	  
	  public static boolean isAlive(Entity entity) {
		    return isLiving(entity) && !entity.isDead && ((EntityLivingBase) (entity)).getHealth() > 0;
		  }
	  
	  public static boolean isValidEntity(Entity entity) {
		    Entity riding = Helper.getLocalPlayer().ridingEntity;
		    return entity.ticksExisted > 1
		        && !isFakeLocalPlayer(entity)
		        && (riding == null || !riding.equals(entity));
		  }
	  
	  /** If the mob by default wont attack the player, but will if the player attacks it */
	  public static boolean isNeutralMob(Entity entity) {
	    return entity instanceof EntityPigZombie
	        || entity instanceof EntityWolf
	        || entity instanceof EntityEnderman;
	  }
	  
	  /** Find the entities interpolated amount */
	  public static Vec3 getInterpolatedAmount(Entity entity, double x, double y, double z) {
	    return new Vec3(
	        (entity.posX - entity.lastTickPosX) * x,
	        (entity.posY - entity.lastTickPosY) * y,
	        (entity.posZ - entity.lastTickPosZ) * z);
	  }
	  
	  public static Vec3 getInterpolatedAmount(Entity entity, Vec3 vec) {
		    return getInterpolatedAmount(entity, vec.xCoord, vec.yCoord, vec.zCoord);
		  }

		  public static Vec3 getInterpolatedAmount(Entity entity, double ticks) {
		    return getInterpolatedAmount(entity, ticks, ticks, ticks);
		  }

		  /** Find the entities interpolated position */
		  public static Vec3 getInterpolatedPos(Entity entity, double ticks) {
		    return new Vec3(entity.lastTickPosX, entity.lastTickPosY, entity.lastTickPosZ)
		        .add(getInterpolatedAmount(entity, ticks));
		  }

		  /** Find the entities interpolated eye position */
		  public static Vec3 getInterpolatedEyePos(Entity entity, double ticks) {
		    return getInterpolatedPos(entity, ticks).addVector(0, entity.getEyeHeight(), 0);
		  }

		  /** Get entities eye position */
		  public static Vec3 getEyePos(Entity entity) {
		    return new Vec3(entity.posX, entity.posY + entity.getEyeHeight(), entity.posZ);
		  }
	  

	  
	  
	 
	 
}
