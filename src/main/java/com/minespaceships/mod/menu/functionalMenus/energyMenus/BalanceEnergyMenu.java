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
import com.minespaceships.mod.spaceship.SpaceshipCommands.EnergyType;

/**
 * 
 * @author ..., ovae.
 * @version 20150402.
 */
public class BalanceEnergyMenu extends Menu implements FunctionalMenu {

	public BalanceEnergyMenu() {
		super("Balance Energy");
	}

	@Override
	public String activate(IMenuInterface terminal, String data) {
		//terminal.getChatRegisterEntity().onCommand(SpaceshipCommands.EnergyCommand(EnergyCommandType.balance), terminal.getPlayerEntity());
		Spaceship ship = TerminalUtil.getShip(terminal);
		if(ship != null){
			ship.balanceEnergy();
		}
		terminal.display(EnumChatFormatting.GREEN+"Using all available energy!\n", terminal.getPlayerEntity(), false);
		return "";
	}

	@Override
	public String getData() {
		return null;
	}

}
