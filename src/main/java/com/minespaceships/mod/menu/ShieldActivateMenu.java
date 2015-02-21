package com.minespaceships.mod.menu;

import com.minespaceships.mod.overhead.CustomGuiChat;

/**
 * 
 * @author ovae.
 * @version 20150220
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
		String out ="";
		out+=">> shield activated <<";
		this.terminal.mc.theWorld.setRainStrength( 0f);
		return out;
	}

}