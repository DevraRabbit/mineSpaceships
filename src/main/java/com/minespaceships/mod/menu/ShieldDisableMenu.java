package com.minespaceships.mod.menu;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.player.*;
import net.minecraft.util.ChatComponentText;

import com.minespaceships.mod.overhead.CustomGuiChat;

/**
 * 
 * @author ovae.
 * @version 20150221
 */
public class ShieldDisableMenu extends Menu implements FunktionalMenu{

	/**
	 * 
	 */
	private CustomGuiChat terminal;

	/**
	 * 
	 * @param name
	 * @param terminal
	 */
	public ShieldDisableMenu(String name, CustomGuiChat terminal) {
		super(name);
		if(terminal.equals(null)){
			throw new IllegalArgumentException("terminal can not be null");
		}
		this.terminal = terminal;
	}

	/**
	 * 
	 */
	@Override
	public String activate(String paramlist) {
		//Let it rain
		this.terminal.getChatRegisterEntity().getWorld().setRainStrength( 2f);
		return ">> shield disabled <<";
	}

}
