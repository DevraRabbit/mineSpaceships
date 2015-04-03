package com.minespaceships.mod.menu.functionalMenus;

import com.minespaceships.mod.menu.FunctionalParamMenu;
import com.minespaceships.mod.menu.Menu;
import com.minespaceships.mod.overhead.IMenuInterface;

/**
 * 
 * @author ovae.
 * @version 20150403.
 */
public class LoginMenu extends Menu {

	public LoginMenu() {
		super("Login");
		this.addSubMenu(new CheckLoginMenu());
	}

}
