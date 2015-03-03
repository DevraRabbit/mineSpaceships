package com.minespaceships.mod.menu;

import com.minespaceships.mod.overhead.CustomGuiChat;

/**
 * this class displays a menu structure.
 * @author ovae.
 * @version 20150220
 */
public class MenuDisplay {

	/**
	 * 
	 */
	protected CustomGuiChat terminal;
	
	/**
	 * 
	 */
	private Menu root;
	
	/**
	 * 
	 */
	public MenuDisplay(final Menu root, final CustomGuiChat terminal){
		if(root == null){
			throw new IllegalArgumentException("root can not be null.");
		}
		if(terminal == null){
			throw new IllegalArgumentException("termianl can not be null.");
		}
		
		this.root = root;
		this.terminal = terminal;
	}

	

	/**
	 * 
	 * @param message
	 */
	public void display(final String command){
		terminal.display(root.switchMenu(command));
	}
}
