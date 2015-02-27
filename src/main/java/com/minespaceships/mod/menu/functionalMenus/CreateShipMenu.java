package com.minespaceships.mod.menu.functionalMenus;

import net.minecraft.util.EnumChatFormatting;

import com.minespaceships.mod.menu.FunctionalMenu;
import com.minespaceships.mod.menu.Menu;
import com.minespaceships.mod.overhead.CustomGuiChat;
import com.minespaceships.mod.spaceship.Spaceship;

/**
 * 	Menu witch will create a new spaceship.
 * @author ovae
 * @verison 20150226
 */
public class CreateShipMenu extends Menu implements FunctionalMenu{

	//The terminal to write in.
	CustomGuiChat terminal;

	/**
	 * The constructor creates a new spaceship.
	 * @param name
	 * @param terminal
	 */
	public CreateShipMenu(String name, CustomGuiChat terminal) {
		super(name);
		if(terminal.equals(null)){
			throw new IllegalArgumentException("terminal can not be null.");
		}
		this.terminal=terminal;
	}

	/**
	 * The functionality of the menu is activated by this method.
	 * @param command
	 */
	@Override
	public String activate(String command) {
		try {
			Spaceship spaceship = terminal.getChatRegisterEntity().getShip();
			this.terminal.getChatRegisterEntity().createShip( this.terminal.getChatRegisterEntity().getPos() , this.terminal.getChatRegisterEntity().getRemoteWorld());
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
