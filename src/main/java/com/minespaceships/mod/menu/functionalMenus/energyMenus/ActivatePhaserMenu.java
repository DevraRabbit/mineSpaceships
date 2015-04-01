package com.minespaceships.mod.menu.functionalMenus.energyMenus;

import net.minecraft.util.EnumChatFormatting;

import com.minespaceships.mod.menu.FunctionalMenu;
import com.minespaceships.mod.menu.Menu;
import com.minespaceships.mod.overhead.CustomGuiChat;
import com.minespaceships.mod.overhead.IMenuInterface;
import com.minespaceships.mod.spaceship.Spaceship;
import com.minespaceships.mod.spaceship.SpaceshipCommands;
import com.minespaceships.mod.spaceship.SpaceshipCommands.EnergyType;

public class ActivatePhaserMenu extends Menu implements FunctionalMenu {

	public ActivatePhaserMenu() {
		super("Activate Phasers");
		// TODO Auto-generated constructor stub
	}

	@Override
	public String activate(String command, IMenuInterface terminal) {
		terminal.getChatRegisterEntity().executeCommand(SpaceshipCommands.activateCommand(true, EnergyType.phaser), terminal.getPlayerEntity(), true);
		return EnumChatFormatting.GREEN+"Engaging phaser!\n";
	}

}
