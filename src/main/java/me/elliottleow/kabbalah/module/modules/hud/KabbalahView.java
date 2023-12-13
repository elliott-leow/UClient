package me.elliottleow.kabbalah.module.modules.hud;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import me.elliottleow.kabbalah.module.Category;
import me.elliottleow.kabbalah.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class KabbalahView extends Module {

	public KabbalahView() {
		super("Kabbalah View", "Shows fellow kabbalah users", Category.HUD);
		this.setKey(Keyboard.KEY_COMMA);
	}
	
	//https://github.com/dries007/HoloInventory/blob/1.8.9/src/main/java/net/dries007/holoInventory/client/ClientEventHandler.java
	
	protected static final Minecraft mc = Minecraft.getMinecraft();
	public static ResourceLocation star = new ResourceLocation("kab/textures/star.png");
    public static Entity nametagEntity;

	
	@SubscribeEvent
	public void onRender(RenderLivingEvent.Specials.Pre<EntityLivingBase> event) {
		if (mc.thePlayer!=null && mc.theWorld!=null) {
			if (event.entity instanceof EntityPlayer) {
				EntityPlayer player = (EntityPlayer) event.entity;
				GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.75F);
		        GL11.glEnable(GL11.GL_BLEND);
		        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
				renderStar(player);
			}
		}
	}


	public void renderStar(EntityPlayer player) {
		if (player.getCustomNameTag().contains("[ZION]")) {
			String name = "✡ " + player.getName();
			System.out.println(name);
		}
	}
	
	/*public static void drawNametag (double x, double y, double z, String[] text, Color color, int type) {
		double dist=mc.thePlayer.getDistance(x,y,z);
		double scale = 1, offset = 0;
		int start=0;
		switch (type) {
			case 0:
				scale=dist/20*Math.pow(1.2589254,0.1/(dist<25?0.5:2));
				scale=Math.min(Math.max(scale,.5),5);
				offset=scale>2?scale/2:scale;
				scale/=40;
				start=10;
				break;
			case 1:
				scale=-((int)dist)/6.0;
				if (scale<1) scale=1;
				scale*=2.0/75.0;
				break;
			case 2:
				scale=0.0018+0.003*dist;
				if (dist<=8.0) scale=0.0245;
				start=-8;
				break;
		}
		GlStateManager.pushMatrix();
		GlStateManager.translate(x-mc.getRenderManager().viewerPosX,y+offset-mc.getRenderManager().viewerPosY,z-mc.getRenderManager().viewerPosZ);
		GlStateManager.rotate(-mc.getRenderManager().playerViewY,0,1,0);
		GlStateManager.rotate(mc.getRenderManager().playerViewX,mc.gameSettings.thirdPersonView==2?-1:1,0,0);
		GlStateManager.scale(-scale,-scale,scale);
		if (type == 2) {
			double width = 0;
			Color color = new Color (0,0,0);
			for (int i = 0; i < text.length; i++) {
				double w= mc.fontRendererObj.getStringWidth(text[i])/2;
				if (w > width) {
					width = w;
				}
			}
			//drawBorderedRect(-width - 1, -mc.fontRendererObj.FONT_HEIGHT, width + 2,1,1.8f);
		}
		GlStateManager.enableTexture2D();
		for (int i=0;i<text.length;i++) {
			mc.fontRendererObj.drawStringWithShadow(text[i],-mc.fontRendererObj.getStringWidth(text[i])/2,i*(mc.fontRendererObj.FONT_HEIGHT+1)+start,color);
		}
		GlStateManager.disableTexture2D();
		if (type!=2) {
			GlStateManager.popMatrix();
		}
	}*/


	/*public static void drawBorderedRect (double x, double y, double x1, double y1, float lineWidth) {
		Tessellator tessellator = Tessellator.getInstance();
		//color of inside
		GL11.glColor4f(0.0f,0.4f,0.0f,0.3f);
		tessellator.startDrawingQuads();
		bufferbuilder.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION);
		bufferbuilder.pos(x,y1,0).endVertex();
		bufferbuilder.pos(x1,y1,0).endVertex();
		bufferbuilder.pos(x1,y,0).endVertex();
		bufferbuilder.pos(x,y,0).endVertex();
		tessellator.draw();
		//color of border
		GL11.glColor4f(0.0f,0.0f,0.0f,0.2f);
		GL11.glLineWidth(lineWidth);
		bufferbuilder.begin(GL11.GL_LINE_STRIP, DefaultVertexFormats.POSITION);
		bufferbuilder.pos(x,y,0).endVertex();
		bufferbuilder.pos(x,y1,0).endVertex();
		bufferbuilder.pos(x1,y1,0).endVertex();
		bufferbuilder.pos(x1,y,0).endVertex();
		bufferbuilder.pos(x,y,0).endVertex();
		tessellator.draw();
	}*/
	
	/*public void renderStar(EntityLivingBase entity, double x, double y, double z) {
		GL11.glPushMatrix();
		GL11.glTranslatef((float) x, (float) y + entity.height, (float) z);
		GL11.glNormal3f(0.0f, 1.0f, 0.0f);
		GL11.glRotatef(-mc.getRenderManager().playerViewY, 0.0f, 1.0f, 0.0f);
		GL11.glRotatef(mc.getRenderManager().playerViewX, 1.0f, 0.0f, 0.0f);
		GL11.glDisable(2896);
		GL11.glDepthMask(false);
		GL11.glDisable(2929);
		GL11.glEnable(3042);
		OpenGlHelper.glBlendFunc(770, 771, 1, 0);
		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0f, 240.0f);
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		
		GL11.glPushMatrix();
		GL11.glTranslatef(0.0f, 0.5f, 0.0f);
		GL11.glScalef(-1.0f, -1.0f, 1.0f);
		float itemScale = 0.03f;
		GL11.glScalef(itemScale, itemScale, itemScale);
		mc.getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
		//drawicon
		GL11.glPopMatrix();
		
		GL11.glDisable(3042);
		GL11.glEnable(2929);
		GL11.glDepthMask(true);
		GL11.glEnable(2896);
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		GL11.glPopMatrix();
	}*/
	
	/*public static void drawNametagIndicator$5aa011b9(final matrixStack matrixStack, final Entity entity, final String str) {
        if (!(entity instanceof EntityPlayer)) {
            return;
        }
        final int stringWidth = Minecraft.getMinecraft().fontRendererObj.getStringWidth(str);
        final Color color = new Color(18, 153, 255);
        GL11.glEnable(GL11.GL_ALPHA);
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glDepthMask(false);
        final float x1 = (float)(-(stringWidth >> 1) - 11);
        final float x2 = (float)(-(stringWidth >> 1) - 1);
        GL11.glDisable(GL11.GL_DEPTH);
        GL11.glEnable(GL11.GL_BLEND);
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        final int backgroundOpacity = 64;
        Tessellator buffer = Tessellator.getInstance();
        buffer.pos(matrixStack, x1, -1.0, 0.0).color(0, 0, 0, backgroundOpacity).endVertex();
        buffer.pos(matrixStack, x1, 8.0, 0.0).color(0, 0, 0, backgroundOpacity).endVertex();
        buffer.pos(matrixStack, x2, 8.0, 0.0).color(0, 0, 0, backgroundOpacity).endVertex();
        buffer.pos(matrixStack, x2, -1.0, 0.0).color(0, 0, 0, backgroundOpacity).endVertex();
        buffer.draw();
    	GL11.glEnable(GL11.GL_DEPTH);
        GL11.glDepthMask(true);
        Star.drawStar(matrixStack, 6, (float)(-(stringWidth >> 1) - 6), 3.5f, color.getRGB());
        GL11.glEnable(GL11.GL_LIGHTING);
    }*/
	
	/*@SubscribeEvent
	public void onRender(RenderLivingEvent.Specials.Post<EntityLivingBase> event) {
		if (event.entity instanceof EntityPlayer) {
			GL11.glPushMatrix();
				GL11.glTranslatef((float) mc.getRenderManager().viewerPosX, 
						(float)mc.getRenderManager().viewerPosY + event.entity.height, 
						(float) mc.getRenderManager().viewerPosZ);
				GL11.glNormal3f(0.0f, 1.0f, 0.0f);
				GL11.glRotatef(-mc.getRenderManager().playerViewY, 0.0f, 1.0f, 0.0f);
				GL11.glRotatef(mc.getRenderManager().playerViewX, 1.0f, 0.0f, 0.0f);
				GL11.glDisable(2896);
				GL11.glDepthMask(false);
				GL11.glDisable(2929);
				GL11.glEnable(3042);
				OpenGlHelper.glBlendFunc(770, 771, 1, 0);
				OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0f, 240.0f);
				GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
				
				ResourceLocation star = new ResourceLocation("kab/textures/star.png");
				mc.getTextureManager().bindTexture(star);
				mc.getItemRenderer().renderItem(event.entity, mc.thePlayer.getHeldItem(), TransformType.NONE);
				GL11.glDisable(3042);
				GL11.glEnable(2929);
				GL11.glDepthMask(true);
				GL11.glEnable(2896);
				GL11.glPopMatrix();
		}
	}*/
	
}
