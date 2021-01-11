package com.example.examplemod.module;

import java.util.ArrayList;
import java.util.List;

import com.example.examplemod.module.modules.client.ClickGUI;
import com.example.examplemod.module.modules.client.HUD;
import com.example.examplemod.module.modules.defense.Chams;
import com.example.examplemod.module.modules.defense.HippoAssist;

import com.example.examplemod.module.modules.offense.AutoLagback;
import com.example.examplemod.module.modules.offense.LowBlockWarning;
import com.example.examplemod.module.modules.offense.PrebowAim;
import com.example.examplemod.module.modules.offense.Trajectories;
import com.example.examplemod.module.modules.player.AutoDodge;
import com.example.examplemod.module.modules.player.DoubleClicker;
import com.example.examplemod.module.modules.player.Info;
import com.example.examplemod.module.modules.player.Sprint;
import com.example.examplemod.module.modules.player.StatChecker;
import com.example.examplemod.module.modules.player.Testing;
import com.example.examplemod.module.modules.player.WaifuESP;

import io.github.elliottleow.kabbalah.ExampleMod;

public class ModuleManager {
	
	public ArrayList<Module> modules;
	
	public ModuleManager() {
		(modules = new ArrayList<Module>()).clear();
		
		//player
		this.modules.add(new DoubleClicker());
		this.modules.add(new ClickGUI());
		this.modules.add(new StatChecker());
		this.modules.add(new AutoDodge());
		
		//this.modules.add(new Info());
		this.modules.add(new Chams());
		this.modules.add(new LowBlockWarning());
		this.modules.add(new HUD());
		//this.modules.add(new WaifuESP());
		
		
		//this.modules.add(new Sprint());
		
		//this.modules.add(new HippoAssist());
		//this.modules.add(new AutoLagback());
		//this.modules.add(new Trajectories());

		
		//this.modules.add(new Testing());
		//this.modules.add(new Tracers());
		
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
		
		for (Module m : ExampleMod.moduleManager.modules) {
			if (m.getCategory() == c) {
				modules.add(m);
			}
			
		}
		return modules;	
		
	}
	
	
}
