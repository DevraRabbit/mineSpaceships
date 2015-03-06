package com.minespaceships.mod.menu;

import com.minespaceships.mod.menu.functionalMenus.*;
import com.minespaceships.mod.overhead.CustomGuiChat;

/**
 * A default menu structure.
 * @author ovae.
 * @version 20150221
 */
public class SpaceshipMenu {

	//Is necessary to now if the DefaultMenu was initialise before.
	private static boolean runBefore;

	//The root menu.
	private static Menu root;

	//Menu for the spaceship navigation.
	private static Menu navigation;

	//Menu for the spaceship armour.
	private static Menu armour;

	//Menu for all protective devices of the spaceship.
	private static Menu protection;

	//Menu if you need help
	private static Menu help;

	//Menu for the spaceship shield.
	private static Menu shield;

	//Menu which contains the ability to activate the shield
	private static Menu activateShield;

	//Menu which contains the ability to disable the shield
	private static Menu disableShield;

	//Menu which contains the ability to set the spaceship to a target position.
	private static Menu shipToTargetMenu;

	//Menu which contains the functionality to get the to target coordinates.
	private static Menu shipToTargetFunc;

	//Menu to create a new spaceship.
	private static Menu createShip;

	//Menu for all phaser types.
	private static Menu phaserMenu;

	//Menu for all torpedo types.
	private static Menu torpedoMenu;
	
	private static Menu moveForwardMenu;
	private static Menu moveForwardFuncMenu;
	private static Menu moveBackMenu;
	private static Menu moveBackFuncMenu;
	private static Menu moveRightMenu;
	private static Menu moveRightFuncMenu;
	private static Menu moveLeftMenu;
	private static Menu moveLeftFuncMenu;
	private static Menu moveUpMenu;
	private static Menu moveUpFuncMenu;
	private static Menu moveDownMenu;
	private static Menu moveDownFuncMenu;
	
	private static Menu liftoffMenu;
	private static Menu liftoffFuncMenu;
	private static Menu landingMenu;

	/**
	 * Initialise the menu structure.
	 * @param terminal
	 */
	public static void initMenu(CustomGuiChat terminal){
		if(terminal.equals(null)){
			System.err.println("terminal can not be null.");
		}
		String out ="";

		//Initialise all menus.
		root = new Menu("Spaceship console");
		createShip = new CreateShipMenu("recreate spaceship");
		navigation = new Menu("spaceship navigation");
		armour = new Menu("weapons");
		protection = new Menu("protection");
		help = new HelpMenu("help");
		shield = new Menu("shield");
		activateShield = new ShieldActivateMenu("activate shield");
		disableShield = new ShieldDisableMenu("disable shield");
		shipToTargetMenu = new Menu("to target");
		phaserMenu = new Menu("phaser");
		torpedoMenu = new Menu("torpedo");

		moveForwardMenu = new Menu("move forward");
		out = "";
		out +="Please type in how may blocks you want to move forward.";
		moveForwardFuncMenu = new moveForwardMenu(out);

		moveBackMenu = new Menu("move back");
		out = "";
		out +="Please type in how may blocks you want to move back.";
		moveBackFuncMenu = new moveBackMenu(out);

		moveRightMenu = new Menu("move right");
		out = "";
		out +="Please type in how may blocks you want to move right.";
		moveRightFuncMenu = new moveRightMenu(out);

		moveLeftMenu = new Menu("move left");
		out = "";
		out +="Please type in how may blocks you want to move left.";
		moveLeftFuncMenu = new moveLeftMenu(out);

		moveUpMenu = new Menu("move up");
		out = "";
		out +="Please type in how may blocks you want to move up.";
		moveUpFuncMenu = new moveUpMenu(out);

		moveDownMenu = new Menu("move down");
		out = "";
		out +="Please type in how may blocks you want to move down.";
		moveDownFuncMenu = new moveDownMenu(out);

		liftoffMenu = new Menu("liftoff");
		out = "";
		out +="liftoff functional menu";
		liftoffFuncMenu = new liftoffMenu(out);
		landingMenu = new landingMenu("landing");

		out = "";
		out+="  To target needs three parameter x,y and z.\n"
		+"    Current position: "+terminal.getChatRegisterEntity().getPos()+'\n'
		+"    Please enter them in the following form,\n"
		+"    without the brakets or whitespaces:\n"
		+"    [x];[y];[z]";
		shipToTargetFunc = new ToTargetFunktionalMenu(out);

		//Create the menu structure.
		root.addSubMenu(createShip);
		root.addSubMenu(navigation);
			navigation.addSubMenu(shipToTargetMenu);
				shipToTargetMenu.addSubMenu(shipToTargetFunc);
			navigation.addSubMenu(moveForwardMenu);
				moveForwardMenu.addSubMenu(moveForwardFuncMenu);
			navigation.addSubMenu(moveBackMenu);
				moveBackMenu.addSubMenu(moveBackFuncMenu);
			navigation.addSubMenu(moveRightMenu);
				moveRightMenu.addSubMenu(moveRightFuncMenu);
			navigation.addSubMenu(moveLeftMenu);
				moveLeftMenu.addSubMenu(moveLeftFuncMenu);
			navigation.addSubMenu(moveUpMenu);
				moveUpMenu.addSubMenu(moveUpFuncMenu);
			navigation.addSubMenu(moveDownMenu);
				moveDownMenu.addSubMenu(moveDownFuncMenu);
			navigation.addSubMenu(liftoffMenu);
				liftoffMenu.addSubMenu(liftoffFuncMenu);
			navigation.addSubMenu(landingMenu);
			
		root.addSubMenu(armour);
			armour.addSubMenu(phaserMenu);
			armour.addSubMenu(torpedoMenu);
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
