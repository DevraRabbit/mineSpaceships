package com.minespaceships.mod.menu.functionalMenus.spaceshipNavigation;

import com.minespaceships.mod.menu.Menu;

/**
 * Move the spaceship position forward.
 * @author ovae.
 * @version 20150323.
 */
public class MoveForwardTurnMenu extends Menu {

	/**
	 * Creates a new moveForwardMenu.
	 * @param name
	 */
	public MoveForwardTurnMenu() {
		super("Move Rotated");
		this.addSubMenu(new MoveForwardTurn90Menu());
		this.addSubMenu(new MoveForwardTurn180Menu());
		this.addSubMenu(new MoveForwardTurn270Menu());	
		
	}

}