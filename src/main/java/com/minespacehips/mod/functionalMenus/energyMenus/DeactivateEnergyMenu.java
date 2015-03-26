package com.minespacehips.mod.functionalMenus.energyMenus;

import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

import com.minespaceships.mod.menu.FunctionalMenu;
import com.minespaceships.mod.menu.Menu;
import com.minespaceships.mod.overhead.CustomGuiChat;
import com.minespaceships.mod.spaceship.ShipInformation;
import com.minespaceships.mod.spaceship.Shipyard;
import com.minespaceships.mod.spaceship.Spaceship;

public class DeactivateEnergyMenu extends Menu implements FunctionalMenu {

	public DeactivateEnergyMenu() {
		super("Deactivate Energy");
	}

	@Override
	public String activate(String command, CustomGuiChat terminal) {
		Spaceship ship = TerminalUtil.getShip(terminal);
		if(ship != null){
			ship.deactivateEverything();
			return EnumChatFormatting.GREEN+"Shutting down.";
		} else {
			return EnumChatFormatting.RED+"No Spaceship\n";
		}
	}

}
