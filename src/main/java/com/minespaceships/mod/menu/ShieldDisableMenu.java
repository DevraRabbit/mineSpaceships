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
	
	/**
	 * 
	 * @param name
	 * @param terminal
	 */
	public ShieldDisableMenu(String name, CustomGuiChat terminal) {
		super(name, terminal);
	}

	/**
	 * 
	 */
	@Override
	public void activate(String paramlist) {
		super.terminal.display(">> shield disabled <<");
		//Let it rain
		super.terminal.mc.theWorld.setRainStrength(2f);
	}

}
