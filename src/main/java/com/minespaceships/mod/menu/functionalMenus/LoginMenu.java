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

	private static boolean loggedIn;

	public static void initMenu(){
		root = new Menu("Login");
		root.addSubMenu(new CheckLoginMenu());
		loggedIn = false;
	}

	public static Menu getRootMenu() {
		return root;
	}

	public static void login(){
		loggedIn = true;
	}

	public static void logout(){
		loggedIn = false;
	}

	public static boolean getLoggedIn() {
		return loggedIn;
	}

}
