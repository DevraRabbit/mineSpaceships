package com.minespaceships.mod.menu.functionalMenus;

import net.minecraft.util.MathHelper;

import com.minespaceships.mod.menu.FunctionalParamMenu;
import com.minespaceships.mod.menu.Menu;
import com.minespaceships.mod.overhead.CustomGuiChat;

/**
 * 
 * @author ovae.
 * @version 20150226
 */
public class moveForwardMenu extends Menu implements FunctionalParamMenu {

	//The terminal to write in.
	private CustomGuiChat terminal;

	public moveForwardMenu(String name,  CustomGuiChat terminal) {
		super(name);
		this.terminal = terminal;
	}

	@Override
	public String activate(String command) {
		if(command.trim().isEmpty()){
			return "command can not be empty.";
		}
		if(command.equals(null)){
			return "command can not be null.";
		}

		return "move forward not implemented yet!";
	}

}
