package me.elliottleow.kabbalah.api.util;

import me.elliottleow.kabbalah.Kabbalah;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EnumPlayerModelParts;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EntityWorldJoinListener {

    private Minecraft mc = Minecraft.getMinecraft();
    boolean caperendered=false;

    @SubscribeEvent
    public void onEntityJoinWorld(EntityJoinWorldEvent event) {
    	if (!(event.entity instanceof EntityPlayer)) return;
        if (!Kabbalah.moduleManager.getModule("Cape").isToggled()) return; 
        if (event.entity.getPersistentID().equals(mc.getSession().getProfile().getId())) {
        	renderCape();
        }
        
    }
        
    private void renderCape() {
        Minecraft.getMinecraft().gameSettings.setModelPartEnabled(EnumPlayerModelParts.CAPE, true);
        for (RenderPlayer render : Minecraft.getMinecraft().getRenderManager().getSkinMap().values()) {
            render.addLayer(new CapeLayer(render));
        }
    }
}