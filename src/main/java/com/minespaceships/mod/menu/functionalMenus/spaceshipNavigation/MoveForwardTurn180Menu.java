package com.minespaceships.mod.menu.functionalMenus.spaceshipNavigation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;

import com.minespaceships.mod.menu.FunctionalMenu;
import com.minespaceships.mod.menu.FunctionalParamMenu;
import com.minespaceships.mod.menu.Menu;
import com.minespaceships.mod.menu.functionalMenus.energyMenus.TerminalUtil;
import com.minespaceships.mod.overhead.CustomGuiChat;
import com.minespaceships.mod.overhead.IMenuInterface;
import com.minespaceships.mod.spaceship.Shipyard;
import com.minespaceships.mod.spaceship.Spaceship;
import com.minespaceships.mod.spaceship.SpaceshipCommands;
import com.minespaceships.mod.spaceship.Turn;

/**
 * Move the spaceship position forward.
 * @author ovae.
 * @version 20150323.
 */
public class MoveForwardTurn180Menu extends Menu implements FunctionalMenu {

	/**
	 * Creates a new moveForwardMenu.
	 * @param name
	 */
	public MoveForwardTurn180Menu() {
		super("Mad Evan");
	}

	/**
	 * The functionality of the menu is activated by this method.
	 * @param command
	 * @param terminal
	 */
	@Override
	public String activate(IMenuInterface terminal, String data) {
		
		Spaceship ship=TerminalUtil.getShip(terminal);
		if (ship!=null){
			ship.setTarget(ship.getOrigin().add(ship.getShipLengthToAdd()), Turn.AROUND);
		
		}
		return "";
	}

	@Override
	public String getData() {
		return null;
	}

}