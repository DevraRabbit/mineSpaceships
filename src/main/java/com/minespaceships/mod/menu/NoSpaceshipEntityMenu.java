package com.minespaceships.mod.menu;

import com.minespaceships.mod.menu.functionalMenus.CreateShipMenu;
import com.minespaceships.mod.overhead.CustomGuiChat;


/**
 * If the navigation block has no spaceship entity, this menu is displayed.
 * @author ovae
 * @version 20150226
 */
public class NoSpaceshipEntityMenu{

	//Is necessary to now if the DefaultMenu was initialise before.
	private static boolean runBefore;

	private static Menu rootMenu;
	private static Menu initMenu;

	private NoSpaceshipEntityMenu(){}

	public static void initMenu(CustomGuiChat terminal){
		rootMenu = new Menu("root");
		initMenu= new CreateShipMenu("initialise spaceship");
		rootMenu.addSubMenu(initMenu);
		runBefore = true;
	}
	
	/**
	 * Returns the root menu.
	 * @return root
	 */
	public static Menu getRootMenu(){
		return rootMenu;
	}
	
	/**
	 * Returns a boolean runBefore.
	 * @return runBefore
	 */
	public static boolean getRunBefore(){
		return runBefore;
	}

}
