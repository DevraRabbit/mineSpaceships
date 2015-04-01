package com.minespaceships.mod.menu.functionalMenus.energyMenus;

import com.minespaceships.mod.menu.FunctionalMenu;
import com.minespaceships.mod.menu.Menu;
import com.minespaceships.mod.overhead.CustomGuiChat;

public class EnergyManagementMenu extends Menu {

	public EnergyManagementMenu() {
		super("Energy Management");		
		addSubMenu(new DisplayShipStatsMenu());
		addSubMenu(new ActivateEnginesMenu());
		addSubMenu(new DeactivateEnginesMenu());
		addSubMenu(new ActivateShieldsMenu());
		addSubMenu(new DeactivateShieldsMenu());
		addSubMenu(new ActivatePhaserMenu());
		addSubMenu(new DeactivatePhaserMenu());
		addSubMenu(new BalanceEnergyMenu());
		addSubMenu(new DeactivateEnergyMenu());
	}
	
}
