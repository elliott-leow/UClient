package me.elliottleow.kabbalah.api.util;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import org.lwjgl.opengl.GL11;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ActiveRenderInfo;
//import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderGlobal;
//import net.minecraft.client.renderer.Tessellator;
//import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Vec3;
//import net.minecraft.util.Vec3i;
//import net.minecraftforge.client.event.RenderWorldLastEvent;

public class RenderUtils {
	public static void entityESPBox(Entity entity,  double r, double g, double b, double t) 
	{
		GL11.glPushMatrix();
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(770, 771);
		//GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		//GL11.glEnable(GL11.GL_LINE_SMOOTH);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glDepthMask(false);
		GL11.glLineWidth((float) t);
		
		//GL11.glDepthMask(false);
		if(r == -1) GL11.glColor4d(
					1 - Minecraft.getMinecraft().thePlayer
						.getDistanceToEntity(entity) / 40,
					Minecraft.getMinecraft().thePlayer.getDistanceToEntity(entity) / 40,
					0, 0.5F);
		else GL11.glColor4d(r, g, b, 1F);
		Minecraft.getMinecraft().getRenderManager();
		RenderGlobal.drawSelectionBoundingBox(
			new AxisAlignedBB(
				entity.getEntityBoundingBox().minX
					- 0.05
					- entity.posX
					+ (entity.posX - Minecraft.getMinecraft()
						.getRenderManager().viewerPosX),
				entity.getEntityBoundingBox().minY
					- entity.posY
					+ (entity.posY - Minecraft.getMinecraft()
						.getRenderManager().viewerPosY),
				entity.getEntityBoundingBox().minZ
					- 0.05
					- entity.posZ
					+ (entity.posZ - Minecraft.getMinecraft()
						.getRenderManager().viewerPosZ),
				entity.getEntityBoundingBox().maxX
					+ 0.05
					- entity.posX
					+ (entity.posX - Minecraft.getMinecraft()
						.getRenderManager().viewerPosX),
				entity.getEntityBoundingBox().maxY
					+ 0.1
					- entity.posY
					+ (entity.posY - Minecraft.getMinecraft()
						.getRenderManager().viewerPosY),
				entity.getEntityBoundingBox().maxZ
					+ 0.05
					- entity.posZ
					+ (entity.posZ - Minecraft.getMinecraft()
						.getRenderManager().viewerPosZ)));
		//GL11.glDisable(GL11.GL_LINE_SMOOTH);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		//GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glDepthMask(true);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glPopMatrix();
	}
	
	public static void itemESPBox(Entity entity,  double r, double g, double b, double t) 
	{
		GL11.glPushMatrix();
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(770, 771);
		//GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		//GL11.glEnable(GL11.GL_LINE_SMOOTH);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glDepthMask(false);
		GL11.glLineWidth((float) t);
		
		//GL11.glDepthMask(false);
		if(r == -1) GL11.glColor4d(
					1 - Minecraft.getMinecraft().thePlayer
						.getDistanceToEntity(entity) / 40,
					Minecraft.getMinecraft().thePlayer.getDistanceToEntity(entity) / 40,
					0, 0.5F);
		else GL11.glColor4d(r, g, b, 1F);
		Minecraft.getMinecraft().getRenderManager();
		RenderGlobal.drawSelectionBoundingBox(
			new AxisAlignedBB(
				entity.getEntityBoundingBox().minX
					- 0.05
					- entity.posX
					+ (entity.posX - Minecraft.getMinecraft()
						.getRenderManager().viewerPosX),
				entity.getEntityBoundingBox().minY
					- entity.posY
					+ (entity.posY - Minecraft.getMinecraft()
						.getRenderManager().viewerPosY),
				entity.getEntityBoundingBox().minZ
					- 0.05
					- entity.posZ
					+ (entity.posZ - Minecraft.getMinecraft()
						.getRenderManager().viewerPosZ),
				entity.getEntityBoundingBox().maxX
					+ 0.05
					- entity.posX
					+ (entity.posX - Minecraft.getMinecraft()
						.getRenderManager().viewerPosX),
				entity.getEntityBoundingBox().maxY
					+ 0.1
					- entity.posY
					+ (entity.posY - Minecraft.getMinecraft()
						.getRenderManager().viewerPosY),
				entity.getEntityBoundingBox().maxZ
					+ 0.05
					- entity.posZ
					+ (entity.posZ - Minecraft.getMinecraft()
						.getRenderManager().viewerPosZ)));
		//GL11.glDisable(GL11.GL_LINE_SMOOTH);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		//GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glDepthMask(true);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glPopMatrix();
	}
	
	public static void drawTracerLine(Entity entity, double r, double g, double b, double t) {
        double X = entity.posX;
        double Y = entity.posY;
        double Z = entity.posZ;
        double mX = Minecraft.getMinecraft().thePlayer.posX;
        double mY = Minecraft.getMinecraft().thePlayer.posY;
        double mZ = Minecraft.getMinecraft().thePlayer.posZ;
        double dX = (mX - X);
        double dY = (mY - Y);
        double dZ = (mZ - Z);
        double size = 0.45;
        //double ytSize = 0.001;
		
		GL11.glBlendFunc(770, 771);
		GL11.glEnable(GL11.GL_BLEND); //+
		GL11.glLineWidth((float) t);
		//GL11.glDisable(2929 /*GL_DEPTH_TEST*/);
		GL11.glDisable(GL11.GL_TEXTURE_2D); //+
		GL11.glDisable(GL11.GL_DEPTH_TEST); //+
		GL11.glDepthMask(false);
		Vec3 eyes=ActiveRenderInfo.getPosition();
        //GL11.glBegin(GL11.GL_LINES);
        GL11.glColor4d(r,g,b,1F);
        GL11.glBegin(GL11.GL_LINES);
        
        GL11.glVertex3d(eyes.xCoord, eyes.yCoord, eyes.zCoord);
        GL11.glVertex3d((-dX + size) - 0.5, -dY, (-dZ - size) + 0.5);
                
        GL11.glVertex3d((-dX + size) - 0.5, -dY, (-dZ - size) + 0.5);
        GL11.glVertex3d((-dX + size) - 0.5, - dY + entity.height, (-dZ - size) + 0.5);

        GL11.glEnd();
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
	    GL11.glDepthMask(true);
	    //GL11.glEnable(2929 /*GL_DEPTH_TEST*/); 
	    GL11.glDisable(GL11.GL_BLEND);
	}
	
	public static void blockESPBox(BlockPos blockPos)
	{
		double x =
			blockPos.getX()
				- Minecraft.getMinecraft().getRenderManager().viewerPosX;
		double y =
			blockPos.getY()
				- Minecraft.getMinecraft().getRenderManager().viewerPosY;
		double z =
			blockPos.getZ()
				- Minecraft.getMinecraft().getRenderManager().viewerPosZ;
		GL11.glBlendFunc(770, 771);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glLineWidth(2.0F);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glDepthMask(false);
		//GL11.glColor4d(0, 1, 0, 0.15F);
		//drawColorBox(new AxisAlignedBB(x, y, z, x + 1.0, y + 1.0, z + 1.0));
		GL11.glColor4d(0, 0, 1, 0.5F);
		RenderGlobal.drawSelectionBoundingBox(new AxisAlignedBB(x, y, z, x + 1.0, y + 1.0, z + 1.0));
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glDepthMask(true);
		GL11.glDisable(GL11.GL_BLEND);
	}
	public static void blockESPBox(BlockPos blockPos, double mode, double t)
	{
		double x =
			blockPos.getX()
				- Minecraft.getMinecraft().getRenderManager().viewerPosX;
		double y =
			blockPos.getY()
				- Minecraft.getMinecraft().getRenderManager().viewerPosY;
		double z =
			blockPos.getZ()
				- Minecraft.getMinecraft().getRenderManager().viewerPosZ;
		GL11.glBlendFunc(770, 771);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glLineWidth((float)t);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glDepthMask(false);
		//drawColorBox(new AxisAlignedBB(x, y, z, x + 1.0, y + 1.0, z + 1.0));
		GL11.glColor3d(0, 1, 0);
		if (mode == 1) GL11.glColor3d(1,0, 0);
		RenderGlobal.drawSelectionBoundingBox(new AxisAlignedBB(x, y, z, x + 1.0, y + 1.0, z + 1.0));
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glDepthMask(true);
		GL11.glDisable(GL11.GL_BLEND);
	}
	
    public static BufferedImage toBufferedImage(Image img) {
        if (img instanceof BufferedImage) {
            return (BufferedImage) img;
        }
        BufferedImage bufferedImage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        Graphics2D bGr = bufferedImage.createGraphics();
        bGr.drawImage(img, 0, 0, null);
        bGr.dispose();
        return bufferedImage;
    }
    public static BufferedImage getImageFromURL(String url) {
        BufferedImage image = null;
        try {
            image = ImageIO.read(new URL(url));
        } catch (IOException e) {
            System.out.println(e);
        }
        return image;
    }
    public static String globToRegex(String glob) {
        StringBuilder builder = new StringBuilder("^");

        boolean literal = false;
        for (int i = 0; i < glob.length(); i++) {
          char at = glob.charAt(i);
          switch (at) {
            case '*':
            case '?':
              // this is an expression, end literal if started
              if (literal) {
                builder.append("\\E");
                literal = false;
              }
              // nasty
              switch (at) {
                case '*':
                  if (i - 1 < 0 || glob.charAt(i - 1) != '*') {
                    // * = match any multiple characters
                    builder.append(".*");
                  }
                  break;
                case '?':
                  // ? = match any single character
                  builder.append('.');
              }
              break;
            default:
              if (!literal) {
                builder.append("\\Q");
                literal = true;
              }builder.append(at);
          }
        }
        return glob;
    }
    public static String getBlockRegistryName(Block block) {
        return block.getRegistryName().toString();
      }
}
