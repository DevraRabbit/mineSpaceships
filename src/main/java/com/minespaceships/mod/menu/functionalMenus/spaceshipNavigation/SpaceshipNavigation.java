package com.minespaceships.mod.menu.functionalMenus.spaceshipNavigation;

import com.minespaceships.mod.menu.Menu;
import com.minespaceships.mod.menu.moveForwardTurnMenu;

/**
 * Creates the spaceship navigation menu structure.
 * @author ovae.
 * @version 20150331.
 */
public class SpaceshipNavigation extends Menu{

	public SpaceshipNavigation() {
		super("Spaceship navigation");
		Menu moveForwad = new Menu("Move forward");
		Menu moveBack = new Menu("Move back");
		Menu moveLeft = new Menu("Move left");
		Menu moveRight = new Menu("Move right");
		Menu moveUp = new Menu("Move up");
		Menu moveDown = new Menu("Move down");

		moveForwad.addSubMenu(new MoveForwardMenu());
		moveBack.addSubMenu(new MoveBackMenu());
		moveLeft.addSubMenu(new MoveLeftMenu());
		moveRight.addSubMenu(new MoveRightMenu());
		moveUp.addSubMenu(new MoveUpMenu());
		moveDown.addSubMenu(new MoveDownMenu());

		addSubMenu(moveForwad);
		addSubMenu(moveBack);
		addSubMenu(moveLeft);
		addSubMenu(moveRight);
		addSubMenu(moveUp);
		addSubMenu(moveDown);
		addSubMenu(new moveForwardTurnMenu());
		addSubMenu(new LandingMenu());
		addSubMenu(new LiftoffMenu());
		addSubMenu(new StopMenu());
	}

}
