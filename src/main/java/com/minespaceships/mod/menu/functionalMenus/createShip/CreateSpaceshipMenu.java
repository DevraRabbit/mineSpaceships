package com.minespaceships.mod.menu.functionalMenus.createShip;

import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

import com.minespaceships.mod.menu.FunctionalMenu;
import com.minespaceships.mod.menu.Menu;
import com.minespaceships.mod.overhead.IMenuInterface;
import com.minespaceships.mod.spaceship.Shipyard;
import com.minespaceships.mod.spaceship.Spaceship;
import com.minespaceships.mod.spaceship.SpaceshipCommands;

public class CreateSpaceshipMenu extends Menu implements FunctionalMenu{

	public CreateSpaceshipMenu() {
		super("Create Spaceship");
		//this.addSubMenu(new InitSpaceshipMenu());
	}

	@Override
	public String activate(IMenuInterface terminal, String data) {
		World world = terminal.getChatRegisterEntity().getWorld();

		terminal.getChatRegisterEntity().executeCommand(SpaceshipCommands.initAuto, null);
		Spaceship ship = Shipyard.getShipyard(world).getShip(terminal.getChatRegisterEntity().getPos(), terminal.getChatRegisterEntity().getWorld());
		if(ship != null){
			terminal.display(EnumChatFormatting.GREEN+">> Initialise spaceship successful<<\nPress 'Esc' and reopen the menu.", terminal.getPlayerEntity(), false);
		} else {
			terminal.display(EnumChatFormatting.RED+">> Ship too huge or connected to the ground.", terminal.getPlayerEntity(), false);
		}
		return "";
	}

}
