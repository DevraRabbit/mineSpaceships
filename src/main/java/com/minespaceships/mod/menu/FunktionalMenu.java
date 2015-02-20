package com.minespaceships.mod.menu;

import com.minespaceships.mod.overhead.CustomGuiChat;

/**
 * A menu object witch contains a function.
 * @author DieDiren, ovae.
 * @version 20150219
 */
public class FunktionalMenu extends Menu{

	/**
	 * 
	 * @param name
	 * @param terminal
	 */
	public FunktionalMenu(String name, CustomGuiChat terminal) {
		super(name, terminal);
	}

	/**
	 * @param name
	 */
	@Override
	public boolean switchMenu(final String name){
		
		return false;
	}
}
