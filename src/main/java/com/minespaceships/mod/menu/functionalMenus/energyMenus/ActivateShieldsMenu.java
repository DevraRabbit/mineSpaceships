package com.minespaceships.mod.menu.functionalMenus.energyMenus;

import net.minecraft.util.EnumChatFormatting;

import com.minespaceships.mod.menu.FunctionalMenu;
import com.minespaceships.mod.menu.Menu;
import com.minespaceships.mod.overhead.CustomGuiChat;
import com.minespaceships.mod.overhead.IMenuInterface;
import com.minespaceships.mod.spaceship.Spaceship;
import com.minespaceships.mod.spaceship.SpaceshipCommands;
import com.minespaceships.mod.spaceship.SpaceshipCommands.EnergyType;

/**
 * 
 * @author ..., ovae.
 * @version 20150402.
 */
public class ActivateShieldsMenu extends Menu implements FunctionalMenu {

	public ActivateShieldsMenu() {
		super("Activate Shields");
	}

	@Override
	public String activate(IMenuInterface terminal, String data) {
		terminal.getChatRegisterEntity().executeCommand(SpaceshipCommands.activateCommand(true, EnergyType.shields), terminal.getPlayerEntity());
		terminal.display(EnumChatFormatting.GREEN+"Activating shields!\n", terminal.getPlayerEntity(), false);
		return "";
	}

	@Override
	public String getData() {
		return null;
	}

}
