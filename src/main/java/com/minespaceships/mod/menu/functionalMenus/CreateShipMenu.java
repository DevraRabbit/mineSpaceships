package com.minespaceships.mod.menu.functionalMenus;

import net.minecraft.util.EnumChatFormatting;

import com.minespaceships.mod.menu.FunctionalMenu;
import com.minespaceships.mod.menu.Menu;
import com.minespaceships.mod.overhead.ChatRegisterEntity;
import com.minespaceships.mod.overhead.CustomGuiChat;
import com.minespaceships.mod.overhead.IMenuInterface;
import com.minespaceships.mod.spaceship.Shipyard;
import com.minespaceships.mod.spaceship.Spaceship;
import com.minespaceships.mod.spaceship.SpaceshipCommands;

/**
 * Menu witch will create a new spaceship.
 * @author ovae.
 * @version 20150402.
 */
public class CreateShipMenu extends Menu implements FunctionalMenu{

	/**
	 * The constructor creates a new spaceship.
	 * @param name
	 * @param terminal
	 */
	public CreateShipMenu() {
		super("Create spaceship");
	}

	/**
	 * The functionality of the menu is activated by this method.
	 * @param command
	 */
	@Override
	public String activate(IMenuInterface terminal, String data) {

		terminal.getChatRegisterEntity().executeCommand(SpaceshipCommands.initAuto, null);
		terminal.display(EnumChatFormatting.GREEN+">> Initialise spaceship successful<<\nPress 'Esc' and reopen the menu.", terminal.getPlayerEntity(), false);
		return "";
	}

	@Override
	public String getData() {
		return null;
	}

}
