package io.github.elliottleow.kabbalah;



import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent;

import javax.security.auth.login.LoginException;

import org.lwjgl.input.Keyboard;

import com.example.examplemod.autosave.SaveLoad;
import com.example.examplemod.clickgui.ClickGui;
import com.example.examplemod.module.Module;
import com.example.examplemod.module.ModuleManager;
import com.example.examplemod.module.modules.offense.LowBlockWarning;
import com.example.examplemod.proxy.*;
import com.example.examplemod.settings.SettingsManager;
import com.example.examplemod.ui.Hud;
import com.example.examplemod.util.*;

@Mod(modid = Reference.MOD_ID, name = Reference.NAME, version = Reference.VERSION)
public class ExampleMod
{
	
	
	
	public static SettingsManager settingsManager;
	public static ModuleManager moduleManager;
	public static Hud hud;
	public static ClickGui clickGui;
	public static SaveLoad saveLoad;
	
	
    public static final String MODID = "Kabbalah Client";
    public static final String VERSION = "1.0";
    
    @Instance
    public ExampleMod instance;
    
    @SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.COMMON_PROXY_CLASS)
    public static CommonProxy proxy;
    
    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
		
    }
    
    @EventHandler
    public void init(FMLInitializationEvent event) throws LoginException
    {
    	
		MinecraftForge.EVENT_BUS.register(instance);
		MinecraftForge.EVENT_BUS.register(new Hud());
		
		
		settingsManager = new SettingsManager();
		moduleManager = new ModuleManager();
		clickGui = new ClickGui();
		hud = new Hud();
		saveLoad = new SaveLoad();
		//Module m = new ModuleManager().getModule("Low Block Warning");
		//m.toggle();
		
		
;    }
    
    @EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
		
    }
    
    @SubscribeEvent
    public void key(KeyInputEvent e) {
    	if (Minecraft.getMinecraft().theWorld == null || Minecraft.getMinecraft().thePlayer == null) return;
    	
    	try {
    		if (Keyboard.isCreated()) {
    			if(Keyboard.getEventKeyState()) {
    				int keyCode = Keyboard.getEventKey();
    				if(keyCode <= 0) return;
    				for (Module m : moduleManager.modules) {
    					if(m.getKey() == keyCode && keyCode > 0) {
    						m.toggle();
    					}
    				}
    			}
    		}
    	} catch (Exception q) {q.printStackTrace();}
    }
}
