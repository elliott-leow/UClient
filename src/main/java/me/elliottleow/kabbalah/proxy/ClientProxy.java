package me.elliottleow.kabbalah.proxy;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

public class ClientProxy implements IProxy {

	@Override
	public void preInit(FMLPreInitializationEvent event) {
		
	}

	@Override
	public void init(FMLInitializationEvent event) {
		//MinecraftForge.EVENT_BUS.register(new PacketListener());
		//MinecraftForge.EVENT_BUS.register(new EntityWorldJoinListener());
	}

	@Override
	public void postInit(FMLPostInitializationEvent event) {
		
	}

	@Override
	public void serverStarting(FMLServerStartingEvent event) {		
	}

}
