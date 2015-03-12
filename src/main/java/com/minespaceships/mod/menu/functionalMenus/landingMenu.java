package com.minespaceships.mod.menu.functionalMenus;

import com.minespaceships.mod.menu.FunctionalMenu;
import com.minespaceships.mod.menu.Menu;
import com.minespaceships.mod.overhead.CustomGuiChat;

/**
 * Lands the spaceship.
 * @author ovae.
 * @version 20150310.
 */
public class landingMenu extends Menu implements FunctionalMenu{

	//The terminal to write in.
	private CustomGuiChat terminal;

	public landingMenu(String name) {
		super(name);
	}

	@Override
	public String activate(String command, CustomGuiChat terminal) {
		return "landing not implemented yet!\nPress 'm' to get back.";
	}

}
