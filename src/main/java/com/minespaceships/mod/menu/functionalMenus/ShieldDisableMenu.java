package com.minespaceships.mod.menu.functionalMenus;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.player.*;
import net.minecraft.util.ChatComponentText;

import com.minespaceships.mod.menu.FunctionalMenu;
import com.minespaceships.mod.menu.Menu;
import com.minespaceships.mod.overhead.CustomGuiChat;

/**
 * A menu for the shields disable functionality.
 * @author ovae.
 * @version 20150312.
 */
public class ShieldDisableMenu extends Menu implements FunctionalMenu{

	/**
	 * Creates a new menu, for the shields disable functionality.
	 * @param name
	 */
	public ShieldDisableMenu(String name) {
		super(name);
	}

	/**
	 * The functionality of the menu is activated by this method.
	 * @param command
	 * @param terminal
	 */
	@Override
	public String activate(String command, CustomGuiChat terminal) {
		//Let it rain
		terminal.getChatRegisterEntity().getWorld().setRainStrength( 2f);
		return ">> shield disabled <<\nPress 'm' to get back.";
	}

}
