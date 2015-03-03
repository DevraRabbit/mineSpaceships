package com.minespaceships.mod.menu;

import com.minespaceships.mod.overhead.CustomGuiChat;

/**
 * 
 * @author ovae.
 * @version 20150220
 */
public class ShieldActivateMenu extends Menu implements FunktionalMenu{
	
	/**
	 * 
	 * @param name
	 * @param terminal
	 */
	public ShieldActivateMenu(String name) {
		super(name);
	}

	/**
	 * 
	 */
	@Override
	public void activate(String paramlist) {
		System.out.println(">> shield activated <<");
		
	}

}