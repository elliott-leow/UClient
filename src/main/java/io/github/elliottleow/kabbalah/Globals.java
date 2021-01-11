package io.github.elliottleow.kabbalah;

import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.client.FMLClientHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public interface Globals {
	  Minecraft MC = FMLClientHandler.instance().getClient();
}
