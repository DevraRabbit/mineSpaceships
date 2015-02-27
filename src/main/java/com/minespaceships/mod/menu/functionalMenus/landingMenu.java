package com.minespaceships.mod.menu.functionalMenus;

import com.minespaceships.mod.menu.FunctionalMenu;
import com.minespaceships.mod.menu.Menu;
import com.minespaceships.mod.overhead.CustomGuiChat;

/**
 * 
 * @author ovae.
 * @version 20150226
 */
public class landingMenu extends Menu implements FunctionalMenu{

	//The terminal to write in.
	private CustomGuiChat terminal;

	public landingMenu(String name, CustomGuiChat terminal) {
		super(name);
		this.terminal = terminal;
	}

	@Override
	public String activate(String command) {
		return "landing not implemented yet!";
	}

}
