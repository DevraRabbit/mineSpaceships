package com.minespaceships.mod.menu.functionalMenus;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

import com.minespaceships.mod.menu.FunctionalMenu;
import com.minespaceships.mod.menu.Menu;
import com.minespaceships.mod.overhead.CustomGuiChat;
import com.minespaceships.mod.spaceship.Shipyard;
import com.minespaceships.mod.spaceship.Spaceship;

/**
 * Lands the spaceship.
 * @author ovae.
 * @version 20150312.
 */
public class landingMenu extends Menu implements FunctionalMenu{

	/**
	 * Creates a new landingMenu.
	 * @param name
	 */
	public landingMenu(String name) {
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
			World world = terminal.getChatRegisterEntity().getWorld();
			Spaceship ship = Shipyard.getShipyard().getShip(terminal.getChatRegisterEntity().getPos(), terminal.getChatRegisterEntity().getWorld());
			if(ship == null) {
				terminal.display("move: Please initialise the Spaceship first", true);
			}
			x = terminal.getChatRegisterEntity().getPos().getX();
			y = terminal.getChatRegisterEntity().getPos().getY();
			z = terminal.getChatRegisterEntity().getPos().getZ();
			BlockPos minPos = ship.getMinPos();

			int height=0;
			boolean run = true;
			int posY= minPos.getY()-1;
			do{
				IBlockState current = world.getBlockState(new BlockPos(x,posY,z));
				if(current == Blocks.air.getDefaultState()){
					run = true;
				}else{
					run = false;
				}
				posY--;
				height++;
			}while(run);

			//(double)x, (double)y, (double)z
			BlockPos position = new BlockPos(x, y-height+2, z);
			ship.move(position);

			return ">> landing <<\nPress 'm' to get back.";
		}catch(Exception e){
			System.err.println("ship is broken");
		}

		return "landing failed!\nPress 'm' to get back.";
	}

}
