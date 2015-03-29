package com.minespaceships.mod.menu;

import com.minespacehips.mod.functionalMenus.energyMenus.EnergyManagementMenu;
import com.minespaceships.mod.menu.functionalMenus.CreateShipMenu;
import com.minespaceships.mod.menu.functionalMenus.HelpMenu;
import com.minespaceships.mod.menu.functionalMenus.ToTargetFunktionalMenu;
import com.minespaceships.mod.menu.functionalMenus.spaceshipNavigation.LandingMenu;
import com.minespaceships.mod.menu.functionalMenus.spaceshipNavigation.SpaceshipNavigation;
import com.minespaceships.mod.menu.functionalMenus.spaceshipNavigation.moveBackMenu;
import com.minespaceships.mod.menu.functionalMenus.spaceshipNavigation.moveDownMenu;
import com.minespaceships.mod.menu.functionalMenus.spaceshipNavigation.moveForwardMenu;
import com.minespaceships.mod.menu.functionalMenus.spaceshipNavigation.moveLeftMenu;
import com.minespaceships.mod.menu.functionalMenus.spaceshipNavigation.moveRightMenu;
import com.minespaceships.mod.menu.functionalMenus.spaceshipNavigation.moveUpMenu;
import com.minespaceships.mod.menu.functionalMenus.targetMenus.PhaserShootMenu;
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

	//Menu for the spaceship energy.
	private static Menu shootPhaserMenu;

	//Menu which contains the ability to set the spaceship to a target position.
	private static Menu shipToTargetMenu;

	//Menu which contains the functionality to get the to target coordinates.
	private static Menu shipToTargetFunc;

	//Menu to create a new spaceship.
	private static Menu createShip;

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
		shootPhaserMenu = new PhaserShootMenu();
		shipToTargetMenu = new Menu("to target");

		out = "";
		out+="  To target needs three parameter x,y and z.\n"
		+"    Current position: "+terminal.getChatRegisterEntity().getPos()+'\n'
		+"    Please enter them in the following form,\n"
		+"    without the brakets or whitespaces:\n"
		+"    [x];[y];[z]";
		shipToTargetFunc = new ToTargetFunktionalMenu(out);

		//Create the menu structure.
		root.addSubMenu(new CreateShipMenu());
		root.addSubMenu(new SpaceshipNavigation());
		root.addSubMenu(new EnergyManagementMenu());
		root.addSubMenu(shootPhaserMenu);
		root.addSubMenu(new HelpMenu());
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
