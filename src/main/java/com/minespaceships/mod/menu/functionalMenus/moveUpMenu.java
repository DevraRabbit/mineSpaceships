package com.minespaceships.mod.menu.functionalMenus;

import com.minespaceships.mod.menu.FunctionalParamMenu;
import com.minespaceships.mod.menu.Menu;
import com.minespaceships.mod.overhead.CustomGuiChat;

/**
 * 
 * @author ovae.
 * @version 20150226
 */
public class moveUpMenu extends Menu implements FunctionalParamMenu{
	
	//The terminal to write in.
	private CustomGuiChat terminal;
	
	public moveUpMenu(String name, CustomGuiChat terminal) {
		super(name);
		this.terminal=terminal;
	}

	@Override
	public String activate(String command) {
		// TODO Auto-generated method stub
		return "move up not implemented yet!";
	}

}
