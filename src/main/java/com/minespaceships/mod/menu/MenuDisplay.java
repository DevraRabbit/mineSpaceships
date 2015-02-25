package com.minespaceships.mod.menu;

import java.util.ArrayList;

import net.minecraft.util.EnumChatFormatting;

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
	private String preparingOutput(final Menu menu, final String command){
		String out = "";
		if(menu == null){
			return "unknown command.\nPress 'm' to get back.";
		}
		if(menu instanceof FunctionalMenu){
			return ((FunctionalMenu)menu).activate(command);
		}

		root.setSelectedMenu(menu);
		//add the menu name
		out += EnumChatFormatting.BOLD.GOLD+"]--"+(menu.getMenuName().toUpperCase())+" ("+menu.getMenuID()+")--[\n\n";
		//add all sub menus to the string.
		int position = 1;
		ArrayList<Menu> list = menu.getChildrenList();
		for(Menu child: list){
			if(child instanceof FunctionalMenu || child instanceof FunctionalParamMenu){
				out+= "    "+EnumChatFormatting.GREEN+"["+position+"] "+child.getMenuName()+" ("+child.getMenuID()+")"+'\n';
			}else{
				out+= EnumChatFormatting.WHITE+"    ["+position+"] "+child.getMenuName()+" ("+child.getMenuID()+")"+'\n';
			}
			position++;
		}
		return out;
	}

	/**
	 * Displays the current selected menu.
	 * @param command
	 */
	public void display(final String command){
		if(command.trim().isEmpty()){
			terminal.display("unknown command.\nPress 'm' to get back.", true);
			return;
		}
		terminal.display(preparingOutput(root.switchMenu(command),command),true);
	}

	/**
	 * Displays the root menu.
	 * @param menu
	 */
	public void displayMain(final Menu menu){
		if(menu.equals(null)){
			throw new IllegalArgumentException("Menu can not be null.");
		}
		terminal.display(preparingOutput(menu, ""),true);
	}
}
