package me.elliottleow.kabbalah.module.modules.hud;

import me.elliottleow.kabbalah.Kabbalah;
import me.elliottleow.kabbalah.module.Category;
import me.elliottleow.kabbalah.module.Module;
import me.elliottleow.kabbalah.module.modules.combat.Autoclicker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;

public class CPS extends Module {

	public CPS() {
		super("CPS", "CPS Counter compatible with autoclicker", Category.HUD);
	}

	Minecraft mc = Minecraft.getMinecraft();
	
	boolean adown=false;
	boolean udown=false;
	
	@SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
		if (udown) udown=false;
		if (event.phase != Phase.START) return;
    	if (adown) adown=false;
    	if (mc.theWorld!=null && mc.thePlayer!=null) {
    		if (Kabbalah.moduleManager.getModule("Auto Clicker").isToggled()){
	    		if (Autoclicker.lticks>=10) {
	    			Autoclicker.lcps = Math.round(Autoclicker.lcounter*9f/(Autoclicker.lticks-1.5f));
	    			if (Autoclicker.lcounter==0 || Autoclicker.lcps<0) Autoclicker.lcps=0;
	    			Autoclicker.lticks=0;
	    			Autoclicker.lcounter/=2;
		    	}
	    		if (Autoclicker.rticks>=10) {
	    			Autoclicker.rcps = Math.round(Autoclicker.rcounter*9f/(Autoclicker.rticks-1.5f));
	    			if (Autoclicker.rcounter==0 || Autoclicker.rcps<0) Autoclicker.rcps=0;
	    			Autoclicker.rticks=0;
	    			Autoclicker.rcounter/=2;
		    	}
    		}
    		else {
    			if (Autoclicker.lticks==10) {
    				Autoclicker.lcps = Math.round(5*(Autoclicker.lcounter+1)/Autoclicker.lticks);
    				if (Autoclicker.lcounter==0 || Autoclicker.lcps<0) Autoclicker.lcps=0;
    				Autoclicker.lcounter/=Math.sqrt(2);
    			}
    			if (Autoclicker.lticks>=20) {
    				Autoclicker.lticks=1;
    				Autoclicker.lcounter/=Math.sqrt(2);
    			}
    			if (Autoclicker.rticks==10) {
    				Autoclicker.rcps = Math.round(5*(Autoclicker.rcounter+1)/Autoclicker.rticks);
    				if (Autoclicker.rcounter==0 || Autoclicker.rcps<0) Autoclicker.rcps=0;
    				Autoclicker.rcounter/=Math.sqrt(2);
    			}
    			if (Autoclicker.rticks>=20) {
    				Autoclicker.rticks=1;
    				Autoclicker.rcounter/=Math.sqrt(2);
    			}
    		}
    		Autoclicker.lticks++;
    		Autoclicker.rticks++;
    	}
    }
	
	@SubscribeEvent
    public void onMouseEvent(MouseEvent e) {
		if (FMLClientHandler.instance().isGUIOpen(GuiChat.class) || mc.currentScreen != null) return;
    	if (mc.theWorld!=null && mc.thePlayer!=null) {
			if (Kabbalah.moduleManager.getModule("Auto Clicker").isToggled() &&  Kabbalah.settingsManager.getSettingByName(Kabbalah.moduleManager.getModule("Auto Clicker"), "Mode").getValString().equals("Hold")) return;
    		if (Minecraft.getMinecraft().gameSettings.keyBindAttack.isKeyDown() && !adown) {
    			Autoclicker.lcounter++;
    			adown=true;
    		}
    		if (Minecraft.getMinecraft().gameSettings.keyBindUseItem.isKeyDown() && !udown) {
    			Autoclicker.rcounter++;
    			udown=true;
    		}
    	}
    }
	
}
