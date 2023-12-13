package me.elliottleow.kabbalah.module.modules.visual;

import java.awt.Color;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import me.elliottleow.kabbalah.Kabbalah;
import me.elliottleow.kabbalah.module.Category;
import me.elliottleow.kabbalah.module.Module;
import me.elliottleow.kabbalah.settings.Setting;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.init.Blocks;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class BedESP extends Module {
	
	protected static final Minecraft mc = Minecraft.getMinecraft();
	
	public BedESP() {
		super("Bed ESP", "Outlines beds and describes its defense", Category.Visual);
		Kabbalah.settingsManager.rSetting(new Setting("Range", this, 20, 1, 64, true));
		Kabbalah.settingsManager.rSetting(new Setting("Line Width", this, 1, 1, 40, true));
		Kabbalah.settingsManager.rSetting(new Setting("Transparency", this, 80, 0, 100, true));
		this.setKey(Keyboard.KEY_P);
	}
	
	@SubscribeEvent
	public void onRender(RenderWorldLastEvent event) {
		int rgb = rainbowDraw(2L, 0L);
		bedsAreNearby(mc.theWorld, mc.thePlayer.getPosition().getX(), mc.thePlayer.getPosition().getY(), mc.thePlayer.getPosition().getZ(), rgb);
	}
	
	public void bedsAreNearby(World world, int posX, int posY, int posZ, int rgb) {
        int range = (int) Kabbalah.settingsManager.getSettingByName(this, "Range").getValDouble();
        int minX = posX - range;
        int maxX = posX + range;
        int minZ = posZ - range;
        int maxZ = posZ + range;
        int minY = posY - range;
        int maxY = posY + range;

        for (int y = minY; y <= maxY; y++) {
            for (int z = minZ; z <= maxZ; z++) {
                for (int x = minX; x <= maxX; x++) {
                    Block block = world.getBlockState(new BlockPos(x, y, z)).getBlock();
                    if (block == Blocks.bed) {
                        drawbox(new BlockPos(x,y,z), rgb);
                    }
                }
            }
        }
    }
	
	public void drawbox(BlockPos bp, int color) {
        if (bp != null) {
           double x = (double)bp.getX() - mc.getRenderManager().viewerPosX;
           double y = (double)bp.getY() - mc.getRenderManager().viewerPosY;
           double z = (double)bp.getZ() - mc.getRenderManager().viewerPosZ;
           GL11.glBlendFunc(770, 771);
           GL11.glEnable(3042);
           GL11.glLineWidth((float)(Kabbalah.settingsManager.getSettingByName(this, "Line Width").getValDouble())/10f);
           GL11.glDisable(3553);
           GL11.glDisable(2929);
           GL11.glDepthMask(false);
           float a = (float)(Kabbalah.settingsManager.getSettingByName(this, "Transparency").getValDouble())/100f;
           float r = (float)(color >> 16 & 255) / 255.0F;
           float g = (float)(color >> 8 & 255) / 255.0F;
           float b = (float)(color & 255) / 255.0F;
           GL11.glColor4d(r, g, b, a);
           RenderGlobal.drawSelectionBoundingBox(new AxisAlignedBB(x, y, z, x + 1.0D, y + 0.56D, z + 1.0D));
           GL11.glEnable(3553);
           GL11.glEnable(2929);
           GL11.glDepthMask(true);
           GL11.glDisable(3042);
        }
     }
	
	public static int rainbowDraw(long speed, long... delay) {
        long time = System.currentTimeMillis() + (delay.length > 0 ? delay[0] : 0L);
        return Color.getHSBColor((float)(time % (15000L / speed)) / (15000.0F / (float)speed), 1.0F, 1.0F).getRGB();
	}
}