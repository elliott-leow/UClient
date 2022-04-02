package me.elliottleow.kabbalah.module;

import java.util.ArrayList;
import java.util.List;

import me.elliottleow.kabbalah.Kabbalah;
import me.elliottleow.kabbalah.module.modules.client.ClickGUI;
import me.elliottleow.kabbalah.module.modules.client.HUD;
//import me.elliottleow.kabbalah.module.modules.defense.BlockBreakESP;
import me.elliottleow.kabbalah.module.modules.defense.Chams;
import me.elliottleow.kabbalah.module.modules.defense.ESP;
import me.elliottleow.kabbalah.module.modules.defense.Tracers;
import me.elliottleow.kabbalah.module.modules.offense.AntiGhost;
import me.elliottleow.kabbalah.module.modules.offense.AutoBlocks;
import me.elliottleow.kabbalah.module.modules.offense.AutoTool;
import me.elliottleow.kabbalah.module.modules.offense.BedESP;
import me.elliottleow.kabbalah.module.modules.player.AutoDodge;
import me.elliottleow.kabbalah.module.modules.player.Cape;
//import me.elliottleow.kabbalah.module.modules.player.Cape;
//import me.elliottleow.kabbalah.module.modules.player.Cape;
import me.elliottleow.kabbalah.module.modules.player.DoubleClicker;
import me.elliottleow.kabbalah.module.modules.player.LowBlockWarning;
import me.elliottleow.kabbalah.module.modules.player.StatChecker;
import me.elliottleow.kabbalah.module.modules.player.ViewModel;

public class ModuleManager {
public ArrayList<Module> modules;
	
	public ModuleManager() {
		(modules = new ArrayList<Module>()).clear();
		
		//client
		this.modules.add(new HUD());
		this.modules.add(new ClickGUI());
		
		//player
		this.modules.add(new LowBlockWarning());
		//this.modules.add(new StatChecker());
		this.modules.add(new AutoDodge());
		this.modules.add(new AntiGhost());
		this.modules.add(new ViewModel());
		//this.modules.add(new Cape());
		
		//offense
		this.modules.add(new DoubleClicker());
		this.modules.add(new AutoBlocks());
		this.modules.add(new AutoTool());
		this.modules.add(new BedESP());
		
		//defense
		//this.modules.add(new BlockBreakESP());
		this.modules.add(new Tracers());
		this.modules.add(new Chams());
		this.modules.add(new ESP());
		this.modules.add(new Cape());
		//this.modules.add(new Sprint());
		//this.modules.add(new HippoAssist());
		//this.modules.add(new AutoLagback());
		//this.modules.add(new Trajectories());
		//this.modules.add(new Testing());
		
	}
	
	public Module getModule(String name) {
		for (Module m : this.modules) {
			if(m.getName().equalsIgnoreCase(name)) {
				return m;
			}
		}
		return null;
	}
	public ArrayList<Module> getModuleList() {
		return this.modules;
	}
	
	public static List<Module> getModulesByCategory(Category c) {
		List<Module> modules = new ArrayList<Module>();
		
		for (Module m : Kabbalah.moduleManager.modules) {
			if (m.getCategory() == c) {
				modules.add(m);
			}
			
		}
		return modules;	
		
	}
}
