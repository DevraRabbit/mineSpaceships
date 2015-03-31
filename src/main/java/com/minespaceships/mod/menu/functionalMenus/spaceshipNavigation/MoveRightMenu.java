package com.minespaceships.mod.menu.functionalMenus.spaceshipNavigation;

import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

import com.minespaceships.mod.menu.FunctionalParamMenu;
import com.minespaceships.mod.menu.Menu;
import com.minespaceships.mod.overhead.CustomGuiChat;
import com.minespaceships.mod.overhead.IMenuInterface;
import com.minespaceships.mod.spaceship.Shipyard;
import com.minespaceships.mod.spaceship.Spaceship;

/**
 * Move the spaceship position to the right.
 * @author ovae.
 * @version 20150320.
 */
public class MoveRightMenu extends Menu implements FunctionalParamMenu{

	/**
	 * Creates a new moveRightMenu.
	 * @param name
	 */
	public MoveRightMenu() {
		super("Please type in how may blocks you want to move\n    right.    ");
	}

	/**
	 * The functionality of the menu is activated by this method.
	 * @param command
	 * @param terminal
	 */
	@Override
	public String activate(String command, IMenuInterface terminal) {
		int distance = Integer.parseInt(command);
		double x,y,z;
		World world = terminal.getChatRegisterEntity().getWorld();
		Spaceship ship = Shipyard.getShipyard(world).getShip(terminal.getChatRegisterEntity().getPos(), terminal.getChatRegisterEntity().getWorld());
		EnumFacing face = ship.getFacing();
		x = terminal.getChatRegisterEntity().getPos().getX();
		y = terminal.getChatRegisterEntity().getPos().getY();
		z = terminal.getChatRegisterEntity().getPos().getZ();

		BlockPos position;
		if(face == EnumFacing.NORTH){
			position = new BlockPos(x+distance, y,z);
		}
		else if(face == EnumFacing.SOUTH){
			position = new BlockPos(x-distance, y,z);
		}
		else if(face == EnumFacing.WEST){
			position = new BlockPos(x, y,z-distance);
		}
		else{
			position = new BlockPos(x, y,z+distance);
		}

		ship.move(position);
		return "";
	}

}