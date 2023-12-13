package me.elliottleow.kabbalah.module.modules.combat;

import org.lwjgl.input.Keyboard;

import me.elliottleow.kabbalah.Kabbalah;
import me.elliottleow.kabbalah.module.Category;
import me.elliottleow.kabbalah.module.Module;
import me.elliottleow.kabbalah.settings.Setting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.item.ItemSword;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;

public class AutoBlockhit extends Module {
	
	public AutoBlockhit() {
		super("Auto Blockhit", "Blocks when hitting with sword", Category.Combat);
		Kabbalah.settingsManager.rSetting(new Setting("Block Ticks", this, 2, 1, 10, true));
		this.setKey(Keyboard.KEY_J);
	}
	
	public static Minecraft mc = Minecraft.getMinecraft();
	int ticks=0;
	
	boolean timeron=false;
	
	@SubscribeEvent
	public void onTick(TickEvent.PlayerTickEvent event) {
		if(event.phase != Phase.START) return;
		if (mc.theWorld!=null && mc.thePlayer!=null) {
			if (!timeron) return;
			int ticklimit = (int) Kabbalah.settingsManager.getSettingByName(this, "Block Ticks").getValDouble();
			ticks+=1;
			if(ticks>=ticklimit) {
				ticks = 0;
				if (mc.thePlayer.getHeldItem()!=null) {
					if (mc.thePlayer.getHeldItem().getItem() instanceof ItemSword) {
						KeyBinding.setKeyBindState(mc.gameSettings.keyBindUseItem.getKeyCode(), false);
						Autoclicker.rcounter=1;
						Autoclicker.rcps=1;
						timeron=false;
					}
				}
			}
		}
	}
	
	@SubscribeEvent
	public void onAttack(AttackEntityEvent event) {
		if(mc.thePlayer.getHeldItem()!=null) {
			if(mc.thePlayer.getHeldItem().getItem() instanceof ItemSword) {
				timeron=true;
				KeyBinding.setKeyBindState(mc.gameSettings.keyBindUseItem.getKeyCode(), true);
				Autoclicker.rcounter=1;
				Autoclicker.rcps=1;
			}
		}
	}
}