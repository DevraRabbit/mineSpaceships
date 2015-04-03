package com.minespaceships.mod.menu.functionalMenus.createShip;

import com.minespaceships.mod.menu.Menu;

public class CreateSpaceshipMenu extends Menu{

	public CreateSpaceshipMenu() {
		super("Create Spaceship");
		this.addSubMenu(new InitSpaceshipMenu());
	}

}
