package com.example.examplemod.module.modules.player;

import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;
import java.lang.Timer;
import java.lang.TimerTask;

import org.lwjgl.input.Keyboard;

import com.example.examplemod.module.Category;
import com.example.examplemod.module.Module;
import com.example.examplemod.settings.Setting;
import com.google.common.collect.Ordering;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import io.github.elliottleow.kabbalah.ExampleMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiPlayerTabOverlay;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent.ClientConnectedToServerEvent;

public class AutoDodge extends Module {
	public AutoDodge() {
		super("Auto Dodge", "Dodges players automatically depending on bridge stats.", Category.PLAYER);
		
		this.setKey(Keyboard.KEY_P);
		
		ExampleMod.settingsManager.rSetting(new Setting("Max Wins", this, 800, 10, 5000, true));
		ExampleMod.settingsManager.rSetting(new Setting("Max Winloss", this, 4, 1, 20, false));
		ExampleMod.settingsManager.rSetting(new Setting("Current Winstreak", this, 20, 5, 120, true));
		ExampleMod.settingsManager.rSetting(new Setting("Best Winstreak", this, 40, 10, 250, true));
		
	}
	
	@Override
	public void onEnable() {
		super.onEnable();
		
	}
	
	
	
	
	boolean cancelDodge = false;
	
	//{wins, winloss, currentws, bestws}
	

	
	@SubscribeEvent
	public void onChat(ClientChatReceivedEvent e) throws IOException {
		final String message = e.message.getUnformattedText();
		if (message.equals("The game starts in 1 second!")) {
			
			Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("Dodge determination cancelled due to game starting in 1 second"));
			cancelDodge = true;
			
		}
		
		if (message.contains(Minecraft.getMinecraft().thePlayer.getName() + " has joined (2/2)")) {
			cancelDodge = false;
			Thread thread = new Thread(){
				
				 public void run(){
					 try {
						 Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("Debug: thread has been run"));
				Collection<NetworkPlayerInfo> players = Minecraft.getMinecraft().getNetHandler().getPlayerInfoMap();
				ArrayList<String> names = new ArrayList<String>();
				for (NetworkPlayerInfo s : players) {
					names.add(s.getGameProfile().getName());
				}

				for (int i = 0; i < names.size(); i++) {
					if (names.get(i).equals(Minecraft.getMinecraft().thePlayer.getName())) {
						names.remove(i);
						break;
					}
				}
//				for (String n : names) {
//					Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("Debug: name in ArrayList - " + n));
//					
//				}
				if (names.size() > 2) {
					doDodge();
					Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("\2472Dodged due to player list being greater than 2."));
				}
				//add auto dodge maps
				if (names.size() == 2) {
					
					
						JsonObject json = new JsonParser().parse(getJson(names.get(0))).getAsJsonObject();
						JsonObject json1 = new JsonParser().parse(getJson(names.get(1))).getAsJsonObject();
						
						
						if (json.get("player").isJsonNull() && (json1.get("player").isJsonNull())) {
							doDodge();
							Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("\2472Dodged due to nicks (Both names in arraylist are not players."));
							
						}else if (!json.get("player").isJsonNull() && !json1.get("player").isJsonNull()) {
							
							Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("\2472Both players are real, checking accounts to determine dodge: " +json.get("player").getAsJsonObject().get("playername").getAsString() + " and " + json1.get("player").getAsJsonObject().get("playername").getAsString()));
							if (shouldDodge(getStats(json))) {
								doDodge();
								return; 
							} else {
								Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("Will not be dodging " + json.get("player").getAsJsonObject().get("playername").getAsString()));
							}
						
							if (shouldDodge(getStats(json1))) {
								doDodge();
								return;
							} else {
								Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("Will not be dodging " + json1.get("player").getAsJsonObject().get("playername").getAsString()));
							}
						
						} else if ((json.get("player").isJsonNull() && !json1.get("player").isJsonNull()) || !(json.get("player").isJsonNull() && json1.get("player").isJsonNull())) {
							
							Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("Dodging due to unusual circumstances, if this keeps happening let a developer know."));
						doDodge();
						return;
						}
					
				}
				if (names.size() == 1) {
					Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("\2472Detected player to determine dodge: " + names.get(0)));
					JsonObject json = new JsonParser().parse(getJson(names.get(0))).getAsJsonObject();
					if (shouldDodge(getStats(json))) {
						doDodge();
					} else if (json.get("player").isJsonNull()) {
						Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("Nick detected, will dodge"));
						doDodge();
					}else {
						Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("Will not be dodging " + json.get("player").getAsJsonObject().get("playername").getAsString()));
					}
				}
					 }catch (JsonSyntaxException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
				 }
					 
			};
			
			thread.start();
		}
		if (message.contains("has joined") && message.contains("(2/2)")) {
			cancelDodge = false;
			apiTimeout();
			Thread thread = new Thread(){
				
				 public void run(){
					 try {
			String player = message.substring(0,message.indexOf("has joined"));
	        player = player.trim();
			if (!player.equals(Minecraft.getMinecraft().thePlayer.getName())) {
				
				Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("\2472Detected player to determine dodge: " + player));
				JsonObject json;
				
					json = new JsonParser().parse(getJson(player)).getAsJsonObject();
				
				if (shouldDodge(getStats(json))) {
					doDodge();
					return;
				} else if (json.get("player").isJsonNull()) {
					Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("Nick detected, will dodge"));
					doDodge();
				}else {
					Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("Will not be dodging " + json.get("player").getAsJsonObject().get("playername").getAsString()));
				}
			}
				}  catch (JsonSyntaxException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			
			};
			thread.start();
		}
			
		
		
	}
	
	public int[] getStats(JsonObject json) {
		//{wins, winloss, currentws, bestws}
		int current_winstreak = 0; 
		int wins = 0;
		
		int duels_losses = 0;
		int doubles_losses = 0;
		int fours_losses = 0;
		int fourtwo_losses = 0;
		int fourthree_losses = 0;
		int best_winstreak =0;
		int losses = 1;
		try {
			current_winstreak = json.get("player").getAsJsonObject().get("stats").getAsJsonObject().get("Duels").getAsJsonObject().get("current_bridge_winstreak").getAsInt();
		}  catch (Exception e) {
			System.out.println(e);
		}
  		try {
  			wins = json.get("player").getAsJsonObject().get("achievements").getAsJsonObject().get("duels_bridge_wins").getAsInt(); 
  		} catch (Exception e) {  
			System.out.println(e);
		}
		try {
		duels_losses = json.get("player").getAsJsonObject().get("stats").getAsJsonObject().get("Duels").getAsJsonObject().get("bridge_duel_losses").isJsonNull() ? 0 : json.get("player").getAsJsonObject().get("stats").getAsJsonObject().get("Duels").getAsJsonObject().get("bridge_duel_losses").getAsInt();
		} catch (Exception e) {
			
		}
		try {
		doubles_losses = json.get("player").getAsJsonObject().get("stats").getAsJsonObject().get("Duels").getAsJsonObject().get("bridge_doubles_losses").isJsonNull() ? 0 : json.get("player").getAsJsonObject().get("stats").getAsJsonObject().get("Duels").getAsJsonObject().get("bridge_doubles_losses").getAsInt();
		} catch (Exception e) {
			System.out.println(e);
		}
		try {
		fours_losses = json.get("player").getAsJsonObject().get("stats").getAsJsonObject().get("Duels").getAsJsonObject().get("bridge_four_losses").isJsonNull() ? 0 : json.get("player").getAsJsonObject().get("stats").getAsJsonObject().get("Duels").getAsJsonObject().get("bridge_four_losses").getAsInt();
		} catch (Exception e) {
			System.out.println(e);
		}
		try {
		fourtwo_losses = json.get("player").getAsJsonObject().get("stats").getAsJsonObject().get("Duels").getAsJsonObject().get("bridge_2v2v2v2_losses").isJsonNull() ? 0 : json.get("player").getAsJsonObject().get("stats").getAsJsonObject().get("Duels").getAsJsonObject().get("bridge_2v2v2v2_losses").getAsInt();
		} catch (Exception e) {
			System.out.println(e);
		}
		try {
		 fourthree_losses = json.get("player").getAsJsonObject().get("stats").getAsJsonObject().get("Duels").getAsJsonObject().get("bridge_3v3v3v3_losses").isJsonNull() ? 0 : json.get("player").getAsJsonObject().get("stats").getAsJsonObject().get("Duels").getAsJsonObject().get("bridge_3v3v3v3_losses").getAsInt();
		} catch (Exception e) {
			System.out.println(e);
		} 
		try {
		best_winstreak = json.get("player").getAsJsonObject().get("stats").getAsJsonObject().get("Duels").getAsJsonObject().get("best_bridge_winstreak").getAsInt();
		} catch (Exception e) {
			System.out.println(e);
		}
		losses = duels_losses + doubles_losses + fours_losses + fourtwo_losses + fourthree_losses;
		if (losses == 0) losses = 1;
		return new int[] {wins, (wins/losses) +1, current_winstreak, best_winstreak};
		
	}
	
	public String getJson(String name) throws IOException {
		 
		
    	URL url1 = new URL("https://api.hypixel.net/player?key=5668d932-38bf-4611-a98c-d3690338bcc6&name="+name);
		Scanner sc = new Scanner(url1.openStream());

        StringBuffer sb = new StringBuffer();
        while(sc.hasNext()) {
           sb.append(sc.next());
           
        }

        String result = sb.toString();

        result = result.replaceAll("<[^>]*>", "");
        
        sc.close();
        return result;
		
        
    }
	
	public boolean shouldDodge(int[] nums) {
		boolean dodge = false;
		final int[] threshold = new int[] {(int)ExampleMod.settingsManager.getSettingByName(this, "Max Wins").getValDouble(), (int)ExampleMod.settingsManager.getSettingByName(this, "Max Winloss").getValDouble(), (int)ExampleMod.settingsManager.getSettingByName(this, "Current Winstreak").getValDouble(), (int)ExampleMod.settingsManager.getSettingByName(this, "Best Winstreak").getValDouble()};
		final String[] stats = new String[] {"Wins", "Winloss" , "Current Winstreak", "Best Winstreak"};
		for (int i = 0; i < nums.length; i++) {
			if (nums[i] > threshold[i]) {
				dodge = true;
				Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(stats[i] + " over " + threshold[i] + ". Will dodge."));
			}
		}
		return dodge;
		
	}
	TimerTask disconnect = new TimerTask() {
		public void run() {
				System.out.println("Disconnecting");
			}
	}
					 Timer timer = new Timer("Timer");
					 timer.schedule(disconnect, 4000);
	public void apiTimeout() {
		Thread thread = new Thread(){
				
				 public void run(){
					 
				 }
		}
		thread.start();
	}
	public void doDodge() {
		if (cancelDodge == true) {
			Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("CANCELLING DODGE NOW, YOU WILL HAVE TO PLAY THIS GAME"));
			cancelDodge = false;
			return;
		} else {
			Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("ok dodging"));
			Minecraft.getMinecraft().thePlayer.sendChatMessage("/l");
		}
	}
}
