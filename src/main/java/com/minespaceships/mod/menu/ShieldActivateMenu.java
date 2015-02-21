package com.minespaceships.mod.menu;

import com.minespaceships.mod.overhead.CustomGuiChat;

/**
 * 
 * @author ovae.
 * @version 20150221
 */
public class ShieldActivateMenu extends Menu implements FunktionalMenu{
	
	private CustomGuiChat terminal;
	
	/**
	 * 
	 * @param name
	 * @param terminal
	 */
	public ShieldActivateMenu(String name, CustomGuiChat terminal) {
		super(name);
		this.terminal = terminal;
	}

	/**
	 * 
	 */
	@Override
	public String activate(String paramlist) {
		this.terminal.getChatRegisterEntity().getWorld().setRainStrength( 0f);
		return ">> shield activated <<";
	}

}