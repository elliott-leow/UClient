package me.elliottleow.kabbalah.module;

public enum Category {
	HUD("HUD"), QoL("QoL"), Visual("Visual"), Combat("Combat");
	
	public String name; 
	public int moduleIndex;
	
	Category(String name) {
		this.name = name;
	}
}
