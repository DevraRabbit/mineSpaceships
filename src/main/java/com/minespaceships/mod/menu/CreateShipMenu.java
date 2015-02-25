package com.minespaceships.mod.menu;

import com.minespaceships.mod.overhead.CustomGuiChat;

public class CreateShipMenu extends Menu implements FunctionalMenu{

	CustomGuiChat terminal;
	
	public CreateShipMenu(String name, CustomGuiChat terminal) {
		super(name);
		if(terminal.equals(null)){
			throw new IllegalArgumentException("terminal can not be null.");
		}
		this.terminal=terminal;
	}

	@Override
	public String activate(String command) {
		try {
			this.terminal.getChatRegisterEntity().createShip( this.terminal.getChatRegisterEntity().getPos() , this.terminal.getChatRegisterEntity().getRemoteWorld());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ">> created ship failed <<\nPress 'm' to get back.";
		
	}

}
