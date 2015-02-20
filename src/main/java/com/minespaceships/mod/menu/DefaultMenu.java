package com.minespaceships.mod.menu;

import com.minespaceships.mod.overhead.CustomGuiChat;

/**
 * A default menu structure.
 * @author ovae
 * @verison 20150220
 */
public class DefaultMenu {

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
	 * Initialise the menu structure.
	 * @param terminal
	 */
	public static void initMenu(CustomGuiChat terminal){
		root = new Menu("Main", terminal);
		navigation = new Menu("spaceshipnavigation", terminal);
		armour = new Menu("weapons", terminal);
		protection = new Menu("protection", terminal);
		help = new HelpMenu("help", terminal);
		shield = new Menu("shield", terminal);
		activateShield = new ShieldActivateMenu(">activate", terminal);
		disableShield = new ShieldDisableMenu(">disable", terminal);

		root.addSubMenu(navigation);
		root.addSubMenu(armour);
		root.addSubMenu(protection);
		root.addSubMenu(help);
			protection.addSubMenu(shield);
			shield.addSubMenu(activateShield);
			shield.addSubMenu(disableShield);
		runBefore = true;
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
