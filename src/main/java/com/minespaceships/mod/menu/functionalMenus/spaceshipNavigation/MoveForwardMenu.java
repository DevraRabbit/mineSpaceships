package com.minespaceships.mod.menu.functionalMenus.spaceshipNavigation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;

import com.minespaceships.mod.menu.FunctionalParamMenu;
import com.minespaceships.mod.menu.Menu;
import com.minespaceships.mod.overhead.CustomGuiChat;
import com.minespaceships.mod.overhead.IMenuInterface;
import com.minespaceships.mod.spaceship.Shipyard;
import com.minespaceships.mod.spaceship.Spaceship;
import com.minespaceships.mod.spaceship.SpaceshipCommands;

/**
 * Move the spaceship position forward.
 * @author ovae.
 * @version 20150323.
 */
public class MoveForwardMenu extends Menu implements FunctionalParamMenu {

	/**
	 * Creates a new moveForwardMenu.
	 * @param name
	 */
	public MoveForwardMenu() {
		super("Please type in how may blocks you want to move\n    forward.    ");
	}

	/**
	 * The functionality of the menu is activated by this method.
	 * @param command
	 * @param terminal
	 */
	@Override
	public String activate(final String command, IMenuInterface terminal, String data) {
		if(command.trim().isEmpty()){
			return "command can not be empty.";
		}
		if(command.equals(null)){
			return "command can not be null.";
		}

		terminal.getChatRegisterEntity().executeCommand(SpaceshipCommands.moveForward+" "+command, terminal.getPlayerEntity());
		return "";
	}

	@Override
	public String getData() {
		return null;
	}

}
