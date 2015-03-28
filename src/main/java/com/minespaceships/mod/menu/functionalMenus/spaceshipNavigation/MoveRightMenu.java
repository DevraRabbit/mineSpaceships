package com.minespaceships.mod.menu.functionalMenus.spaceshipNavigation;

import com.minespaceships.mod.menu.FunctionalParamMenu;
import com.minespaceships.mod.menu.Menu;
import com.minespaceships.mod.overhead.CustomGuiChat;
import com.minespaceships.mod.overhead.IMenuInterface;

/**
 * Move the spaceship position to the right.
 * @author ovae.
 * @version 20150320.
 */
public class MoveRightMenu extends Menu implements FunctionalParamMenu{

	/**
	 * Creates a new moveRightMenu.
	 * @param name
	 */
	public MoveRightMenu() {
		super("Please type in how may blocks you want to move\n    right.    ");
	}

	/**
	 * The functionality of the menu is activated by this method.
	 * @param command
	 * @param terminal
	 */
	@Override
	public String activate(String command, IMenuInterface terminal) {
		// TODO Auto-generated method stub
		return "move right not implemented yet!";
	}

}
