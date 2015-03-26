package com.minespaceships.mod.menu.functionalMenus;

import com.minespaceships.mod.menu.FunctionalParamMenu;
import com.minespaceships.mod.menu.Menu;
import com.minespaceships.mod.overhead.CustomGuiChat;
import com.minespaceships.mod.overhead.IMenuInterface;

/**
 * Move the spaceship position to the left.
 * @author ovae.
 * @version 20150320.
 */
public class moveLeftMenu extends Menu implements FunctionalParamMenu{

	/**
	 * Creates a new moveLeftmenu.
	 * @param name
	 */
	public moveLeftMenu(String name) {
		super(name);
	}

	/**
	 * The functionality of the menu is activated by this method.
	 * @param command
	 * @param terminal
	 */
	@Override
	public String activate(String command, IMenuInterface terminal) {
		// TODO Auto-generated method stub
		return "move left not implemented yet!";
	}

}
