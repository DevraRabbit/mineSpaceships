package com.minespaceships.mod.menu;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.player.*;
import net.minecraft.util.ChatComponentText;

import com.minespaceships.mod.overhead.CustomGuiChat;

/**
 * A menu for the shields disable functionality.
 * @author ovae.
 * @version 20150221
 */
public class ShieldDisableMenu extends Menu implements FunctionalMenu{

	//The terminal to write in.
	private CustomGuiChat terminal;

	/**
	 * Creates a new menu, for the shields disable functionality.
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
	 * The functionality of the menu is activated by this method.
	 * @param command
	 */
	@Override
	public String activate(String command) {
		//Let it rain
		this.terminal.getChatRegisterEntity().getWorld().setRainStrength( 2f);
		return ">> shield disabled <<\nPress 'm' to get back.";
	}

}
