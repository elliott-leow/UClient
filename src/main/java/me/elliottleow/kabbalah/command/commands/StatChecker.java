package me.elliottleow.kabbalah.command.commands;

import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.Scanner;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import me.elliottleow.kabbalah.api.util.HypixelUtil;
import me.elliottleow.kabbalah.command.Command;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;

public class StatChecker extends Command {

	public StatChecker() {
		super("Stats Checker", "Gets stats for player based on mode", "statcheck", "sc");
	}

	@Override
	public void onCommand(final String[] args, String command) {
		Thread thread = new Thread(){
			
     		 public void run(){
     			try {
     				String s = args[0];
     				String mode = args[1];
     				long startTime = System.currentTimeMillis();
     			//String s = message.replaceAll("\\s", "");
     			//s= s.substring(s.lastIndexOf("Opponent:") + 9);
				//s = s.trim(); 
				
				//s = s.substring(s.indexOf(']') +1);
				// [VIP+] quirple
				//System.out.println(s);
				
			//String json1 = "";
				Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("\2476Detected Player: " + s));
					
			
			JsonObject json;
			
				json = new JsonParser().parse(HypixelUtil.getJsonName(s)).getAsJsonObject();
				final long apiTime = System.currentTimeMillis() - startTime;
				Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("\247c---------\2476Stat Checking\247b----------"));
				
				if (json.get("player").isJsonNull()) {
					Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("The opponent " + s +" is not a player (If missing a letter then it is a nick)"));
	        		return;
	        	}
				
				//System.out.println(json.get("player").getAsJsonObject().get("stats").getAsJsonObject().get("Duels").getAsJsonObject().get("bridge_3v3v3v3_losses").isJsonNull());
				//System.out.println(json.get("player").getAsJsonObject().get("stats").getAsJsonObject().get("Duels").getAsJsonObject().get("bridge_3v3v3v3_losses").isJsonObject());
				
			
			String[] colors = new String[]{"\2478", "\247i", "\2476", "\2473", "\2472", "\2474", "\247e\247l", "\2475\247l"};	
			
				
			int current_winstreak = 0; 
			int wins = 0;
			
			int duels_losses = 0;
			int doubles_losses = 0;
			int fours_losses = 0;
			int fourtwo_losses = 0;
			int fourthree_losses = 0;
			int best_winstreak =0;
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
			int losses = duels_losses + doubles_losses + fours_losses + fourtwo_losses + fourthree_losses;
			long elapsedTime = System.currentTimeMillis() - startTime;
			Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("DEBUG: time to get data: " + elapsedTime + " ms; Time to get API: " + apiTime));
			if (current_winstreak > 249) {
				Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("\2472Current Winstreak: \2475\247l" +  + current_winstreak));
			} else {
			Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("\2472Current Winstreak: " + colors[(int) (current_winstreak/30)] + current_winstreak));
			}
			Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("\2472Best Winstreak: \2473" + best_winstreak));
			
			if (wins < 100) {
			Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("\2472Wins: \2478" + wins));
			}
			else if (wins < 250 && wins > 99) {
				Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("\2472Wins: \247i" + wins));
			}
			else if (wins < 500 && wins > 249) {
				Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("\2472Wins: \2476" + wins));
			}
			else if (wins < 1000 && wins > 499) {
				Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("\2472Wins: \2473" + wins));
			}
			else if (wins < 2000 && wins > 999) {
				Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("\2472Wins: \2472" + wins));
			}
			else if (wins < 5000 && wins > 1999) {
				Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("\2472Wins: \2474" + wins));
			}
			else if (wins < 10000 && wins > 4999) {
				Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("\2472Wins: \247e\247l" + wins));
			}
			if (wins > 9999) {
				Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("\2472Wins: \2475\247l" + wins));
			}
			Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("\2472Losses: \2473" + (losses)));
			DecimalFormat df = new DecimalFormat("#####.##");
			Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("\2472Winloss: \2473" + df.format((double)wins/(double)(losses))));
			Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("\247c----------\2476Check done\247b-----------"));
			
     			} catch (JsonSyntaxException e) {
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
