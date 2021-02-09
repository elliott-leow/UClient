package com.example.examplemod.module.modules.player;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Scanner;

import javax.annotation.processing.RoundEnvironment;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import com.example.examplemod.module.Category;
import com.example.examplemod.module.Module;
import com.example.examplemod.ui.Hud.ModuleComparator;
import com.example.examplemod.util.DynamicTextureWrapper;
import com.example.examplemod.util.Reference;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.ibm.icu.text.DecimalFormat;

import io.github.elliottleow.kabbalah.ExampleMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.ServerConfigurationManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class Info extends Module {
	public Info() {
		super("Info", "Gives game info.", Category.PLAYER);
		this.setKey(Keyboard.KEY_B);
	}
	
	@Override
	public void onEnable() {
		super.onEnable();
		
	}
	boolean hasStarted = false;
	String name = "quirple";
	//TextureManager renderEngine = Minecraft.getMinecraft().renderEngine;
	@SubscribeEvent
	public void renderOverlay(RenderGameOverlayEvent.Text event) {
		
		ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
			FontRenderer fr = Minecraft.getMinecraft().fontRendererObj;
			
			GL11.glPushMatrix();
			
			boolean blend = GL11.glIsEnabled(GL11.GL_BLEND);
			//GL11.glDisable(GL11.GL_BLEND);
			//ResourceLocation r = new ResourceLocation(Reference.MOD_ID, "textures/black-transparent-box.png");
			//Minecraft.getMinecraft().renderEngine.bindTexture(r);
			double scale = 1;
			int x = sr.getScaledWidth()/2;
			int y = sr.getScaledHeight()/2;
			
			double textScale = 0.7;
			GlStateManager.scale(textScale, textScale, textScale);
			
			//GL11.glPushMatrix();
			//GL11.glColor4f(0f, 0f, 0f, .6f);
			Gui.drawRect(x+160, y-2, x, y+87, 0x70000000);
			//GL11.glPopMatrix();
//			GL11.glEnable(GL11.GL_BLEND);
//			GL11.glColor4f(0f, 0f, 0f, .6f);
//			GL11.glRecti(420, 150, 150, 180);
//			GL11.glFlush();
			//Gui.drawRect(420, 150, 150, 180, 0xff000000);
			//Gui.drawScaledCustomSizeModalRect(x, y, 0, 0, (int)(120 *scale), (int)(75*scale), (int)(120*scale), (int)(85*scale), (int)(111*scale), (int)(75*scale));
			
			
			
			
			//GlStateManager.scale(textScale, textScale, textScale);
			fr.drawStringWithShadow("Information", x+ (int)(36*scale), y+2, 0xffffffff);
			fr.drawStringWithShadow("Opponent dist from mid:", x+ (int)(36*scale), y+17, 0xffffffff);
			EntityPlayer player = getPlayer(name) == null ? Minecraft.getMinecraft().thePlayer : getPlayer(name);
			
			BigDecimal bd = new BigDecimal(Math.sqrt((player.posX*player.posX) + ((player.posY-93)*(player.posY-93)) + (player.posZ*player.posZ))).setScale(2, RoundingMode.HALF_UP);
			fr.drawStringWithShadow("" + bd.doubleValue(), x+ (int)(36*scale), y+29, 0xffffffff);
			fr.drawStringWithShadow("Your dist from mid:", x+ (int)(36*scale), y+45, 0xffffffff);
			EntityPlayer self = Minecraft.getMinecraft().thePlayer;
			BigDecimal bd1 = new BigDecimal(Math.sqrt((self.posX*self.posX) + ((self.posY-93)*(self.posY-93)) + (self.posZ*self.posZ))).setScale(2, RoundingMode.HALF_UP);
			fr.drawStringWithShadow("" + bd1.doubleValue(), x+ (int)(36*scale), y+57, 0xffffffff);
try {
				
				Minecraft.getMinecraft().renderEngine.bindTexture(DynamicTextureWrapper.getTexture(new URL("https://mc-heads.net/body/" + name + "/128.png")));
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//int x = sr.getScaledWidth()/2;
			//int y = sr.getScaledHeight()/2;
			Gui.drawScaledCustomSizeModalRect(x + 4, y +2,(int) (0*scale), (int) (0*scale), (int)(110*scale), (int)(30*scale), (int)(30*scale),(int) (80*scale), (int) (110*scale),(int) (30*scale));
			
			if (blend) GL11.glEnable(GL11.GL_BLEND);
			GL11.glPopMatrix();
	}
	
	@SubscribeEvent
	public void onChat(ClientChatReceivedEvent e) {
		
		final String message = e.message.getUnformattedText();
	
		if (message.contains("Opponent:")) {
			System.out.println("recieved");
			final long startTime = System.currentTimeMillis();
			//String[] parts = s.split(" ");
			
          			
          			String s = message.replaceAll("\\s", "");
          			s= s.substring(s.lastIndexOf("Opponent:") + 9);
    				s = s.trim(); 
    				
    				s = s.substring(s.indexOf(']') +1);
    				// [VIP+] quirple
    				
				
					
						
    				System.out.println(s);
    				name =s;
    				hasStarted = true;
    				
	}
       
	

}
	public String getJson(String name) throws IOException {
    	URL url1 = new URL("https://api.mojang.com/users/profiles/minecraft/"+name);
		Scanner sc = new Scanner(url1.openStream());

        StringBuffer sb = new StringBuffer();
        while(sc.hasNext()) {
           sb.append(sc.next());
           
        }
        //Retrieving the String from the String Buffer object
        String result = sb.toString();
        //System.out.println(result);
        //Removing the HTML tags
        result = result.replaceAll("<[^>]*>", ""); //result is the json
        
        sc.close();
        
        return result;
    }
	
	public EntityPlayer getPlayer(String name){

	    ServerConfigurationManager server = MinecraftServer.getServer().getConfigurationManager();
	    List<EntityPlayer> pl = Minecraft.getMinecraft().theWorld.playerEntities;//server.getPlayerList();
	    
	    //ListIterator li = pl.listIterator();

	    for (EntityPlayer player : pl) {

	        EntityPlayer p = player;
	        if(p.getGameProfile().getName().equals(name)){

	            return p;

	        }

	    }
	    return null;

	}
	
}
