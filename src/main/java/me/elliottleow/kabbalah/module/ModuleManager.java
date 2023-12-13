package me.elliottleow.kabbalah.module;

import java.util.ArrayList;
import java.util.List;

import me.elliottleow.kabbalah.module.modules.combat.AimAssist;
import me.elliottleow.kabbalah.module.modules.combat.AutoBlockhit;
import me.elliottleow.kabbalah.module.modules.combat.AutoSprint;
import me.elliottleow.kabbalah.module.modules.combat.Autoclicker;
import me.elliottleow.kabbalah.module.modules.hud.CPS;
import me.elliottleow.kabbalah.module.modules.hud.ClickGUI;
import me.elliottleow.kabbalah.module.modules.hud.FPS;
import me.elliottleow.kabbalah.module.modules.hud.HUD;
import me.elliottleow.kabbalah.module.modules.hud.LowBlockWarning;
import me.elliottleow.kabbalah.module.modules.qol.Announcer;
import me.elliottleow.kabbalah.module.modules.qol.AutoBlocks;
import me.elliottleow.kabbalah.module.modules.qol.AutoTool;
import me.elliottleow.kabbalah.module.modules.qol.DoubleClicker;
import me.elliottleow.kabbalah.module.modules.qol.HideSpam;
import me.elliottleow.kabbalah.module.modules.qol.HighlightNons;
import me.elliottleow.kabbalah.module.modules.visual.BedESP;
import me.elliottleow.kabbalah.module.modules.visual.Chams;
import me.elliottleow.kabbalah.module.modules.visual.ESP;
import me.elliottleow.kabbalah.module.modules.visual.GoalPath;
import me.elliottleow.kabbalah.module.modules.visual.ObsidianESP;
import me.elliottleow.kabbalah.module.modules.visual.Tracers;

public class ModuleManager {
public static ArrayList<Module> modules;
	
	public ModuleManager() {
		(modules = new ArrayList<Module>()).clear();
		
		//----------Combat
		ModuleManager.modules.add(new AimAssist());
		ModuleManager.modules.add(new AutoBlockhit());
		ModuleManager.modules.add(new Autoclicker());
		//ModuleManager.modules.add(new AutoDodge());  //make it work pregame
		ModuleManager.modules.add(new AutoSprint());
		//ModuleManager.modules.add(new StatChecker()); //works but needs pregame info
		//----------HUD
		//ModuleManager.modules.add(new Cape()); //needs work
		ModuleManager.modules.add(new HUD());	
		ModuleManager.modules.add(new ClickGUI());
		ModuleManager.modules.add(new CPS());
		ModuleManager.modules.add(new FPS());
		//ModuleManager.modules.add(new KabbalahView()); //not coded yet
		ModuleManager.modules.add(new LowBlockWarning());
		//----------QOL
		ModuleManager.modules.add(new HideSpam());
		ModuleManager.modules.add(new Announcer());
		//this.modules.add(new AntiGhost());       //not that useful
		ModuleManager.modules.add(new AutoTool());
		ModuleManager.modules.add(new AutoBlocks());
		//ModuleManager.modules.add(new FastPlace());    //maybe risky
		ModuleManager.modules.add(new DoubleClicker());
		ModuleManager.modules.add(new HighlightNons());
		//----------Visual
		//this.modules.add(new BlockBreakESP());    //crash issue and hypixel bypass
		ModuleManager.modules.add(new Chams());
		ModuleManager.modules.add(new ESP());
		//this.modules.add(new HeadHitterESP());    //crash issue and hypixel bypass
		ModuleManager.modules.add(new Tracers());
		ModuleManager.modules.add(new BedESP());
		ModuleManager.modules.add(new ObsidianESP());
		ModuleManager.modules.add(new GoalPath());
		//ModuleManager.modules.add(new PathingTest());
		//ModuleManager.modules.add(new Trajectories()); //needs work
		//ModuleManager.modules.add(new ViewModel());      //pointless and doesnt work on items
		//ModuleManager.modules.add(new SovereignSword());  //really pointless
		
		
		
		//this.modules.add(new HippoAssist());
		//this.modules.add(new AutoLagback());
		//this.modules.add(new Blink());
		
		//Old module organization
		/*
		//client
		this.modules.add(new HUD());
		this.modules.add(new ClickGUI());
		this.modules.add(new KabbalahView());
		//player
		this.modules.add(new AntiGhost());
		this.modules.add(new ViewModel());
		this.modules.add(new AimAssist());
		this.modules.add(new AutoDodge());
		this.modules.add(new AutoSprint());
		this.modules.add(new StatChecker());
		this.modules.add(new AutoBlockhit());
		this.modules.add(new LowBlockWarning());
		//this.modules.add(new Cape());
		//offense
		this.modules.add(new BedESP());
		this.modules.add(new AutoTool());
		this.modules.add(new AutoBlocks());
		this.modules.add(new Announcer());
		this.modules.add(new ObsidianESP());
		this.modules.add(new Autoclicker());
		//this.modules.add(new Trajectories());
		this.modules.add(new DoubleClicker());
		//defense
		this.modules.add(new ESP());
		this.modules.add(new Chams());
		this.modules.add(new Tracers());
		this.modules.add(new BlockBreakESP());
		*/
		
	}
	
	public Module getModule(String name) {
		for (Module m : ModuleManager.modules) {
			if(m.getName().equalsIgnoreCase(name)) {
				return m;
			}
		}
		return null;
	}
	public ArrayList<Module> getModuleList() {
		return ModuleManager.modules;
	}
	
	public static List<Module> getModulesByCategory(Category c) {
		List<Module> modules = new ArrayList<Module>();
		
		for (Module m : ModuleManager.modules) {
			if (m.getCategory() == c) {
				modules.add(m);
			}
			
		}
		return modules;	
		
	}
}
