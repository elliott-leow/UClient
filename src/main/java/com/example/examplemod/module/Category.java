package com.example.examplemod.module;

public enum Category {
	DEFENSE("Defense"), OFFENSE("Offense"), PLAYER("Player"), CLIENT("Client");
	
	public String name; 
	public int moduleIndex;
	
	Category(String name) {
		this.name = name;
	}
}
