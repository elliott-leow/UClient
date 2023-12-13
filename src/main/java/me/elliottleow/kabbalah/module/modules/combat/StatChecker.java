package me.elliottleow.kabbalah.module.modules.combat;

import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;

import javax.security.auth.login.LoginException;

import org.lwjgl.input.Keyboard;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import me.elliottleow.kabbalah.Kabbalah;
import me.elliottleow.kabbalah.module.Category;
import me.elliottleow.kabbalah.module.Module;
import me.elliottleow.kabbalah.settings.Setting;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class StatChecker extends Module {
	
	public static Minecraft mc = Minecraft.getMinecraft();
	
	public static boolean ingame = false;
	
	public StatChecker() {
		super("Stat Checker", "Checks player stats at the beginning of each game.", Category.Combat);
		this.setKey(Keyboard.KEY_M);
		ArrayList<String> Options = new ArrayList<String>();
		Options.add("classic");
		Options.add("bridge");
		Options.add("sumo");
		Options.add("overall");
		Kabbalah.settingsManager.rSetting(new Setting("Mode", this, "classic", Options));
		Kabbalah.settingsManager.rSetting(new Setting("Pregame Check", this, false));
		Kabbalah.settingsManager.rSetting(new Setting("Ignore Allies", this, false));
	}
	
	String playername;
	String displayname;
	
	@SubscribeEvent
	public void onChat(ClientChatReceivedEvent event) {
		final String message = event.message.getUnformattedText().toString();
		if (Kabbalah.settingsManager.getSettingByName(this, "Pregame Check").getValBoolean() && message.contains("has joined (2/2)!")) {
			ingame = true;
		}
		if (!Kabbalah.settingsManager.getSettingByName(this, "Pregame Check").getValBoolean() && message.contains("Opponent: ")) {
			ingame = true;
		}
	}
	
	@SubscribeEvent
	public void onRender(RenderPlayerEvent.Pre event) throws JsonSyntaxException, IOException, ExecutionException, NullPointerException {
		if (ingame) {
			if (!event.entityPlayer.getUniqueID().equals(mc.getSession().getProfile().getId())) {
				if ((Kabbalah.settingsManager.getSettingByName(this, "Ignore Allies").getValBoolean() && (event.entityPlayer.getCurrentArmor(1)!=mc.thePlayer.getCurrentArmor(1) && mc.thePlayer.getCurrentArmor(1)!=null)) || !Kabbalah.settingsManager.getSettingByName(this, "Ignore Allies").getValBoolean()) {
					JsonObject json = new JsonParser().parse(getJsonUUID(event.entityPlayer.getUniqueID().toString())).getAsJsonObject();
					String mode = (String) Kabbalah.settingsManager.getSettingByName(this, "Mode").getValString();
					playername = event.entityPlayer.getName();
					displayname = event.entityPlayer.getDisplayNameString();
					System.out.println(event.entityPlayer.getName() + " " + event.entityPlayer.getDisplayNameString());
					statcheck(json, mode);
					ingame = false;
				}
			}
		}
	}
	
	@SubscribeEvent
	public void onCommand(ServerChatEvent event) throws LoginException, InterruptedException, ArrayIndexOutOfBoundsException {	
		if (event.username.equals(mc.thePlayer.getName()) && event.message.startsWith(".")){
			String[] args = event.message.substring(".".length()).split(" ");
			if ((args[0].equals("sc") || args[0].equals("statcheck") || args[0].equals("statchecker") || args[0].equals("stats"))) {
				if (args.length!=3) {
					mc.thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.DARK_AQUA + "[Kabbalah Client] Incorrect usage (.sc user mode)"));
					event.setCanceled(true);
					return;
				}
				else {
					playername = args[1];
					displayname = args[1];
					statcheckcommand(args[1], args[2]);
					event.setCanceled(true);
					return;
				}
			} else {
				mc.thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.DARK_AQUA + "[Kabbalah Client] Command does not exist"));
				event.setCanceled(true);
				return;
			}
		}
	}
	
	public void statcheckcommand(String name, String mode) {
		JsonObject json = null;
		try {
			json = new JsonParser().parse(getJsonName(name)).getAsJsonObject();
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		statcheck(json, mode);
	}
	
	public void statcheck(JsonObject json, String mode) {
		if (json.get("player").isJsonNull()) {
			Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(displayname + " is nicked"));
    		return;
    	}
		
		mc.thePlayer.addChatMessage(new ChatComponentText("\247c---------\2476 Checking " + playername + "\247b ---------"));
		
		String[] colors = new String[]{"\2478", "\247i", "\2476", "\2473", "\2472", "\2474", "\247e\247l", "\2475\247l"};	
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
		if (current_winstreak > 249) {
			mc.thePlayer.addChatMessage(new ChatComponentText("\2472Current Winstreak: \2475\247l" +  + current_winstreak));
		} else {
		mc.thePlayer.addChatMessage(new ChatComponentText("\2472Current Winstreak: " + colors[(int) (current_winstreak/30)] + current_winstreak));
		}
		mc.thePlayer.addChatMessage(new ChatComponentText("\2472Best Winstreak: \2473" + best_winstreak));
		
		if (wins < 100) {
		mc.thePlayer.addChatMessage(new ChatComponentText("\2472Wins: \2478" + wins));
		} else if (wins < 250 && wins > 99) {
			mc.thePlayer.addChatMessage(new ChatComponentText("\2472Wins: \247i" + wins));
		} else if (wins < 500 && wins > 249) {
			mc.thePlayer.addChatMessage(new ChatComponentText("\2472Wins: \2476" + wins));
		} else if (wins < 1000 && wins > 499) {
			mc.thePlayer.addChatMessage(new ChatComponentText("\2472Wins: \2473" + wins));
		} else if (wins < 2000 && wins > 999) {
			mc.thePlayer.addChatMessage(new ChatComponentText("\2472Wins: \2472" + wins));
		} else if (wins < 5000 && wins > 1999) {
			mc.thePlayer.addChatMessage(new ChatComponentText("\2472Wins: \2474" + wins));
		} else if (wins < 10000 && wins > 4999) {
			mc.thePlayer.addChatMessage(new ChatComponentText("\2472Wins: \247e\247l" + wins));
		} if (wins > 9999) {
			mc.thePlayer.addChatMessage(new ChatComponentText("\2472Wins: \2475\247l" + wins));
		}
		mc.thePlayer.addChatMessage(new ChatComponentText("\2472Losses: \2473" + (losses)));
		DecimalFormat df = new DecimalFormat("#####.##");
		mc.thePlayer.addChatMessage(new ChatComponentText("\2472Winloss: \2473" + df.format((double)wins/(double)(losses))));
		int numofdashes=(("---------" + " Checking " + playername + " ---------").length()-(" Check finished ").length())/(2);
		String repeated = new String(new char[numofdashes]).replace("\0", "-");
		mc.thePlayer.addChatMessage(new ChatComponentText("\247c" + repeated + "\2476 Check finished \247b" + repeated));
		ingame = false;
	}
	
	public String getJsonUUID(String uuid) throws IOException {
    	URL url1 = new URL("https://api.hypixel.net/player?key=6efb6647-c609-40b7-9d95-adb08bd9878f&uuid="+uuid);
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
	
    public String getJsonName(String name) throws IOException {
    	URL url1 = new URL("https://api.hypixel.net/player?key=6efb6647-c609-40b7-9d95-adb08bd9878f&name="+name);
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
}
