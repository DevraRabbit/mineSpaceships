package com.minespaceships.mod.menu;

import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;

import com.minespaceships.mod.overhead.ChatRegisterEntity;
import com.minespaceships.mod.overhead.CustomGuiChat;
import com.minespaceships.mod.spaceship.Spaceship;

/**
 * 
 * @author ovae.
 * @version 20150220
 */
public class NavigateToTargetMenu extends Menu implements FunktionalMenu{

	/**
	 * 
	 * @param name
	 * @param terminal
	 */
	public NavigateToTargetMenu(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	@Override
	public void activate(String paramlist) {
		String[] param = paramlist.split("\\s+");
		double x = Double.parseDouble(param[0]);
		double y = Double.parseDouble(param[1]);
		double z = Double.parseDouble(param[2]);
		
		Spaceship ship = super.terminal.getChatRegisterEntity().getShip();
		//(double)x, (double)y, (double)z
		BlockPos position = new BlockPos(x, y, z);
		if(ship == null) {
			super.terminal.display("move: Please initialize the Spaceship first");
			return;
		}
		ship.setTarget(position);
	}

}
