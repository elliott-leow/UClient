package me.elliottleow.kabbalah.api.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.entity.player.EnumPlayerModelParts;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

import java.util.UUID;

import me.elliottleow.kabbalah.Reference;
import me.elliottleow.kabbalah.module.modules.player.Cape;

public class CapeLayer implements LayerRenderer<AbstractClientPlayer> {

    private final RenderPlayer playerRenderer;

    public CapeLayer(RenderPlayer playerRendererIn) {
        this.playerRenderer = playerRendererIn;
    }

    @Override
    public void doRenderLayer(AbstractClientPlayer entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        final UUID playerUUID = Minecraft.getMinecraft().getSession().getProfile().getId();
        if (!entity.getPersistentID().equals(playerUUID)) return;
        if (!entity.isInvisible() && entity.isWearing(EnumPlayerModelParts.CAPE)) {
            float f9 = 0.14F;
            float f10 = 0.0F;
            if (entity.isSneaking()) {
                f9 = 0.1F;
                f10 = 0.09F;
            }
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            try {
                this.playerRenderer.bindTexture(CapeDownloader.DOWNLOADER.getCachedTexture());
            } catch (NullPointerException ignored) {
            }
            GlStateManager.pushMatrix();
            GlStateManager.translate(0.0F, f10, f9);
            double d0 = entity.prevChasingPosX + (entity.chasingPosX - entity.prevChasingPosX) * (double) partialTicks - (entity.prevPosX + (entity.posX - entity.prevPosX) * (double) partialTicks);
            double d1 = entity.prevChasingPosY + (entity.chasingPosY - entity.prevChasingPosY) * (double) partialTicks - (entity.prevPosY + (entity.posY - entity.prevPosY) * (double) partialTicks);
            double d2 = entity.prevChasingPosZ + (entity.chasingPosZ - entity.prevChasingPosZ) * (double) partialTicks - (entity.prevPosZ + (entity.posZ - entity.prevPosZ) * (double) partialTicks);
            float f = entity.prevRenderYawOffset + (entity.renderYawOffset - entity.prevRenderYawOffset) * partialTicks;
            double d3 = (double) MathHelper.sin(f * 0.017453292F);
            double d4 = (double) (-MathHelper.cos(f * 0.017453292F));
            float f1 = (float) d1 * 10.0F;
            f1 = MathHelper.clamp_float(f1, 3.0F, 32.0F);
            float f2 = (float) (d0 * d3 + d2 * d4) * 100.0F;
            float f3 = (float) (d0 * d4 - d2 * d3) * 100.0F;
            if (f2 < 0.0F) {
                f2 = 0.0F;
            }

            float f4 = entity.prevCameraYaw + (entity.cameraYaw - entity.prevCameraYaw) * partialTicks;
            f1 += MathHelper.sin((entity.prevDistanceWalkedModified + (entity.distanceWalkedModified - entity.prevDistanceWalkedModified) * partialTicks) * 6.0F) * 32.0F * f4;
            if (entity.isSneaking()) {
                f1 += 20.0F;
            }

            GlStateManager.rotate(5.0F + f2 / 2.0F + f1, 1.0F, 0.0F, 0.0F);
            GlStateManager.rotate(f3 / 2.0F, 0.0F, 0.0F, 1.0F);
            GlStateManager.rotate(-f3 / 2.0F, 0.0F, 1.0F, 0.0F);
            GlStateManager.rotate(0.0F, 0.0F, 1.0F, 0.0F);
            //GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
            this.playerRenderer.getMainModel().renderCape(0.0625F);
            GlStateManager.popMatrix();
        }
    }

    @Override
    public boolean shouldCombineTextures() {
        return false;
    }
    
    public static class CapeDownloader {

        public static final CapeDownloader DOWNLOADER = new CapeDownloader();

        private ResourceLocation cachedTexture = getCapeTexture();

        private ResourceLocation getCapeTexture() {
            ResourceLocation location = null;
            location = Minecraft.getMinecraft().getTextureManager().getDynamicTextureLocation(Reference.MOD_ID, new DynamicTexture(RenderUtils.getImageFromURL(Cape.theurl)));
            return location;
        }

        ResourceLocation getCachedTexture() {
            return cachedTexture;
        }

        public void updateCachedTexture() {
            cachedTexture = getCapeTexture();
        }

    }
}