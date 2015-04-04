package com.minespaceships.mod.menu.functionalMenus;

import com.minespaceships.mod.menu.FunctionalParamMenu;
import com.minespaceships.mod.menu.Menu;
import com.minespaceships.mod.overhead.IMenuInterface;

/**
 * 
 * @author ovae.
 * @version 20150403.
 */
public class LoginMenu {

	//The root menu.
	private static Menu root;

	public static void initMenu(){
		root = new Menu("Login");
		root.addSubMenu(new CheckLoginMenu());
	}

	public static Menu getRootMenu() {
		return root;
	}

}
