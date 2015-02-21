package com.minespaceships.mod.menu;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.player.*;
import net.minecraft.util.ChatComponentText;

import com.minespaceships.mod.overhead.CustomGuiChat;

/**
 * 
 * @author ovae.
 * @version 20150220
 */
public class ShieldDisableMenu extends Menu implements FunktionalMenu{
	
	private CustomGuiChat terminal;
	/**
	 * 
	 * @param name
	 * @param terminal
	 */
	public ShieldDisableMenu(String name, CustomGuiChat terminal) {
		super(name);
		this.terminal = terminal;
	}

	/**
	 * 
	 */
	@Override
	public String activate(String paramlist) {
		String out ="";
		out+=">> shield disabled <<";
		//Let it rain
		this.terminal.mc.theWorld.setRainStrength(2f);
		return out;
	}

}
