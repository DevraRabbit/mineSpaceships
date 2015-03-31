package com.minespaceships.mod.menu.functionalMenus.spaceshipNavigation;

import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumChatFormatting;

import com.minespaceships.mod.menu.FunctionalMenu;
import com.minespaceships.mod.menu.FunctionalParamMenu;
import com.minespaceships.mod.menu.Menu;
import com.minespaceships.mod.menu.functionalMenus.energyMenus.TerminalUtil;
import com.minespaceships.mod.overhead.CustomGuiChat;
import com.minespaceships.mod.overhead.IMenuInterface;
import com.minespaceships.mod.spaceship.Shipyard;
import com.minespaceships.mod.spaceship.Spaceship;
import com.minespaceships.mod.spaceship.SpaceshipCommands;

/**
 * Move the spaceship position up.
 * @author ovae.
 * @version 20150323.
 */
public class StopMenu extends Menu implements FunctionalMenu{

	/**
	 * Creates a new liftoffMenu.
	 * @param name
	 */
	public StopMenu() {
		super("liftoff");
	}

	/**
	 * The functionality of the menu is activated by this method.
	 * @param command
	 * @param terminal
	 */
	@Override
	public String activate(String command, IMenuInterface terminal) {
		Spaceship ship = TerminalUtil.getShip(terminal);
		if(ship != null){
			int distToGround = ship.getDistanceToGround();
			ship.stop();
			return EnumChatFormatting.RED+"Braking!";
		}
		return "No Spaceship...";
	}

}
