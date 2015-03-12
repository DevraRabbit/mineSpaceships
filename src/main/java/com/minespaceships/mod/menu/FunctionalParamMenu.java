package com.minespaceships.mod.menu;

import com.minespaceships.mod.overhead.CustomGuiChat;

/**
 * A menu object witch contains a function and needs parameters.
 * @author ovae.
 * @version 20150225
 */
public interface FunctionalParamMenu{

	/**
	 * Activate the functionality of the menu.
	 * @param paramlist
	 */
	public String activate(String command, CustomGuiChat terminal);

}
