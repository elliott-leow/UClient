package com.example.examplemod.module.modules.player;

import java.lang.reflect.Method;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.Project;

import com.example.examplemod.module.Category;
import com.example.examplemod.module.Module;
import com.example.examplemod.settings.Setting;
import com.example.examplemod.util.entity.EntityUtils;

import io.github.elliottleow.kabbalah.ExampleMod;
import net.dv8tion.jda.api.hooks.SubscribeEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.Vec3;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import scala.actors.threadpool.Arrays;

public class WaifuESP extends Module {
	
	
	public WaifuESP() {
		super("WaifuESP", "Overlays Israel-chan uwu", Category.PLAYER);
		this.setKey(Keyboard.KEY_L);
		//ExampleMod.settingsManager.rSetting(new Setting("Width", this, 50, 1, 100, true));
		//ExampleMod.settingsManager.rSetting(new Setting("Height", this, 60, 1, 100, true));
		//ExampleMod.settingsManager.rSetting(new Setting("Only Clay", this, true));
		//ExampleMod.settingsManager.rSetting(new Setting("Min Blocks", this, 16, 1, 64, true));
		
		
	}
	
	@Override 
	public void onEnable() {
		super.onEnable();
	}
	
//	private boolean shouldDraw(EntityLivingBase entity) {
//	    return (!entity.equals(Minecraft.getMinecraft().thePlayer)
//	        && EntityUtils.isAlive(entity)
//	        && EntityUtils.isValidEntity(entity)
//	        && (EntityUtils.isPlayer(entity)));
//	  }
//	
//	@SubscribeEvent
//	  public void onRenderGameOverlayEvent(RenderGameOverlayEvent.Text event) {
//		for (Entity entity : Minecraft.getMinecraft().theWorld.loadedEntityList) {
//			if (EntityUtils.isLiving(entity) && shouldDraw((EntityLivingBase) entity)) {
//				EntityLivingBase living = (EntityLivingBase) (entity);
//		        Vec3 bottomVec = EntityUtils.getInterpolatedPos(living, event.partialTicks);
//		        Vec3 topVec =
//		            bottomVec.add(new Vec3(0, (entity.getEntityBoundingBox().maxY - entity.posY), 0));
//		        VectorUtils.ScreenPos top = VectorUtils._toScreen(topVec.xCoord, topVec.y, topVec.z);
//		        VectorUtils.ScreenPos bot = VectorUtils._toScreen(bottomVec.xCoord, bottomVec.y, bottomVec.z);
//						
//			}
//		}
//		ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
//		
//	}
}
