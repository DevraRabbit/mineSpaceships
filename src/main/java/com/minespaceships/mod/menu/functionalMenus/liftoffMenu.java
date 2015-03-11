package com.minespaceships.mod.menu.functionalMenus;

import net.minecraft.util.BlockPos;

import com.minespaceships.mod.menu.FunctionalParamMenu;
import com.minespaceships.mod.menu.Menu;
import com.minespaceships.mod.overhead.CustomGuiChat;
import com.minespaceships.mod.spaceship.Shipyard;
import com.minespaceships.mod.spaceship.Spaceship;

/**
 * Move the spaceship position up.
 * @author ovae.
 * @version 20150310.
 */
public class liftoffMenu extends Menu implements FunctionalParamMenu{

	public liftoffMenu(String name) {
		super(name);
	}

	@Override
	public String activate(String command, CustomGuiChat terminal) {
		try{
			double x,y,z;
			x = terminal.getChatRegisterEntity().getPos().getX();
			y = terminal.getChatRegisterEntity().getPos().getY()+20;
			z = terminal.getChatRegisterEntity().getPos().getZ();
			Spaceship ship = Shipyard.getShipyard().getShip(terminal.getChatRegisterEntity().getPos(), terminal.getChatRegisterEntity().getRemoteWorld());
			BlockPos position = new BlockPos(x, y, z);

			if(ship == null) {
				terminal.display("liftoff: Please initialise the Spaceship first", true);
			}
			ship.move(position);

			return ">> Liftoff <<";
		}catch(Exception e){
			System.err.println("ship is broken");
		}
		return "liftoff not implemented yet!";
	}

}
