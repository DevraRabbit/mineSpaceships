package com.minespaceships.mod.menu;

import com.minespaceships.mod.menu.functionalMenus.moveForwardTurn180Menu;
import com.minespaceships.mod.menu.functionalMenus.moveForwardTurn270Menu;
import com.minespaceships.mod.menu.functionalMenus.moveForwardTurn90Menu;

/**
 * Move the spaceship position forward.
 * @author ovae.
 * @version 20150323.
 */
public class moveForwardTurnMenu extends Menu {

	/**
	 * Creates a new moveForwardMenu.
	 * @param name
	 */
	public moveForwardTurnMenu(String name) {
		super(name);
		this.addSubMenu(new moveForwardTurn90Menu("move forward turn right"));
		this.addSubMenu(new moveForwardTurn180Menu("move forward turn around"));
		this.addSubMenu(new moveForwardTurn270Menu("move forward turn left"));	
		
	}

}