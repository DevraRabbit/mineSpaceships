package com.minespaceships.mod.menu;

import com.minespaceships.mod.menu.functionalMenus.energyMenus.EnergyManagementMenu;
import com.minespaceships.mod.menu.functionalMenus.CreateShipMenu;
import com.minespaceships.mod.menu.functionalMenus.HelpMenu;
import com.minespaceships.mod.menu.functionalMenus.ToTargetFunktionalMenu;
import com.minespaceships.mod.menu.functionalMenus.spaceshipNavigation.LandingMenu;
import com.minespaceships.mod.menu.functionalMenus.spaceshipNavigation.SpaceshipNavigation;
import com.minespaceships.mod.menu.functionalMenus.spaceshipNavigation.LiftoffMenu;
import com.minespaceships.mod.menu.functionalMenus.spaceshipNavigation.MoveBackMenu;
import com.minespaceships.mod.menu.functionalMenus.spaceshipNavigation.MoveDownMenu;
import com.minespaceships.mod.menu.functionalMenus.spaceshipNavigation.MoveForwardMenu;
import com.minespaceships.mod.menu.functionalMenus.spaceshipNavigation.MoveLeftMenu;
import com.minespaceships.mod.menu.functionalMenus.spaceshipNavigation.MoveRightMenu;
import com.minespaceships.mod.menu.functionalMenus.spaceshipNavigation.MoveUpMenu;
import com.minespaceships.mod.menu.functionalMenus.targetMenus.PhaserShootMenu;
import com.minespaceships.mod.overhead.CustomGuiChat;
import com.minespaceships.mod.overhead.IMenuInterface;

/**
 * A default menu structure.
 * @author ovae.
 * @version 20150331.
 */
public class SpaceshipMenu {

	//Is necessary to now if the DefaultMenu was initialise before.
	private static boolean runBefore;

	//The root menu.
	private static Menu root;

	/**
	 * Initialise the menu structure.
	 * @param terminal
	 */
	public static void initMenu(IMenuInterface terminal){
		if(terminal.equals(null)){
			System.err.println("terminal can not be null.");
		}

		//Initialise all menus.
		root = new Menu("Spaceship console");

		//Create the menu structure.
		root.addSubMenu(new CreateShipMenu());
		root.addSubMenu(new SpaceshipNavigation());
		root.addSubMenu(new EnergyManagementMenu());
		root.addSubMenu(new PhaserShootMenu());
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
