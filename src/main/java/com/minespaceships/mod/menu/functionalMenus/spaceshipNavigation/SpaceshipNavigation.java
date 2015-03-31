package com.minespaceships.mod.menu.functionalMenus.spaceshipNavigation;

import com.minespaceships.mod.menu.Menu;

/**
 * Creates the spaceship navigation menu structure.
 * @author ovae.
 * @version 20150331.
 */
public class SpaceshipNavigation extends Menu{

	public SpaceshipNavigation() {
		super("Spaceship navigation");
		Menu moveForwad = new Menu("move forward");
		Menu moveBack = new Menu("move back");
		Menu moveLeft = new Menu("move left");
		Menu moveRight = new Menu("move right");
		Menu moveUp = new Menu("move up");
		Menu moveDown = new Menu("move down");

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
		addSubMenu(new LandingMenu());
		addSubMenu(new LiftoffMenu());
	}

}
