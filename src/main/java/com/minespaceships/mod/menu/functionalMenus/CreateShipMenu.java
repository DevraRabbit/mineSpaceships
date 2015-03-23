package com.minespaceships.mod.menu.functionalMenus;

import net.minecraft.util.EnumChatFormatting;

import com.minespaceships.mod.menu.FunctionalMenu;
import com.minespaceships.mod.menu.Menu;
import com.minespaceships.mod.overhead.ChatRegisterEntity;
import com.minespaceships.mod.overhead.CustomGuiChat;
import com.minespaceships.mod.spaceship.Shipyard;
import com.minespaceships.mod.spaceship.Spaceship;

/**
 * Menu witch will create a new spaceship.
 * @author ovae
 * @version 20150226
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
		try {
			Spaceship spaceship = terminal.getChatRegisterEntity().getShip();
			Shipyard.getShipyard(terminal.getChatRegisterEntity().getWorld()).createShip(terminal.getChatRegisterEntity().getPos() , terminal.getChatRegisterEntity().getWorld());
			String out="";
			out+= EnumChatFormatting.GREEN+">> Initialise spaceship successful<<\n"
				+"Press 'Esc' and reopen the menu.";
			return out;
		} catch (Exception e) {
			e.printStackTrace();
		}
		String out="";
		out+="If the navigation cube or a part of your spaceship connects"
			+"to the ground you can't initialise a new spaceship.\n\n"
			+EnumChatFormatting.RED+">> Initialise spaceship failed <<"
			+ "\nPress 'Esc' to close the menu.";
		return out;
	}

}
