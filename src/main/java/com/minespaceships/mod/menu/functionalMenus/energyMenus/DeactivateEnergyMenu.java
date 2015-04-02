package com.minespaceships.mod.menu.functionalMenus.energyMenus;

import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

import com.minespaceships.mod.menu.FunctionalMenu;
import com.minespaceships.mod.menu.Menu;
import com.minespaceships.mod.overhead.CustomGuiChat;
import com.minespaceships.mod.overhead.IMenuInterface;
import com.minespaceships.mod.spaceship.ShipInformation;
import com.minespaceships.mod.spaceship.Shipyard;
import com.minespaceships.mod.spaceship.Spaceship;
import com.minespaceships.mod.spaceship.SpaceshipCommands;
import com.minespaceships.mod.spaceship.SpaceshipCommands.EnergyCommandType;

/**
 * 
 * @author ..., ovae.
 * @version 20150402.
 */
public class DeactivateEnergyMenu extends Menu implements FunctionalMenu {

	public DeactivateEnergyMenu() {
		super("Deactivate Energy");
	}

	@Override
	public String activate(IMenuInterface terminal, String data) {
		terminal.getChatRegisterEntity().executeCommand(SpaceshipCommands.EnergyCommand(EnergyCommandType.shutdown), terminal.getPlayerEntity());
		terminal.display(EnumChatFormatting.GREEN+"Shutting down.\n", terminal.getPlayerEntity(), false);
		return "";
	}

	@Override
	public String getData() {
		// TODO Auto-generated method stub
		return null;
	}

}
