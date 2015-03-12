package com.minespaceships.mod.menu.functionalMenus;

import com.minespaceships.mod.menu.FunctionalMenu;
import com.minespaceships.mod.menu.Menu;
import com.minespaceships.mod.overhead.CustomGuiChat;

/**
 * A menu for the shield activate functionality.
 * @author ovae.
 * @version 20150312.
 */
public class ShieldActivateMenu extends Menu implements FunctionalMenu{

	/**
	 * Creates a new menu, for the shield activate functionality.
	 * @param name
	 */
	public ShieldActivateMenu(String name) {
		super(name);
	}

	/**
	 * The functionality of the menu is activated by this method.
	 * @param command
	 * @param terminal
	 */
	@Override
	public String activate(String command, CustomGuiChat terminal) {
		terminal.getChatRegisterEntity().getWorld().setRainStrength( 0f);
		return ">> shield activated <<\nPress 'm' to get back.";
	}

}