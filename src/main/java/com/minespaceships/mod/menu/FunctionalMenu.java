package com.minespaceships.mod.menu;

import com.minespaceships.mod.overhead.CustomGuiChat;
import com.minespaceships.mod.overhead.IMenuInterface;

/**
 * A menu object witch contains a function.
 * @author DieDiren, ovae.
 * @version 20150219
 */
public interface FunctionalMenu{

	/**
	 * Activate the functionality of the menu.
	 * @param paramlist
	 */
	public String activate(IMenuInterface terminal, String data);
	public String getData();

}
