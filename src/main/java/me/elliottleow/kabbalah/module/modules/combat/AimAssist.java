package me.elliottleow.kabbalah.module.modules.combat;

import java.util.List;

import org.lwjgl.input.Keyboard;

import me.elliottleow.kabbalah.Kabbalah;
import me.elliottleow.kabbalah.module.Category;
import me.elliottleow.kabbalah.module.Module;
import me.elliottleow.kabbalah.settings.Setting;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class AimAssist extends Module {

	public AimAssist() {
		super("Aim Assist", "Moves your crosshair to the nearest player", Category.Combat);
		Kabbalah.settingsManager.rSetting(new Setting("Severity", this, 2, 0, 30, false));
		Kabbalah.settingsManager.rSetting(new Setting("Range", this, 4, 0, 30, false));
		Kabbalah.settingsManager.rSetting(new Setting("Wall Check", this, true));
		Kabbalah.settingsManager.rSetting(new Setting("Leather Armor Check", this, true));
		Kabbalah.settingsManager.rSetting(new Setting("Hold Only", this, true));
		Kabbalah.settingsManager.rSetting(new Setting("Sword Only", this, true));
		Kabbalah.settingsManager.rSetting(new Setting("Target Invis", this, true));
		this.setKey(Keyboard.KEY_M);
	}
	
	public static List<EntityPlayer> getPlayersAroundPlayer(float range) {
        AxisAlignedBB area = new AxisAlignedBB(
        		mc.thePlayer.posX - range,
        		mc.thePlayer.posY - range,
        		mc.thePlayer.posZ - range,
        		mc.thePlayer.posX + range,
        		mc.thePlayer.posY + range,
        		mc.thePlayer.posZ + range);
        return mc.theWorld.getEntitiesWithinAABB(EntityPlayer.class, area);
    }
	
	public static Minecraft mc = Minecraft.getMinecraft();
	
	public static float[] getYawPitchBetween(
            double sourceX, double sourceY, double sourceZ,
            double targetX, double targetY, double targetZ) {
        double diffX = targetX - sourceX;
        double diffY = targetY - sourceY;
        double diffZ = targetZ - sourceZ;
        double dist = MathHelper.sqrt_double(diffX * diffX + diffZ * diffZ);
        float yaw = (float) ((Math.atan2(diffZ, diffX) * 180.0D / Math.PI) - 90.0F );
        float pitch = (float) - (Math.atan2(diffY, dist) * 180.0D / Math.PI);
        return new float[] { yaw, pitch };
    }
	
	public static float[] getClosestYawPitchBetween(Entity source, Entity target) {
        float[] bestYawPitch = new float[] { Float.MAX_VALUE, Float.MAX_VALUE };
        for (float factor : new float[]{0f, 0.05f, 0.1f, 0.25f, 0.5f, 0.75f, 1.0f}) {
            float[] yawPitch = getYawPitchBetween(
                    source.posX,
                    source.posY + source.getEyeHeight(),
                    source.posZ,
                    target.posX,
                    target.posY + target.getEyeHeight() * factor,
                    target.posZ);
            if (Math.abs(yawPitch[0]) + Math.abs(yawPitch[1]) < Math.abs(bestYawPitch[0]) + Math.abs(bestYawPitch[1])) {
                bestYawPitch = yawPitch;
            }
        }
        return bestYawPitch;
    }
	
	public static Entity getClosestEntityToCrosshair(List<EntityPlayer> entities) {
        float minDist = Float.MAX_VALUE;
        Entity closest = null;
        for(Entity entity : entities){
            float[] yawPitch = getClosestYawPitchBetween(
                    mc.thePlayer, entity);
            float distYaw = MathHelper.abs(MathHelper.wrapAngleTo180_float(yawPitch[0] - mc.thePlayer.rotationYaw));
            float distPitch = MathHelper.abs(MathHelper.wrapAngleTo180_float(yawPitch[1] - mc.thePlayer.rotationPitch));
            float dist = MathHelper.sqrt_float(distYaw*distYaw + distPitch*distPitch);
            if(dist < minDist) {
                closest = entity;
                minDist = dist;
            }
        }
        return closest;
    }
	
	public static float[] getRotationsNeeded(List<EntityPlayer> entitylist, float fovX, float fovY, float stepX, float stepY) {
        float[] yawPitch;
        yawPitch = getClosestYawPitchBetween(mc.thePlayer, getClosestEntityToCrosshair(entitylist));
        float yaw = yawPitch[0];
        float pitch = yawPitch[1];
        boolean inFovX = MathHelper.abs(MathHelper.wrapAngleTo180_float(yaw - mc.thePlayer.rotationYaw)) <= fovX;
        boolean inFovY = MathHelper.abs(MathHelper.wrapAngleTo180_float(pitch - mc.thePlayer.rotationPitch)) <= fovY;
        if(inFovX && inFovY) {
            float yawFinal, pitchFinal;
            yawFinal = ((MathHelper.wrapAngleTo180_float(yaw - mc.thePlayer.rotationYaw)) * stepX) / 100;
            pitchFinal = ((MathHelper.wrapAngleTo180_float(pitch - mc.thePlayer.rotationPitch)) * stepY) / 100;
            return new float[] { mc.thePlayer.rotationYaw + yawFinal, mc.thePlayer.rotationPitch + pitchFinal};
        } else {
            return new float[] { mc.thePlayer.rotationYaw, mc.thePlayer.rotationPitch};
        }
    }
	
    public static void setRotations(float yaw, float pitch) {
    	mc.thePlayer.rotationYaw = yaw;
        mc.thePlayer.rotationPitch = pitch;
    }
	
	@SubscribeEvent
	public void onTick(TickEvent.PlayerTickEvent event) {
		boolean hold = Kabbalah.settingsManager.getSettingByName(this, "Hold Only").getValBoolean();
		boolean sword = Kabbalah.settingsManager.getSettingByName(this, "Sword Only").getValBoolean();
		boolean wall = Kabbalah.settingsManager.getSettingByName(this, "Wall Check").getValBoolean();
		boolean leather = Kabbalah.settingsManager.getSettingByName(this, "Leather Armor Check").getValBoolean();
		boolean invis = Kabbalah.settingsManager.getSettingByName(this, "Target Invis").getValBoolean();
		EntityPlayer user = mc.thePlayer;
		if (Minecraft.getMinecraft().isGamePaused()) return;
		if (!hold || (hold && Minecraft.getMinecraft().gameSettings.keyBindAttack.isKeyDown())) {
			ItemStack current = user.getCurrentEquippedItem();
			if (sword && current==null) return;
			if (!sword || (sword && user.getCurrentEquippedItem().getItem() instanceof ItemSword)) {
				float range = (float) Kabbalah.settingsManager.getSettingByName(this, "Range").getValDouble();
				float severity = (float) Kabbalah.settingsManager.getSettingByName(this, "Severity").getValDouble();
				List<EntityPlayer> entities = getPlayersAroundPlayer(range);
				if (getClosestEntityToCrosshair(entities)==null) return;
				if (getClosestEntityToCrosshair(entities)==user) return;
				EntityPlayer player = (EntityPlayer)getClosestEntityToCrosshair(entities);
				if (!invis && player.isInvisible()) return;
				if (!wall || (wall && user.canEntityBeSeen(player))) {
					if (player==null) return;
					if (user.getCurrentArmor(2)==null || player.getCurrentArmor(2)==null) leather = false;
					if (!leather || (leather && armorleather(user, player) && !armorequal(user, player))) {
						final float[] rotations = getRotationsNeeded(entities, mc.gameSettings.fovSetting, mc.gameSettings.fovSetting, severity, severity);
						setRotations(rotations[0], rotations[1]);
					}
				}
			}
		}
	}
	
	public boolean armorequal(EntityPlayer player, EntityPlayer target) {
		ItemStack playerarmor = player.getCurrentArmor(2);
		ItemStack targetarmor = target.getCurrentArmor(2);
		if (playerarmor.hasTagCompound() && targetarmor.hasTagCompound()) {
			NBTTagCompound playernbt = playerarmor.getTagCompound().getCompoundTag("display");
			NBTTagCompound targetnbt = targetarmor.getTagCompound().getCompoundTag("display");
			if (playernbt.toString().equals(targetnbt.toString())) {
				return true;
			}
		}
		return false;
	}
	
	public boolean armorleather(EntityPlayer player, EntityPlayer target) {
		if (player.getCurrentArmor(2)==null || target.getCurrentArmor(2)==null) return false;
		boolean playerarmor = player.getCurrentArmor(2).getTagCompound().toString().contains("color");
		boolean targetarmor = target.getCurrentArmor(2).getTagCompound().toString().contains("color");
		if (playerarmor && targetarmor) return true;
		return false;
	}
}
