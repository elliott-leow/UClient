package me.elliottleow.kabbalah.module.modules.qol;

import org.lwjgl.input.Keyboard;

import me.elliottleow.kabbalah.Kabbalah;
import me.elliottleow.kabbalah.module.Category;
import me.elliottleow.kabbalah.module.Module;
import me.elliottleow.kabbalah.settings.Setting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.item.ItemFood;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.ArrowLooseEvent;
import net.minecraftforge.event.entity.player.PlayerUseItemEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.ItemPickupEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;

public class Announcer extends Module {
	//add bedwars shopkeeper buy notifs
	public Announcer() {
		super("Announcer", "Announces things in chat when certain events happen", Category.QoL);
		Kabbalah.settingsManager.rSetting(new Setting("Specific Item Names", this, true));
		Kabbalah.settingsManager.rSetting(new Setting("Ticks Before Announcing", this, 400, 1, 2400, true));
		Kabbalah.settingsManager.rSetting(new Setting("Random Ticks", this, false));
		Kabbalah.settingsManager.rSetting(new Setting("Client Side", this, false));
		Kabbalah.settingsManager.rSetting(new Setting("All Chat", this, false));
		Kabbalah.settingsManager.rSetting(new Setting("Mining", this, true));
		Kabbalah.settingsManager.rSetting(new Setting("Placing", this, true));
		Kabbalah.settingsManager.rSetting(new Setting("Eating", this, true));
		Kabbalah.settingsManager.rSetting(new Setting("Shooting", this, true));
		Kabbalah.settingsManager.rSetting(new Setting("Item Drop", this, true));
		Kabbalah.settingsManager.rSetting(new Setting("Item Pickup", this, true));
		Kabbalah.settingsManager.rSetting(new Setting("Killing", this, true));
		Kabbalah.settingsManager.rSetting(new Setting("Zion on top!", this, true));
		Kabbalah.settingsManager.rSetting(new Setting("Racist", this, false));
		this.setKey(Keyboard.KEY_X);
	}
	
	protected static final Minecraft mc = Minecraft.getMinecraft();
	int ticks = 0;
	int blocksbroken = 0;
	int blocksplaced = 0;
	int foodeaten = 0;
	int arrowsshot = 0;
	int itemsdropped = 0;
	int itemspicked = 0;
	int entitieskilled = 0;
	String blockbroken;
	String blockplaced;
	String foodtype;
	String itemdropped;
	String itempicked;
	String entitykilled;
    String[] endstrings = new String[] {" WINNER!", "1st Place - ", "Winner: ", " - Damage Dealt - ", "Winning Team - ", "1st - ", "Winners: ", "Winning Team: ", " won the game!", "1st Place: ", "Last team standing!", "Winner #1 (", "Top Survivors", "1st Killer - " };
	
	
	@SubscribeEvent
	public void onTick(TickEvent.PlayerTickEvent event) {
		if(event.phase != Phase.START) return;
		int ticklimit = (int) Kabbalah.settingsManager.getSettingByName(this, "Ticks Before Announcing").getValDouble();
		if (ticks==0 && Kabbalah.settingsManager.getSettingByName(this, "Random Ticks").getValBoolean()) {
			ticklimit = (int) Math.random()*10000 + 40;
		}
		ticks+=1;
		if(ticks>=ticklimit) {
			ticks=0;
			Announce();
		}
	}
	
	@SubscribeEvent
	public void onMine(BlockEvent.HarvestDropsEvent event) {
		if (event.harvester!=null) {
			if (event.harvester.getUniqueID()==mc.thePlayer.getUniqueID()) {
				if (Kabbalah.settingsManager.getSettingByName(this, "Mining").getValBoolean()) {
					blocksbroken+=1;
					if (Kabbalah.settingsManager.getSettingByName(this, "Specific Item Names").getValBoolean()) {
						blockbroken = event.drops.get(0).getDisplayName();
					}
					else {
						blockbroken = "blocks";
						if (Kabbalah.settingsManager.getSettingByName(this, "Racist").getValBoolean()) blockbroken="nganga";
					}
				}
			}
		}
	}
	
	@SubscribeEvent
	public void onPlace(BlockEvent.PlaceEvent event) {
		if (event.player!=null) {
			if (event.player.getUniqueID()==mc.thePlayer.getUniqueID()) {
				if (Kabbalah.settingsManager.getSettingByName(this, "Placing").getValBoolean()) {
					blocksplaced+=1;
					if (Kabbalah.settingsManager.getSettingByName(this, "Specific Item Names").getValBoolean()) {
						blockplaced = mc.theWorld.getBlockState(event.pos).getBlock().getLocalizedName().toString();
					}
					else {
						blockplaced = "blocks";
						if (Kabbalah.settingsManager.getSettingByName(this, "Racist").getValBoolean()) blockplaced="nganga";
					}
				}
			}
		}
	}
	
	@SubscribeEvent
	public void onUse(PlayerUseItemEvent.Finish event) {
		if (event.entity!=null) {
			if (event.entityPlayer==mc.thePlayer) {
				if (Kabbalah.settingsManager.getSettingByName(this, "Eating").getValBoolean() && event.item.getItem() instanceof ItemFood) {
					foodeaten+=1;
					if (Kabbalah.settingsManager.getSettingByName(this, "Specific Item Names").getValBoolean()) {
						foodtype = event.item.getDisplayName();
					}
					else {
						foodtype = "food";
						if (Kabbalah.settingsManager.getSettingByName(this, "Racist").getValBoolean()) foodtype="nganga";
					}
				}
			}
		}
	}
	
	@SubscribeEvent
	public void onShoot(ArrowLooseEvent event) {
		if (event.entityPlayer!=null) {
			if (event.entityPlayer==mc.thePlayer) {
				if (Kabbalah.settingsManager.getSettingByName(this, "Shooting").getValBoolean()) {
					arrowsshot+=1;
				}
			}
		}
	}
	
	@SubscribeEvent
	public void onDrop(ItemTossEvent event) {
		if (event.player!=null) {
			if (event.player.getUniqueID()==mc.thePlayer.getUniqueID()) {
				if (Kabbalah.settingsManager.getSettingByName(this, "Item Drop").getValBoolean()) {
					if (Kabbalah.settingsManager.getSettingByName(this, "Specific Item Names").getValBoolean()) {
						itemdropped=event.entityItem.getEntityItem().getDisplayName();
					}
					else {
						itemdropped="items";
						if (Kabbalah.settingsManager.getSettingByName(this, "Racist").getValBoolean()) itemdropped="nganga";
					}
					itemsdropped+=1;
				}
			}
		}
	}
	
	@SubscribeEvent
	public void onPick(ItemPickupEvent event) {
		if (event.player.getUniqueID()==mc.thePlayer.getUniqueID()) {
			if (Kabbalah.settingsManager.getSettingByName(this, "Item Pickup").getValBoolean()) {
				if (Kabbalah.settingsManager.getSettingByName(this, "Specific Item Names").getValBoolean()) {
					itempicked=event.pickedUp.getEntityItem().getDisplayName();
				}
				else {
					itempicked="items";
					if (Kabbalah.settingsManager.getSettingByName(this, "Racist").getValBoolean()) itempicked="nganga";
				}
				itemspicked+=1;
			}
		}
	}
	
	@SubscribeEvent
	public void onKill(LivingDeathEvent event) {
		System.out.println(event.entity);
		if (event.source!=null) {
			if (event.source.getEntity() instanceof EntityOtherPlayerMP) {
				if (Kabbalah.settingsManager.getSettingByName(this, "Killing").getValBoolean()) {
					if (Kabbalah.settingsManager.getSettingByName(this, "Specific Item Names").getValBoolean()) {
						entitykilled=event.entity.getName();
					}
					else {
						entitykilled="entities";
						if (Kabbalah.settingsManager.getSettingByName(this, "Racist").getValBoolean()) entitykilled="nganga";
					}
					entitieskilled+=1;
				}
			}
		}
	}
	
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onClientChatReceived(ClientChatReceivedEvent event) {
		String msg = event.message.getUnformattedText();
		for (int i = 0; i < endstrings.length; i++) {
			if (msg.contains(endstrings[i])) {
				if (!Kabbalah.settingsManager.getSettingByName(this, "Client Side").getValBoolean()) {
					mc.thePlayer.sendChatMessage("/ac Zion on top!");
					break;
				}
				else {
					mc.thePlayer.addChatMessage(new ChatComponentText("Zion on top!"));
					break;
				}
			}
		}
	}
	
	public void Announce() {
		int Random = (int) (Math.random()*35);
		if (Random<=5 && blocksbroken>0) {
			if (Kabbalah.settingsManager.getSettingByName(this, "Mining").getValBoolean()) {
				postMessage("Mining");
				return;
			}
		}
		Random+=5;
		if (Random>5 && Random<=10 && blocksplaced>0) {
			if (Kabbalah.settingsManager.getSettingByName(this, "Placing").getValBoolean()) {
				postMessage("Placing");
				return;
			}
		}
		Random+=5;
		if (Random>10 && Random<=15 && foodeaten>0) {
			if (Kabbalah.settingsManager.getSettingByName(this, "Eating").getValBoolean()) {
				postMessage("Eating");
				return;
			}
		}
		Random+=5;
		if (Random>15 && Random<=20 && arrowsshot>0) {
			if (Kabbalah.settingsManager.getSettingByName(this, "Shooting").getValBoolean()) {
				postMessage("Shooting");
				return;
			}
		}
		Random+=5;
		if (Random>20 && Random<=25 && itemsdropped>0) {
			if (Kabbalah.settingsManager.getSettingByName(this, "Item Drop").getValBoolean()) {
				postMessage("Item Drop");
				return;
			}
		}
		Random+=5;
		if (Random>25 && Random<=30 && itemspicked>0) {
			if (Kabbalah.settingsManager.getSettingByName(this, "Item Pickup").getValBoolean()) {
				postMessage("Item Pickup");
				return;
			}
		}
		Random+=5;
		if (Random>30 && Random<=35 && entitieskilled>0) {
			if (Kabbalah.settingsManager.getSettingByName(this, "Killing").getValBoolean()) {
				postMessage("Killing");
				return;
			}
		}
		Random+=5;
		if (Random>35) {
			return;
		}
	}
	
	public void postMessage(String type) {
		if (Kabbalah.settingsManager.getSettingByName(this, "Client Side").getValBoolean()) {
			if (type=="Mining") {
				mc.thePlayer.addChatMessage(new ChatComponentText("I mined " + blocksbroken + " " + blockbroken + "!"));
			}
			if (type=="Placing") {
				mc.thePlayer.addChatMessage(new ChatComponentText("I placed " + blocksplaced + " " + blockplaced + "!"));
			}
			if (type=="Eating") {
				mc.thePlayer.addChatMessage(new ChatComponentText("I ate " + foodeaten + " " + foodtype + "!"));
			}
			if (type=="Shooting") {
				mc.thePlayer.addChatMessage(new ChatComponentText("I shot " + arrowsshot + " arrows!"));
			}
			if (type=="Item Drop") {
				mc.thePlayer.addChatMessage(new ChatComponentText("I dropped " + itemsdropped + " " + itemdropped + "!"));
			}
			if (type=="Item Pickup") {
				mc.thePlayer.addChatMessage(new ChatComponentText("I picked up " + itemspicked + " " + itempicked + "!"));
			}
			if (type=="Killing") {
				mc.thePlayer.addChatMessage(new ChatComponentText("I killed " + entitieskilled + " " + entitykilled + "! Clown down!"));
			}
		}
		else {
			if (Kabbalah.settingsManager.getSettingByName(this, "All Chat").getValBoolean()) {
				if (type=="Mining") {
					mc.thePlayer.sendChatMessage("/ac I mined " + blocksbroken + " " + blockbroken + "!");
				}
				if (type=="Placing") {
					mc.thePlayer.sendChatMessage("/ac I placed " + blocksplaced + " " + blockplaced + "!");
				}
				if (type=="Eating") {
					mc.thePlayer.sendChatMessage("/ac I ate " + foodeaten + " " + foodtype + "!");
				}
				if (type=="Shooting") {
					mc.thePlayer.sendChatMessage("/ac I shot " + arrowsshot + " arrows!");
				}
				if (type=="Item Drop") {
					mc.thePlayer.sendChatMessage("/ac I dropped " + itemsdropped + " " + itemdropped + "!");
				}
				if (type=="Item Pickup") {
					mc.thePlayer.sendChatMessage("/ac I picked up " + itemspicked + " " + itempicked + "!");
				}
				if (type=="Killing") {
					mc.thePlayer.sendChatMessage("/ac I killed " + entitieskilled + " " + entitykilled + "! Clown down!");
				}
			}
			else {
				if (type=="Mining") {
					mc.thePlayer.sendChatMessage("I mined " + blocksbroken + " " + blockbroken + "!");
				}
				if (type=="Placing") {
					mc.thePlayer.sendChatMessage("I placed " + blocksplaced + " " + blockplaced + "!");
				}
				if (type=="Eating") {
					mc.thePlayer.sendChatMessage("I ate " + foodeaten + " " + foodtype + "!");
				}
				if (type=="Shooting") {
					mc.thePlayer.sendChatMessage("I shot " + arrowsshot + " arrows!");
				}
				if (type=="Item Drop") {
					mc.thePlayer.sendChatMessage("I dropped " + itemsdropped + " " + itemdropped + "!");
				}
				if (type=="Item Pickup") {
					mc.thePlayer.sendChatMessage("I picked up " + itemspicked + " " + itempicked + "!");
				}
				if (type=="Killing") {
					mc.thePlayer.sendChatMessage("I killed " + entitieskilled + " " + entitykilled + "! Clown down!");
				}
			}
		}
		blocksbroken = 0;
		blocksplaced = 0;
		foodeaten = 0;
		arrowsshot = 0;
		itemsdropped = 0;
		itemspicked = 0;
		entitieskilled = 0;
	}
	
}
