package com.minespaceships.mod.menu.functionalMenus;

import com.minespaceships.mod.menu.FunctionalParamMenu;
import com.minespaceships.mod.menu.Menu;
import com.minespaceships.mod.overhead.CustomGuiChat;

/**
 * Move the spaceship position to the left.
 * @author ovae.
 * @version 20150310.
 */
public class moveLeftMenu extends Menu implements FunctionalParamMenu{
	
	//The terminal to write in.
	private CustomGuiChat terminal;
	
	public moveLeftMenu(String name) {
		super(name);
	}

	@Override
	public String activate(String command, CustomGuiChat terminal) {
		// TODO Auto-generated method stub
		return "move left not implemented yet!";
	}

}
