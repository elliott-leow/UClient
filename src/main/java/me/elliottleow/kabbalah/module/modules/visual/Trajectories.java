package me.elliottleow.kabbalah.module.modules.visual;

import org.lwjgl.input.Keyboard;

import me.elliottleow.kabbalah.Kabbalah;
import me.elliottleow.kabbalah.module.Category;
import me.elliottleow.kabbalah.module.Module;
import me.elliottleow.kabbalah.settings.Setting;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemEgg;
import net.minecraft.item.ItemEnderPearl;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemSnowball;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class Trajectories extends Module{

	public Trajectories() {
		super("Trajectories", "Renders trajectories of certain projectiles", Category.Visual);
		Kabbalah.settingsManager.rSetting(new Setting("Bow", this, true));
		Kabbalah.settingsManager.rSetting(new Setting("Egg", this, true));
		Kabbalah.settingsManager.rSetting(new Setting("Potion", this, true));
		Kabbalah.settingsManager.rSetting(new Setting("Snowball", this, true));
		Kabbalah.settingsManager.rSetting(new Setting("Ender Pearl", this, true));
		this.setKey(Keyboard.KEY_Z);
	}
	
	public static Minecraft mc = Minecraft.getMinecraft();
	
	@SubscribeEvent
	public void render(RenderWorldLastEvent event) {
		ItemStack item = mc.thePlayer.getHeldItem();
		if (item==null) return;
		if ((Kabbalah.settingsManager.getSettingByName(this, "Bow").getValBoolean() && item.getItem() instanceof ItemBow) ||
			(Kabbalah.settingsManager.getSettingByName(this, "Egg").getValBoolean() && item.getItem() instanceof ItemEgg) ||
			(Kabbalah.settingsManager.getSettingByName(this, "Potion").getValBoolean() && item.getItem() instanceof ItemPotion) ||
			(Kabbalah.settingsManager.getSettingByName(this, "Snowball").getValBoolean() && item.getItem() instanceof ItemSnowball) ||
			(Kabbalah.settingsManager.getSettingByName(this, "Ender Pearl").getValBoolean() && item.getItem() instanceof ItemEnderPearl)) {
			/*boolean usingBow = mc.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemBow;
			double arrowPosX = mc.thePlayer.lastTickPosX
					+ (mc.thePlayer.posX - mc.thePlayer.lastTickPosX) * mc.timer.renderPartialTicks
					- Math.cos((float)Math.toRadians(mc.thePlayer.rotationYaw)) * 0.16F;
			double arrowPosY = mc.thePlayer.lastTickPosY
				+ (mc.thePlayer.posY - mc.thePlayer.lastTickPosY)
					* Minecraft.getMinecraft().timer.renderPartialTicks
				+ mc.thePlayer.getEyeHeight() - 0.1;
			double arrowPosZ = mc.thePlayer.lastTickPosZ
				+ (mc.thePlayer.posZ - mc.thePlayer.lastTickPosZ)
					* Minecraft.getMinecraft().timer.renderPartialTicks
				- Math.sin((float)Math.toRadians(mc.thePlayer.rotationYaw)) * 0.16F;
			float arrowMotionFactor = usingBow ? 1F : 0.4F;
			float yaw = (float)Math.toRadians(mc.thePlayer.rotationYaw);
			float pitch = (float)Math.toRadians(mc.thePlayer.rotationPitch);
			float arrowMotionX =
				(float) (-Math.sin(yaw) * Math.cos(pitch) * arrowMotionFactor);
			float arrowMotionY = (float) (-Math.sin(pitch) * arrowMotionFactor);
			float arrowMotionZ =
				(float) (Math.cos(yaw) * Math.cos(pitch) * arrowMotionFactor);
			double arrowMotion = Math.sqrt(arrowMotionX * arrowMotionX
				+ arrowMotionY * arrowMotionY + arrowMotionZ * arrowMotionZ);
			arrowMotionX /= arrowMotion;
			arrowMotionY /= arrowMotion;
			arrowMotionZ /= arrowMotion;
			if(usingBow)
			{
				float bowPower = (72000 - mc.thePlayer.getItemInUseCount()) / 20F;
				bowPower = (bowPower * bowPower + bowPower * 2F) / 3F;
				
				if(bowPower > 1F)
					bowPower = 1F;
				
				if(bowPower <= 0.1F)
					bowPower = 1F;
				
				bowPower *= 3F;
				arrowMotionX *= bowPower;
				arrowMotionY *= bowPower;
				arrowMotionZ *= bowPower;
			}else
			{
				arrowMotionX *= 1.5D;
				arrowMotionY *= 1.5D;
				arrowMotionZ *= 1.5D;
			}
			
			// GL settings
			GL11.glPushMatrix();
			GL11.glEnable(GL11.GL_LINE_SMOOTH);
			GL11.glBlendFunc(770, 771);
			GL11.glEnable(3042);
			GL11.glDisable(3553);
			GL11.glDisable(2929);
			GL11.glEnable(GL13.GL_MULTISAMPLE);
			GL11.glDepthMask(false);
			GL11.glLineWidth(1.8F);
			
			RenderManager renderManager = mc.getRenderManager();
			
			// draw trajectory line
			double gravity =
				usingBow ? 0.05D : item instanceof ItemPotion ? 0.4D : 0.03D;
			Vec3d playerVector = new Vec3d(mc.thePlayer.posX,
					mc.thePlayer.posY + mc.thePlayer.getEyeHeight(), mc.thePlayer.posZ);
			GL11.glColor3d(0, 1, 0);
			GL11.glBegin(GL11.GL_LINE_STRIP);
			for(int i = 0; i < 1000; i++)
			{
				GL11.glVertex3d(arrowPosX - renderManager.renderPosX,
					arrowPosY - renderManager.renderPosY,
					arrowPosZ - renderManager.renderPosZ);
				
				arrowPosX += arrowMotionX;
				arrowPosY += arrowMotionY;
				arrowPosZ += arrowMotionZ;
				arrowMotionX *= 0.99D;
				arrowMotionY *= 0.99D;
				arrowMotionZ *= 0.99D;
				arrowMotionY -= gravity;
				
				if(mc.theWorld.rayTraceBlocks(playerVector,
					new Vec3d(arrowPosX, arrowPosY, arrowPosZ)) != null)
					break;
			}
			GL11.glEnd();
			
			// draw end of trajectory line
			double renderX = arrowPosX - renderManager.renderPosX;
			double renderY = arrowPosY - renderManager.renderPosY;
			double renderZ = arrowPosZ - renderManager.renderPosZ;
			AxisAlignedBB bb = new AxisAlignedBB(renderX - 0.5, renderY - 0.5,
				renderZ - 0.5, renderX + 0.5, renderY + 0.5, renderZ + 0.5);
			GL11.glColor4d(0, 1, 0, 0.15F);
			//RenderUtils.drawColorBox(bb);
			GL11.glColor4d(0, 0, 0, 0.5F);
			RenderGlobal.drawOutlinedBoundingBox(bb, -1);
			
			// GL resets
			GL11.glDisable(3042);
			GL11.glEnable(3553);
			GL11.glEnable(2929);
			GL11.glDisable(GL13.GL_MULTISAMPLE);
			GL11.glDepthMask(true);
			GL11.glDisable(GL11.GL_LINE_SMOOTH);
			GL11.glPopMatrix();*/
		}
	}
	
}
