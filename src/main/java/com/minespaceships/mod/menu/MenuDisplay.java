package com.minespaceships.mod.menu;

import java.util.ArrayList;

import com.minespaceships.mod.overhead.CustomGuiChat;

/**
 * This class displays a menu structure.
 * @author ovae.
 * @version 20150221
 */
public class MenuDisplay {

	//the terminal to write in.
	protected CustomGuiChat terminal;

	//The root of the menu structure.
	private Menu root;

	/**
	 * Creates a new MenuDisplay.
	 */
	public MenuDisplay(final Menu root, final CustomGuiChat terminal){
		if(root.equals(null)){
			throw new IllegalArgumentException("root can not be null.");
		}
		if(terminal.equals(null)){
			throw new IllegalArgumentException("termianl can not be null.");
		}

		this.root = root;
		this.terminal = terminal;
	}

	/**
	 * Get the currently selected menu and prepares the output of that menu.
	 * @param menu
	 */
	private String preparingOutput(final Menu menu){
		String out = "";
		if(menu == null){
			return "unknown command.\nPress 'm' to get back.";
		}
		if(menu instanceof FunktionalMenu){
			return ((FunktionalMenu)menu).activate("");
		}

		root.setSelectedMenu(menu);
		//add the menu name
		out += "]--"+(menu.getMenuName().toUpperCase())+" ("+menu.getMenuID()+")--[\n\n";
		//add all sub menus to the string.
		int position = 1;
		ArrayList<Menu> list = menu.getChildrenList();
		for(Menu child: list){
			out+= "    ["+position+"] "+child.getMenuName()+" ("+child.getMenuID()+")"+'\n';
			position++;
		}
		return out;
	}

	/**
	 * Displays the current selected menu.
	 * @param command
	 */
	public void display(final String command){
		terminal.display(preparingOutput(root.switchMenu(command)));
	}

	/**
	 * Displays the root menu.
	 * @param menu
	 */
	public void displayMain(final Menu menu){
		terminal.display(preparingOutput(menu));
	}
}
