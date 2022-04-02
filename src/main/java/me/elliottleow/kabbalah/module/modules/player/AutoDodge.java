package me.elliottleow.kabbalah.module.modules.player;

import org.lwjgl.input.Keyboard;

import me.elliottleow.kabbalah.Kabbalah;
import me.elliottleow.kabbalah.api.util.HypixelUtil;
import me.elliottleow.kabbalah.module.Category;
import me.elliottleow.kabbalah.module.Module;
import me.elliottleow.kabbalah.settings.Setting;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import net.minecraft.util.ChatComponentText;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;

public class AutoDodge extends Module {

	public static Minecraft mc = Minecraft.getMinecraft();
	
	boolean ingame = false;
	
	public AutoDodge() {
		super("Auto Dodge", "Dodges players automatically depending on bridge stats.", Category.PLAYER);
		this.setKey(Keyboard.KEY_P);
		Kabbalah.settingsManager.rSetting(new Setting("Max Wins", this, 200, 0, 5000, true));
		Kabbalah.settingsManager.rSetting(new Setting("Max Winloss", this, 2, 1, 20, false));
		Kabbalah.settingsManager.rSetting(new Setting("Current Winstreak", this, 10, 0, 120, true));
		Kabbalah.settingsManager.rSetting(new Setting("Best Winstreak", this, 20, 1, 500, true));
		ArrayList<String> Options = new ArrayList<String>();
		Options.add("classic");
		Options.add("bridge");
		Options.add("sumo");
		Options.add("overall");
		Kabbalah.settingsManager.rSetting(new Setting("Mode", this, "overall", Options));
	}
	
	@SubscribeEvent
	public void onChat(ClientChatReceivedEvent event) {
		final String message = event.message.getUnformattedText().toString();
		if (message.contains("has joined (2/2)!")) {
			ingame = true;
		}
	}
	//classic, bridge, sumo, overall
	
	@SubscribeEvent
	public void onRender(RenderPlayerEvent.Pre event) throws JsonSyntaxException, IOException, ExecutionException, NullPointerException {
		if (ingame) {
			if (!event.entityPlayer.getUniqueID().equals(mc.getSession().getProfile().getId())) {
				JsonObject json = new JsonParser().parse(HypixelUtil.getJsonUUID(event.entityPlayer.getUniqueID().toString())).getAsJsonObject();
				String mode = (String) Kabbalah.settingsManager.getSettingByName(this, "Mode").getValString();
				mc.thePlayer.addChatMessage(new ChatComponentText("Dodge mode: " + mode));
				if (json.get("player").isJsonNull()) {
					mc.thePlayer.addChatMessage(new ChatComponentText("Player is a nick, will dodge"));
					doDodge();
				}
				else if (shouldDodge(getStats(json, mode), mode)) {
					mc.thePlayer.addChatMessage(new ChatComponentText("Dodging " + json.get("player").getAsJsonObject().get("playername").getAsString()));
					doDodge();
				} 
				else {
					mc.thePlayer.addChatMessage(new ChatComponentText("Will not be dodging " + json.get("player").getAsJsonObject().get("playername").getAsString()));
				}
				ingame = false;
			}
		}
	}
	
	public boolean shouldDodge(int[] nums, String mode) {
		boolean dodge = false;
		int settingswins = (int) Kabbalah.settingsManager.getSettingByName(this, "Max Wins").getValDouble();
		int settingswinloss = (int) Kabbalah.settingsManager.getSettingByName(this, "Max Winloss").getValDouble();
		int settingscurrentws = (int) Kabbalah.settingsManager.getSettingByName(this, "Current Winstreak").getValDouble();
		int settingsbestws = (int) Kabbalah.settingsManager.getSettingByName(this, "Best Winstreak").getValDouble();
		final int[] threshold = new int[] {settingswins, settingswinloss, settingscurrentws, settingsbestws};
		final String[] stats = new String[] {"Wins", "Winloss" , "Current Winstreak", "Best Winstreak"};
		for (int i = 0; i < nums.length; i++) {
			if (nums[i] > threshold[i]) {
				dodge = true;
				mc.thePlayer.addChatMessage(new ChatComponentText(stats[i] + " over " + threshold[i] + " in " + mode + " stats, dodging"));
			}
		}
		return dodge;
	}
	
	public int[] getStats(JsonObject json, String mode) {
		int current_winstreak = 0; 
		int wins = 0;
		int duels_losses = 0;
		int doubles_losses = 0;
		int fours_losses = 0;
		int fourtwo_losses = 0;
		int fourthree_losses = 0;
		int best_winstreak = 0;
		int losses = 1;
		
		if (mode.equals("bridge")) {
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
		}
		
		if (mode.equals("sumo")) {
			try {
				current_winstreak = json.get("player").getAsJsonObject().get("stats").getAsJsonObject().get("Duels").getAsJsonObject().get("current_sumo_winstreak").getAsInt();
			}  catch (Exception e) {
				System.out.println(e);
			}
	  		try {
	  			wins = json.get("player").getAsJsonObject().get("stats").getAsJsonObject().get("Duels").getAsJsonObject().get("sumo_duel_wins").isJsonNull() ? 0 : json.get("player").getAsJsonObject().get("stats").getAsJsonObject().get("Duels").getAsJsonObject().get("sumo_duel_wins").getAsInt();
	  		} catch (Exception e) {  
				System.out.println(e);
			}
			try {
				duels_losses = json.get("player").getAsJsonObject().get("stats").getAsJsonObject().get("Duels").getAsJsonObject().get("sumo_duel_losses").isJsonNull() ? 0 : json.get("player").getAsJsonObject().get("stats").getAsJsonObject().get("Duels").getAsJsonObject().get("sumo_duel_losses").getAsInt();
			} catch (Exception e) {
				
			}
			try {
				best_winstreak = json.get("player").getAsJsonObject().get("stats").getAsJsonObject().get("Duels").getAsJsonObject().get("best_sumo_winstreak").getAsInt();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
		
		if (mode.equals("classic")) {
			try {
				current_winstreak = json.get("player").getAsJsonObject().get("stats").getAsJsonObject().get("Duels").getAsJsonObject().get("current_classic_winstreak").getAsInt();
			}  catch (Exception e) {
				System.out.println(e);
			}
	  		try {
	  			wins = json.get("player").getAsJsonObject().get("stats").getAsJsonObject().get("Duels").getAsJsonObject().get("classic_duel_wins").isJsonNull() ? 0 : json.get("player").getAsJsonObject().get("stats").getAsJsonObject().get("Duels").getAsJsonObject().get("classic_duel_wins").getAsInt();
	  		} catch (Exception e) {  
				System.out.println(e);
			}
			try {
				duels_losses = json.get("player").getAsJsonObject().get("stats").getAsJsonObject().get("Duels").getAsJsonObject().get("classic_duel_losses").isJsonNull() ? 0 : json.get("player").getAsJsonObject().get("stats").getAsJsonObject().get("Duels").getAsJsonObject().get("classic_duel_losses").getAsInt();
			} catch (Exception e) {
				
			}
			try {
				best_winstreak = json.get("player").getAsJsonObject().get("stats").getAsJsonObject().get("Duels").getAsJsonObject().get("best_classic_winstreak").getAsInt();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
		
		if (mode.equals("overall")) {
			try {
				current_winstreak = json.get("player").getAsJsonObject().get("stats").getAsJsonObject().get("Duels").getAsJsonObject().get("current_winstreak").getAsInt();
			}  catch (Exception e) {
				System.out.println(e);
			}
	  		try {
	  			wins = json.get("player").getAsJsonObject().get("stats").getAsJsonObject().get("Duels").getAsJsonObject().get("wins").isJsonNull() ? 0 : json.get("player").getAsJsonObject().get("stats").getAsJsonObject().get("Duels").getAsJsonObject().get("wins").getAsInt();
	  		} catch (Exception e) {  
				System.out.println(e);
			}
			try {
				duels_losses = json.get("player").getAsJsonObject().get("stats").getAsJsonObject().get("Duels").getAsJsonObject().get("losses").isJsonNull() ? 0 : json.get("player").getAsJsonObject().get("stats").getAsJsonObject().get("Duels").getAsJsonObject().get("losses").getAsInt();
			} catch (Exception e) {
				
			}
			try {
				best_winstreak = json.get("player").getAsJsonObject().get("stats").getAsJsonObject().get("Duels").getAsJsonObject().get("best_overall_winstreak").getAsInt();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
		losses = duels_losses + doubles_losses + fours_losses + fourtwo_losses + fourthree_losses;
		if (losses == 0) losses = 1;
		mc.thePlayer.addChatMessage(new ChatComponentText("Wins: "+wins));
		mc.thePlayer.addChatMessage(new ChatComponentText("Win/Loss: "+(wins/losses)));
		mc.thePlayer.addChatMessage(new ChatComponentText("Current WS: "+current_winstreak));
		mc.thePlayer.addChatMessage(new ChatComponentText("Best WS: "+best_winstreak));
		return new int[] {wins, (wins/losses) +1, current_winstreak, best_winstreak};
	}
	
	

	public void doDodge() {
		mc.thePlayer.addChatMessage(new ChatComponentText("ok dodging"));
		mc.thePlayer.sendChatMessage("/l");
		ingame=false;
	}
}
