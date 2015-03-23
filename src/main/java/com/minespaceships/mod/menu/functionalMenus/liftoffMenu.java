package com.minespaceships.mod.menu.functionalMenus;

import net.minecraft.util.BlockPos;

import com.minespaceships.mod.menu.FunctionalMenu;
import com.minespaceships.mod.menu.FunctionalParamMenu;
import com.minespaceships.mod.menu.Menu;
import com.minespaceships.mod.overhead.CustomGuiChat;
import com.minespaceships.mod.spaceship.Shipyard;
import com.minespaceships.mod.spaceship.Spaceship;

/**
 * Move the spaceship position up.
 * @author ovae.
 * @version 20150312.
 */
public class liftoffMenu extends Menu implements FunctionalMenu{

	/**
	 * Creates a new liftoffMenu.
	 * @param name
	 */
	public liftoffMenu(String name) {
		super(name);
	}

	/**
	 * The functionality of the menu is activated by this method.
	 * @param command
	 * @param terminal
	 */
	@Override
	public String activate(String command, CustomGuiChat terminal) {
		try{
			double x,y,z;
			x = terminal.getChatRegisterEntity().getPos().getX();
			y = terminal.getChatRegisterEntity().getPos().getY()+20;
			z = terminal.getChatRegisterEntity().getPos().getZ();
			Spaceship ship = Shipyard.getShipyard(terminal.getChatRegisterEntity().getWorld()).getShip(terminal.getChatRegisterEntity().getPos(), terminal.getChatRegisterEntity().getWorld());
			BlockPos position = new BlockPos(x, y, z);

			if(ship == null) {
				terminal.display("liftoff: Please initialise the Spaceship first", true);
			}
			ship.move(terminal.getChatRegisterEntity(), position);
			return ">> Liftoff <<\nPress 'm' to get back.";
		}catch(Exception e){
			System.err.println("ship is broken");
		}
		return "liftoff failed!";
	}

}
