package com.minespaceships.mod.menu;

import java.util.Scanner;

import com.minespaceships.mod.menu.Menu;

public class TestMain {
	
	private static Scanner scanner = new Scanner(System.in);
	private static String input;
	/**
	 * Is necessary to now if the DefaultMenu was initialise before.
	 */
	private static boolean runBefore;

	/**
	 * The root menu.
	 */
	private static Menu root;

	/**
	 * Menu for the spaceship navigation.
	 */
	private static Menu navigation;

	/*
	 * Menu for the spaceship armour.
	 */
	private static Menu armour;

	/**
	 * Menu for all protective devices of the spaceship.
	 */
	private static Menu protection;

	/**
	 * Menu if you need help
	 */
	private static Menu help;

	/**
	 * Menu for the spaceship shield.
	 */
	private static Menu shield;

	/**
	 * Menu which contains the ability to activate the shield
	 */
	private static Menu activateShield;

	/**
	 * Menu which contains the ability to disable the shield
	 */
	private static Menu disableShield;

	/**
	 * 
	 */
	private static Menu shiptoTarget;

	
	
	public static void main(String[] args){
		
		root = new Menu("Main");
		navigation = new Menu("spaceshipnavigation");
		armour = new Menu("weapons");
		protection = new Menu("protection");
		help = new HelpMenu("help");
		shield = new Menu("shield");
		activateShield = new ShieldActivateMenu(">activate");
		disableShield = new ShieldDisableMenu(">disable");
		shiptoTarget = new NavigateToTargetMenu("to target [x][y][z]");

		root.addSubMenu(navigation);
			navigation.addSubMenu(shiptoTarget);
		root.addSubMenu(armour);
		root.addSubMenu(protection);
		root.addSubMenu(help);
			protection.addSubMenu(shield);
			shield.addSubMenu(activateShield);
			shield.addSubMenu(disableShield);
			
			runBefore = true;
			
		System.out.println("");

		userInput(root);
		root.switchMenu(input);
		
		/*
		System.out.print("> ");
		input = scanner.next();
		menu1.switchMenu(input);
		
		System.out.print("> ");
		input = scanner.next();
		menu3.switchMenu(input);
		*/
		
		scanner.close();
	}
	
	/**
	 * @param menu
	 */
	private static void userInput(Menu menu){
		System.out.print("> ");
		input = scanner.next();
		while(!input.equals("quit")){
			menu.switchMenu(input);
			userInput(menu);
		}
		
	}
	
	/**
	 * Returns the root menu.
	 * @return root
	 */
	public static Menu getRootMenu(){
		return root;
	}

	/**
	 * Returns a boolean runBefore.
	 * @return runBefore
	 */
	public static boolean getRunBefore(){
		return runBefore;
	}

	


}