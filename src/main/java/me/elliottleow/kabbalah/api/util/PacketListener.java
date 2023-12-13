package me.elliottleow.kabbalah.api.util;

import me.elliottleow.kabbalah.module.modules.combat.AutoDodge;
import me.elliottleow.kabbalah.module.modules.combat.StatChecker;
import net.azurewebsites.thehen101.coremod.forgepacketmanagement.ForgePacketManagement;
import net.azurewebsites.thehen101.coremod.forgepacketmanagement.event.EventPacketQueued;
import net.azurewebsites.thehen101.coremod.forgepacketmanagement.event.PacketQueueListener;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;

public class PacketListener implements PacketQueueListener {
	
	@Override
	public void onPacketQueued(EventPacketQueued event) {
		System.out.println(event.getPacket().getClass());
	}
	
	@SubscribeEvent
	public void onTick(ClientTickEvent event) {
    	if(event.phase != Phase.END) return;    	
    	if (AutoDodge.ingame || StatChecker.ingame) {
    		ForgePacketManagement.INSTANCE.getPacketQueueManager().addListener(this);
    	}
    	if (!AutoDodge.ingame || !StatChecker.ingame) {
    		ForgePacketManagement.INSTANCE.getPacketQueueManager().removeListener(this);
    	}
	}
	  
}