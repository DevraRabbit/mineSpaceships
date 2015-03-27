package com.minespaceships.mod.menu.functionalMenus.spaceshipNavigation;

import com.minespaceships.mod.menu.Menu;

public class SpaceshipNavigation extends Menu{

	public SpaceshipNavigation() {
		super("Spaceship navigation");
		Menu moveForwad = new Menu("move forward");
		Menu moveBack = new Menu("move back");
		Menu moveLeft = new Menu("move left");
		Menu moveRight = new Menu("move right");
		Menu moveUp = new Menu("move up");
		Menu moveDown = new Menu("move down");

		moveForwad.addSubMenu(new moveForwardMenu());
		moveBack.addSubMenu(new moveBackMenu());
		moveLeft.addSubMenu(new moveLeftMenu());
		moveRight.addSubMenu(new moveRightMenu());
		moveUp.addSubMenu(new moveUpMenu());
		moveDown.addSubMenu(new moveDownMenu());

		addSubMenu(moveForwad);
		addSubMenu(moveBack);
		addSubMenu(moveLeft);
		addSubMenu(moveRight);
		addSubMenu(moveUp);
		addSubMenu(moveDown);
		addSubMenu(new LandingMenu());
		addSubMenu(new liftoffMenu());
	}

}
