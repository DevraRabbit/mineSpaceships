package com.minespaceships.mod.menu.functionalMenus.energyMenus;

import net.minecraft.util.EnumChatFormatting;

import com.minespaceships.mod.menu.FunctionalMenu;
import com.minespaceships.mod.menu.Menu;
import com.minespaceships.mod.overhead.CustomGuiChat;
import com.minespaceships.mod.overhead.IMenuInterface;
import com.minespaceships.mod.spaceship.Spaceship;
import com.minespaceships.mod.spaceship.SpaceshipCommands;
import com.minespaceships.mod.spaceship.SpaceshipCommands.EnergyType;

public class ActivateEnginesMenu extends Menu implements FunctionalMenu {

	public ActivateEnginesMenu() {
		super("Activate Engines");
		// TODO Auto-generated constructor stub
	}

	@Override
	public String activate(IMenuInterface terminal, String Data) {
		terminal.getChatRegisterEntity().executeCommand(SpaceshipCommands.activateCommand(true, EnergyType.engines), terminal.getPlayerEntity());
		return EnumChatFormatting.GREEN+"Turning up engines!\n";
	}

	@Override
	public String getData() {
		// TODO Auto-generated method stub
		return null;
	}

}
