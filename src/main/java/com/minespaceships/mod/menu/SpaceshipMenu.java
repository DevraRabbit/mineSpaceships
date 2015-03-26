package com.minespaceships.mod.menu;

import com.minespacehips.mod.functionalMenus.energyMenus.EnergyManagementMenu;
import com.minespaceships.mod.menu.functionalMenus.*;
import com.minespaceships.mod.overhead.CustomGuiChat;
import com.minespaceships.mod.overhead.IMenuInterface;

/**
 * A default menu structure.
 * @author ovae.
 * @version 20150313.
 */
public class SpaceshipMenu {

	//Is necessary to now if the DefaultMenu was initialise before.
	private static boolean runBefore;

	//The root menu.
	private static Menu root;

	//Menu for the spaceship navigation.
	private static Menu navigation;

	//Menu if you need help
	private static Menu help;

	//Menu for the spaceship shield.
	private static Menu energySystem;

	//Menu which contains the ability to set the spaceship to a target position.
	private static Menu shipToTargetMenu;

	//Menu which contains the functionality to get the to target coordinates.
	private static Menu shipToTargetFunc;

	//Menu to create a new spaceship.
	private static Menu createShip;
	
	//Menu of the move forward command
	private static Menu moveForwardMenu;

	//Menu of the move forward command witch contains the functionality.
	private static Menu moveForwardFuncMenu;

	//Menu of the move back command.
	private static Menu moveBackMenu;

	//Menu for the move back command witch contains the functionality.
	private static Menu moveBackFuncMenu;

	//Menu of the move right command.
	private static Menu moveRightMenu;

	//Menu for the move right command witch contains the functionality.
	private static Menu moveRightFuncMenu;

	//Menu of the move left command.
	private static Menu moveLeftMenu;

	//Menu for the move left command witch contains the functionality.
	private static Menu moveLeftFuncMenu;

	//Menu of the move up command.
	private static Menu moveUpMenu;

	//Menu for the move up command witch contains the functionality.
	private static Menu moveUpFuncMenu;

	//Menu of the move down command.
	private static Menu moveDownMenu;

	//Menu for the move down command witch contains the functionality.
	private static Menu moveDownFuncMenu;

	//Menu for the liftoff functionality.
	private static Menu liftoffMenu;

	//Menu for the landing functionality.
	private static Menu landingMenu;

	/**
	 * Initialise the menu structure.
	 * @param terminal
	 */
	public static void initMenu(IMenuInterface terminal){
		if(terminal.equals(null)){
			System.err.println("terminal can not be null.");
		}
		String out ="";

		//Initialise all menus.
		root = new Menu("Spaceship console");
		createShip = new CreateShipMenu("recreate spaceship");
		navigation = new Menu("spaceship navigation");
		help = new HelpMenu("help");
		energySystem = new EnergyManagementMenu();
		shipToTargetMenu = new Menu("to target");

		moveForwardMenu = new Menu("move forward");
		out = "";
		out +="Please type in how may blocks you want to move\n"
		+ "    forward.    ";
		moveForwardFuncMenu = new moveForwardMenu(out);

		moveBackMenu = new Menu("move back");
		out = "";
		out +="Please type in how may blocks you want to move\n"
		+ "    back.    ";
		moveBackFuncMenu = new moveBackMenu(out);

		moveRightMenu = new Menu("move right");
		out = "";
		out +="Please type in how may blocks you want to move\n"
		+ "    right.    ";
		moveRightFuncMenu = new moveRightMenu(out);

		moveLeftMenu = new Menu("move left");
		out = "";
		out +="Please type in how may blocks you want to move\n"
		+ "    left.    ";
		moveLeftFuncMenu = new moveLeftMenu(out);

		moveUpMenu = new Menu("move up");
		out = "";
		out +="Please type in how may blocks you want to move\n"
		+ "    up.    ";
		moveUpFuncMenu = new moveUpMenu(out);

		moveDownMenu = new Menu("move down");
		out = "";
		out +="Please type in how may blocks you want to move\n"
		+ "    down.    ";
		moveDownFuncMenu = new moveDownMenu(out);

		liftoffMenu = new liftoffMenu("liftoff");
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
			navigation.addSubMenu(landingMenu);
		root.addSubMenu(energySystem);

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
