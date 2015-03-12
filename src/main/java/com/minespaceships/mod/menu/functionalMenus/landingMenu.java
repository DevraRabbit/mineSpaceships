package com.minespaceships.mod.menu.functionalMenus;

import com.minespaceships.mod.menu.FunctionalMenu;
import com.minespaceships.mod.menu.Menu;
import com.minespaceships.mod.overhead.CustomGuiChat;

/**
 * Lands the spaceship.
 * @author ovae.
 * @version 20150312.
 */
public class landingMenu extends Menu implements FunctionalMenu{

	/**
	 * Creates a new landingMenu.
	 * @param name
	 */
	public landingMenu(String name) {
		super(name);
	}

	/**
	 * The functionality of the menu is activated by this method.
	 * @param command
	 * @param terminal
	 */
	@Override
	public String activate(String command, CustomGuiChat terminal) {
		return "landing not implemented yet!\nPress 'm' to get back.";
	}

}
