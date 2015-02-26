package com.minespaceships.mod.menu;

import com.minespaceships.mod.overhead.CustomGuiChat;

/**
 * A menu for the shield activate functionality.
 * @author ovae.
 * @version 20150221
 */
public class ShieldActivateMenu extends Menu implements FunctionalMenu{

	//The terminal to write in.
	private CustomGuiChat terminal;

	/**
	 * Creates a new menu, for the shield activate functionality.
	 * @param name
	 * @param terminal
	 */
	public ShieldActivateMenu(String name, CustomGuiChat terminal) {
		super(name);
		this.terminal = terminal;
	}

	/**
	 * The functionality of the menu is activated by this method.
	 * @param command
	 */
	@Override
	public String activate(String command) {
		this.terminal.getChatRegisterEntity().getWorld().setRainStrength( 0f);
		return ">> shield activated <<\nPress 'm' to get back.";
	}

}