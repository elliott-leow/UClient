package me.elliottleow.kabbalah.api.util;

import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class NametagUtil extends RenderPlayer {

	public NametagUtil(RenderManager renderManager, boolean useSmallArms) {
		super(renderManager, false);
	}

	@Override
	protected void renderOffsetLivingLabel(AbstractClientPlayer entityIn, double x, double y, double z, String str, float p_177069_9_, double p_177069_10_){
        return;
    }
	
	
	/*@SubscribeEvent
	public void renderName(PlayerEvent.NameFormat event) {
		NBTTagCompound tag = event.entityPlayer.getEntityData();
		System.out.println(tag);
		if(tag.hasKey("fakename")) {
			System.out.println(event.displayname);
		}
		else {
			event.setCanceled(true);
		}
	}*/
	
}
