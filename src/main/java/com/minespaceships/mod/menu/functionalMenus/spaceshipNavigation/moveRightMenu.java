package com.minespaceships.mod.menu.functionalMenus.spaceshipNavigation;

import com.minespaceships.mod.menu.FunctionalParamMenu;
import com.minespaceships.mod.menu.Menu;
import com.minespaceships.mod.overhead.CustomGuiChat;
import com.minespaceships.mod.overhead.IMenuInterface;
import com.minespaceships.mod.spaceship.SpaceshipCommands;

/**
 * Move the spaceship position to the right.
 * @author ovae.
 * @version 20150320.
 */
public class moveRightMenu extends Menu implements FunctionalParamMenu{

	/**
	 * Creates a new moveRightMenu.
	 * @param name
	 */
	public moveRightMenu() {
		super("Please type in how may blocks you want to move\n    right.    ");
	}

	/**
	 * The functionality of the menu is activated by this method.
	 * @param command
	 * @param terminal
	 */
	@Override
	public String activate(String command, IMenuInterface terminal) {
		if(command.trim().isEmpty()){
			return "command can not be empty.";
		}
		if(command.equals(null)){
			return "command can not be null.";
		}

		terminal.getChatRegisterEntity().onCommand(SpaceshipCommands.moveRight+" "+command, terminal.getPlayerEntity());
		return "";
	}

}
