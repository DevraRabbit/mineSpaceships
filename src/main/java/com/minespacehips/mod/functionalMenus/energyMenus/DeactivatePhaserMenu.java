package com.minespacehips.mod.functionalMenus.energyMenus;

import net.minecraft.util.EnumChatFormatting;

import com.minespaceships.mod.menu.FunctionalMenu;
import com.minespaceships.mod.menu.Menu;
import com.minespaceships.mod.overhead.CustomGuiChat;
import com.minespaceships.mod.spaceship.Spaceship;

public class DeactivatePhaserMenu extends Menu implements FunctionalMenu {

	public DeactivatePhaserMenu() {
		super("Deactivate Phasers");
		// TODO Auto-generated constructor stub
	}

	@Override
	public String activate(String command, CustomGuiChat terminal) {
		Spaceship ship = TerminalUtil.getShip(terminal);
		if(ship != null){
			ship.deactivatePhasers();
			return EnumChatFormatting.GREEN+"Deactivating phasers!\n";
		} else {
			return EnumChatFormatting.RED+"No Spaceship\n";
		}
	}

}
