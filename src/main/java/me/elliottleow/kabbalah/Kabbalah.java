package me.elliottleow.kabbalah;


import javax.security.auth.login.LoginException;

import org.lwjgl.input.Keyboard;

import me.elliottleow.kabbalah.KabbalahPacketHandler;
import me.elliottleow.kabbalah.Reference;
import me.elliottleow.kabbalah.autosave.SaveLoad;
import me.elliottleow.kabbalah.clickgui.ClickGui;
import me.elliottleow.kabbalah.module.Module;
import me.elliottleow.kabbalah.module.ModuleManager;
import me.elliottleow.kabbalah.proxy.IProxy;
import me.elliottleow.kabbalah.settings.SettingsManager;
import me.elliottleow.kabbalah.ui.Hud;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent;

@Mod(modid = Reference.MOD_ID, name = Reference.NAME, version = Reference.VERSION)
public class Kabbalah {

	public static SettingsManager settingsManager;
	public static ModuleManager moduleManager;
	public static Hud hud;
	public static ClickGui clickGui;
	public static SaveLoad saveLoad;
	
	@Instance
	public Kabbalah instance;
	
	@SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.COMMON_PROXY_CLASS)
    public static IProxy proxy;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent e) {
	}
	
	@EventHandler
	public void init(FMLInitializationEvent e) throws LoginException, InterruptedException {
		settingsManager = new SettingsManager();
		moduleManager = new ModuleManager();
		clickGui = new ClickGui();
		hud = new Hud();
		saveLoad = new SaveLoad();
		
		MinecraftForge.EVENT_BUS.register(instance);
		MinecraftForge.EVENT_BUS.register(new Hud());
		MinecraftForge.EVENT_BUS.register(new KabbalahPacketHandler());
		proxy.init(e);
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent e) throws LoginException, InterruptedException {
	}
	
	@SubscribeEvent
    public void key(KeyInputEvent e) {
    	if (Minecraft.getMinecraft().theWorld == null || Minecraft.getMinecraft().thePlayer == null) return;
    	try {
    		if (Keyboard.isCreated()) {
    			if(Keyboard.getEventKeyState()) {
    				int keyCode = Keyboard.getEventKey();
    				if(keyCode <= 0) return;
    				for (Module m : ModuleManager.modules) {
    					if(m.getKey() == keyCode && keyCode > 0) {
							//if (Kabbalah.settingsManager.getSettingByName(Kabbalah.moduleManager.getModule("Click GUI"), "Output").getValBoolean()) Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.DARK_AQUA + "[Kabbalah Client]: " + m.getName() + " toggled"));
    						System.out.println("[Kabbalah Client]: " + m.getName());
    						m.toggle();
    					}
    				}
    			}
    		}
    	} catch (Exception q) {q.printStackTrace();}
    }
	
}
