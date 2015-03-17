package com.minespaceships.mod.menu.functionalMenus;

import net.minecraft.util.EnumChatFormatting;

import com.minespaceships.mod.CommandMessage;
import com.minespaceships.mod.MineSpaceships;
import com.minespaceships.mod.menu.FunctionalMenu;
import com.minespaceships.mod.menu.Menu;
import com.minespaceships.mod.overhead.CustomGuiChat;
import com.minespaceships.mod.spaceship.SpaceshipCommands;

/**
 * This menu is the help menu witch explains to the user how to
 * use the menu structure and how to navigate in it.
 * @author ovae.
 * @version 20150312.
 */
public class HelpMenu extends Menu implements FunctionalMenu{

	/**
	 * Help menu constructor, creates an help menu.
	 * @param name
	 * @param terminal
	 */
	public HelpMenu(String name) {
		super(name);
	}

	/**
	 * The functionality of the menu is activated by this method.
	 * @param command
	 * @param terminal
	 */
	@Override
	public String activate(String command, CustomGuiChat terminal) {
		return SpaceshipCommands.help(terminal.getPlayerEntity());
	}

}
