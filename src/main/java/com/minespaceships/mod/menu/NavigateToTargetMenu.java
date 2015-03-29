package com.minespaceships.mod.menu;

import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;

import com.minespaceships.mod.overhead.ChatRegisterEntity;
import com.minespaceships.mod.overhead.CustomGuiChat;
import com.minespaceships.mod.spaceship.Spaceship;

/**
 * 
 * @author ovae.
 * @version 20150221
 */
public class NavigateToTargetMenu extends Menu implements FunktionalMenu{

	//
	private CustomGuiChat terminal;
	
	/**
	 * 
	 * @param name
	 * @param terminal
	 */
	public NavigateToTargetMenu(final String name, final CustomGuiChat terminal, final String paramInfo) {
		super(name);
		this.terminal = terminal;
	}

	/**
	 * 
	 */
	@Override
	public String activate(String paramlist) {
		String[] param = paramlist.split("\\s+");
		double x = Double.parseDouble(param[0]);
		double y = Double.parseDouble(param[1]);
		double z = Double.parseDouble(param[2]);
		
		Spaceship ship = this.terminal.getChatRegisterEntity().getShip();
		//(double)x, (double)y, (double)z
		BlockPos position = new BlockPos(x, y, z);
		if(ship == null) {
			this.terminal.display("move: Please initialize the Spaceship first", terminal.getPlayerEntity(), true);
		}
		ship.setTarget(position);
		return "";
	}

}
