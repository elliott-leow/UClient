package me.elliottleow.kabbalah.module;

public enum Category {
	DEFENSE("Defense"), OFFENSE("Offense"), PLAYER("Player"), CLIENT("Client");
	
	public String name; 
	public int moduleIndex;
	
	Category(String name) {
		this.name = name;
	}
}
