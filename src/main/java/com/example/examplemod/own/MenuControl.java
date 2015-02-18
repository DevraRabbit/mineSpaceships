package com.example.examplemod.own;

/*
 * Passes through the structure of the menu.
 * @author ovae
 * @version 2015-02-18
 */
public class MenuControl {

	/**
	 * The root menu witch contains all submenus.
	 */
	private Menu menuRoot;
	
	/**
	 * The parent menu of the current menu.
	 */
	private Menu parentMenu;
	
	/**
	 * The menu that the user currently has called.
	 */
	private Menu currentMenu;
	
	/**
	 * The command String that the user has entered.
	 */
	private String command;
	
	/**
	 * MenuControl Constructor, creates a MenuControl Object witch passes through the
	 * handed over menu.
	 * @param menu
	 */
	public MenuControl(Menu menu){
		this.menuRoot = menu;
		this.currentMenu = menu;
		this.command = "";
	}
	
	/**
	 * Go's through the menu structure.
	 * @param command
	 */
	public void control(String command){
		if(command.equals("up")){
			switchToParentMenu();
			currentMenu.display();
		}
		if(currentMenu.contains(command)){
		}
		
	}
	
	/**
	 * Switch from the current menu into it's parent menu.
	 */
	private void switchToParentMenu(){
		if(command.length()-1 > 1){
			command = command.substring(0, (command.length()-1));
		}
	}
	
}
