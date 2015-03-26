package com.minespacehips.mod.functionalMenus.energyMenus;

import net.minecraft.util.EnumChatFormatting;

import com.minespaceships.mod.menu.FunctionalMenu;
import com.minespaceships.mod.menu.Menu;
import com.minespaceships.mod.overhead.CustomGuiChat;
import com.minespaceships.mod.spaceship.Spaceship;

public class ActivatePhaserMenu extends Menu implements FunctionalMenu {

	public ActivatePhaserMenu() {
		super("Activate Phasers");
		// TODO Auto-generated constructor stub
	}

	@Override
	public String activate(String command, CustomGuiChat terminal) {
		Spaceship ship = TerminalUtil.getShip(terminal);
		if(ship != null){
			ship.activatePhasers();
			return EnumChatFormatting.GREEN+"Engaging phasers!\n";
		} else {
			return EnumChatFormatting.RED+"No Spaceship\n";
		}
	}

}
