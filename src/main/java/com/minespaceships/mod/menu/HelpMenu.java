package com.minespaceships.mod.menu;

import com.minespaceships.mod.overhead.CustomGuiChat;

public class HelpMenu extends Menu{

	public HelpMenu(String name, CustomGuiChat terminal) {
		super(name, terminal);

	}
	
	/**
	 * 
	 */
	public void function(){
		String out="";
		out+="--Help--\n";
		out+="You can navigate in three different ways: \n";
		out+="  -by the name of the Menu \n";
		out+="  -by id and the number in brakets e.g. id2 \n";
		out+="  -by the number for the submenu order.\n";
		out+="\n To get back in the parent menu, you can either\n";
		out+="enter 'm','up' or 'parent'.\n\n";
		super.terminal.display(out);
		
	}
}
