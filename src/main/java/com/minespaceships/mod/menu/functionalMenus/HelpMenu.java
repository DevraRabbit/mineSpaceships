package com.minespaceships.mod.menu.functionalMenus;

import net.minecraft.util.EnumChatFormatting;

import com.minespaceships.mod.CommandMessage;
import com.minespaceships.mod.MineSpaceships;
import com.minespaceships.mod.menu.FunctionalMenu;
import com.minespaceships.mod.menu.Menu;
import com.minespaceships.mod.overhead.CustomGuiChat;
import com.minespaceships.mod.overhead.IMenuInterface;
import com.minespaceships.mod.spaceship.SpaceshipCommands;

/**
 * This menu is the help menu witch explains to the user how to
 * use the menu structure and how to navigate in it.
 * @author ovae.
 * @version 20150320.
 */
public class HelpMenu extends Menu implements FunctionalMenu{

	/**
	 * Help menu constructor, creates an help menu.
	 * @param name
	 * @param terminal
	 */
	public HelpMenu() {
		super("Help");
	}

	/**
	 * The functionality of the menu is activated by this method.
	 * @param command
	 * @param terminal
	 */
	@Override
	public String activate(IMenuInterface terminal, String data) {
		return SpaceshipCommands.help(terminal.getPlayerEntity());
	}

	@Override
	public String getData() {
		// TODO Auto-generated method stub
		return null;
	}

}
