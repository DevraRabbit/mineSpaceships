package com.minespaceships.mod.menu.functionalMenus;

import net.minecraft.world.World;

import com.minespaceships.mod.menu.FunctionalParamMenu;
import com.minespaceships.mod.menu.Menu;
import com.minespaceships.mod.menu.SpaceshipMenu;
import com.minespaceships.mod.overhead.CustomGuiChat;
import com.minespaceships.mod.overhead.IMenuInterface;
import com.minespaceships.mod.spaceship.Shipyard;
import com.minespaceships.mod.spaceship.Spaceship;

/**
 * 
 * @author ovae.
 * @version 20150403.
 */
public class CheckLoginMenu extends Menu implements FunctionalParamMenu{

	public CheckLoginMenu() {
		super("Enter your password:");
	}

	@Override
	public String activate(String command, IMenuInterface terminal, String data) {
		if(command == null)command = "";
		World world = terminal.getChatRegisterEntity().getWorld();
		Spaceship ship = Shipyard.getShipyard(world).getShip(terminal.getChatRegisterEntity().getPos(), terminal.getChatRegisterEntity().getWorld());

		if((command.trim()).equals(ship.getPassword())){
			terminal.display("Logged in", terminal.getPlayerEntity(), false);
			terminal.getChatRegisterEntity().getSpaceshipMenu().displayMain(SpaceshipMenu.getRootMenu(), (CustomGuiChat) terminal);
		}else{
			terminal.display("Logged in failed", terminal.getPlayerEntity(), false);
		}
		return "";
	}

	@Override
	public String getData() {
		return null;
	}
}
