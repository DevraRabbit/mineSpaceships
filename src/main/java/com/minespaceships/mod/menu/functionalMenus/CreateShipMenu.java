package com.minespaceships.mod.menu.functionalMenus;

import net.minecraft.util.EnumChatFormatting;

import com.minespaceships.mod.menu.FunctionalMenu;
import com.minespaceships.mod.menu.Menu;
import com.minespaceships.mod.overhead.ChatRegisterEntity;
import com.minespaceships.mod.overhead.CustomGuiChat;
import com.minespaceships.mod.spaceship.Shipyard;
import com.minespaceships.mod.spaceship.Spaceship;
import com.minespaceships.mod.spaceship.SpaceshipCommands;

/**
 * Menu witch will create a new spaceship.
 * @author ovae.
 * @version 20150323.
 */
public class CreateShipMenu extends Menu implements FunctionalMenu{

	/**
	 * The constructor creates a new spaceship.
	 * @param name
	 * @param terminal
	 */
	public CreateShipMenu(String name) {
		super(name);
	}

	/**
	 * The functionality of the menu is activated by this method.
	 * @param command
	 */
	@Override
	public String activate(String command, CustomGuiChat terminal) {
		if(command.trim().isEmpty()){
			return "command can not be empty.";
		}
		if(command.equals(null)){
			return "command can not be null.";
		}

		terminal.getChatRegisterEntity().onCommand(SpaceshipCommands.initAuto, null);
		return EnumChatFormatting.GREEN+">> Initialise spaceship successful<<\nPress 'Esc' and reopen the menu.";
	}

}
