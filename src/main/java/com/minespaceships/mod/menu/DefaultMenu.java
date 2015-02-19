package com.minespaceships.mod.menu;

import com.minespaceships.mod.overhead.CustomGuiChat;

public class DefaultMenu {
	
	private static Menu root;
	private static Menu control;
	private static Menu armour;
	private static Menu protection;
	
	private static Menu shield;
	
	public static void initMenu(CustomGuiChat terminal){
		root = new Menu("Main", terminal);
		control = new Menu("spaceshipcontrole", terminal);
		armour = new Menu("weapons", terminal);
		protection = new Menu("protection", terminal);
		shield = new Menu("shield", terminal);
		
		root.addSubMenu(control);
		root.addSubMenu(armour);
		root.addSubMenu(protection);
			protection.addSubMenu(shield);
	}
	
	public static Menu getRootMenu(){
		return root;
	}

}
