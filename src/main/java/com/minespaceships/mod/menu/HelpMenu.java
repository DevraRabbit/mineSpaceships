package com.minespaceships.mod.menu;

import net.minecraft.util.EnumChatFormatting;

import com.minespaceships.mod.overhead.CustomGuiChat;

/**
 * This menu is the help menu witch explains to the user how to
 * use the menu structure and how to navigate in it.
 * @author ovae.
 * @version 20150221
 */
public class HelpMenu extends Menu implements FunctionalMenu{

	/**
	 * Help menu constructor, creates an help menu.
	 * @param name
	 * @param terminal
	 */
	public HelpMenu(String name) {
		super(name);
	}

	/**
	 * 
	 */
	@Override
	public String activate(String command) {
		String out="";
		out+=EnumChatFormatting.GOLD+" "+EnumChatFormatting.BOLD+"]--HELP--[\n\n"
		+"  "+EnumChatFormatting.YELLOW+"Menu structure:\n"
		+"    ]--Menuname (menu id)--[\n"
		+"        [position] sub menu name (sub menu id)\n"
		+'\n'
		+"  "+EnumChatFormatting.YELLOW+"Menu navigation:\n"
		+"   You can navigate in three different ways: \n"
		+"    -by the name of the Menu \n"
		+"    -by id and the number in brakets e.g. id2 \n"
		+"    -by the number for the submenu order.\n"
		+'\n'
		+"  "+EnumChatFormatting.YELLOW+"Menu colours:\n"
		+"    -Oragne: header\n"
		+"    -White : normal menus\n"
		+"    -Green:  menus with a functinality\n"
		+"    -Red: error"
		+'\n'
		+EnumChatFormatting.RED+"\n   To get back in the parent menu, you can either\n"
		+EnumChatFormatting.RED+"    enter 'm','up' or 'parent'.\n\n";
		return out;
	}

}
