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
public class DeactivatePhaserMenu extends Menu implements FunctionalMenu {

	public DeactivatePhaserMenu() {
		super("Deactivate Phasers");
		// TODO Auto-generated constructor stub
	}

	@Override
	public String activate(IMenuInterface terminal, String data) {
		terminal.getChatRegisterEntity().executeCommand(SpaceshipCommands.activateCommand(false, EnergyType.phaser), terminal.getPlayerEntity());
		terminal.display(EnumChatFormatting.GREEN+"Deactivating phaser.\n", terminal.getPlayerEntity(), false);
		return "";
	}

	@Override
	public String getData() {
		// TODO Auto-generated method stub
		return null;
	}

}
