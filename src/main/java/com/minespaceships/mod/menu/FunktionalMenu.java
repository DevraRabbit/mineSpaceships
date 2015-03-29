package com.minespaceships.mod.menu;

import com.minespaceships.mod.overhead.CustomGuiChat;

/**
 * A menu object witch contains a function.
 * @author DieDiren, ovae.
 * @version 20150219
 */
public interface FunktionalMenu{

	/**
	 * Activate the functionality of the menu.
	 * @param paramlist
	 */
	public String activate(String paramlist, CustomGuiChat terminal);

}
