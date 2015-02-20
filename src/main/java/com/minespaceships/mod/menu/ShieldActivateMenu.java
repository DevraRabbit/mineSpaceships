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
	public ShieldActivateMenu(String name, CustomGuiChat terminal) {
		super(name, terminal);
	}

	/**
	 * 
	 */
	@Override
	public void activate(String paramlist) {
		super.terminal.display(">> shield activated <<");
		super.terminal.mc.theWorld.setRainStrength( 0f);
	}

}