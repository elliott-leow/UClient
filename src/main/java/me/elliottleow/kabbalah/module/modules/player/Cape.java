package me.elliottleow.kabbalah.module.modules.player;

import org.lwjgl.input.Keyboard;

import me.elliottleow.kabbalah.api.util.CapeLayer;
import me.elliottleow.kabbalah.module.Category;
import me.elliottleow.kabbalah.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EnumPlayerModelParts;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class Cape extends Module {
	public Cape() {
		super("Cape", "Custom cape for Kabbalah masters", Category.PLAYER);
		this.setKey(Keyboard.KEY_M);
	}
	
	public static String theurl = "https://i.imgur.com/99VzNNb.png";
	
    //String theurl = "https://i.pinimg.com/originals/75/1b/c6/751bc6c85d4dfd1763c6233e453647f4.png";
    //String theurl = "https://i.ibb.co/dMyc5pT/cape-img.png";
	
    private Minecraft mc = Minecraft.getMinecraft();

    @SubscribeEvent
    public void onRender(RenderPlayerEvent.Pre event) {
        if (!(event.entity instanceof EntityPlayer)) {
        	return;
        }
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