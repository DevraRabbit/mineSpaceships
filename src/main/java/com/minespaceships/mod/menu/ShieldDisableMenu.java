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
	public ShieldDisableMenu(String name) {
		super(name);
	}

	/**
	 * 
	 */
	@Override
	public void activate(String paramlist) {
		System.out.println(">> shield disabled <<");
		
	}

}
