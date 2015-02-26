package com.minespaceships.mod.menu;

import com.minespaceships.mod.menu.functionalMenus.CreateShipMenu;
import com.minespaceships.mod.overhead.CustomGuiChat;


/**
 * If the navigation block has no spaceship entity, this menu is displayed.
 * @author ovae
 * @version 20150226
 */
public class NoSpaceshipEntityMenu{

	private static Menu rootMenu;
	private static Menu initMenu;

	public static void initMenu(CustomGuiChat terminal){
		rootMenu = new Menu("root");
		initMenu= new CreateShipMenu("create ship", terminal);
	}

}
