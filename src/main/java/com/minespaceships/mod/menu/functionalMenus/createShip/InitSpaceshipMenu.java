package com.minespaceships.mod.menu.functionalMenus.createShip;

import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

import com.minespaceships.mod.menu.FunctionalParamMenu;
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
public class InitSpaceshipMenu extends Menu implements FunctionalParamMenu{

	/**
	 * The constructor creates a new spaceship.
	 * @param name
	 * @param terminal
	 */
	public InitSpaceshipMenu() {
		super("Enter a password for your new spaceship:");
	}

	/**
	 * The functionality of the menu is activated by this method.
	 * @param command
	 */
	@Override
	public String activate(String password, IMenuInterface terminal, String data) {
		World world = terminal.getChatRegisterEntity().getWorld();

		terminal.getChatRegisterEntity().executeCommand(SpaceshipCommands.initAuto, null);
		Spaceship ship = Shipyard.getShipyard(world).getShip(terminal.getChatRegisterEntity().getPos(), terminal.getChatRegisterEntity().getWorld());
		ship.setPassword(password);
		terminal.display(EnumChatFormatting.GREEN+">> Initialise spaceship successful<<\nPress 'Esc' and reopen the menu.", terminal.getPlayerEntity(), false);
		
		return "";
	}

	@Override
	public String getData() {
		return null;
	}

}
