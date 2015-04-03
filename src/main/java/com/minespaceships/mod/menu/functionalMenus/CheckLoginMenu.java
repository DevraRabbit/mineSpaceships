package com.minespaceships.mod.menu.functionalMenus;

import com.minespaceships.mod.menu.FunctionalParamMenu;
import com.minespaceships.mod.menu.Menu;
import com.minespaceships.mod.overhead.IMenuInterface;

/**
 * 
 * @author ovae.
 * @version 20150403.
 */
public class CheckLoginMenu extends Menu implements FunctionalParamMenu{

	public CheckLoginMenu() {
		super("Check login");
	}

	@Override
	public String activate(String command, IMenuInterface terminal, String data) {
		return null;
	}

}
