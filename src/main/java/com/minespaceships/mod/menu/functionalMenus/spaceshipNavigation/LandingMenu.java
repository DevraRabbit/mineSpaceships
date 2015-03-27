package com.minespaceships.mod.menu.functionalMenus.spaceshipNavigation;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

import com.minespaceships.mod.CommandMessage;
import com.minespaceships.mod.MineSpaceships;
import com.minespaceships.mod.menu.FunctionalMenu;
import com.minespaceships.mod.menu.Menu;
import com.minespaceships.mod.overhead.CustomGuiChat;
import com.minespaceships.mod.overhead.IMenuInterface;
import com.minespaceships.mod.spaceship.Shipyard;
import com.minespaceships.mod.spaceship.Spaceship;
import com.minespaceships.mod.spaceship.SpaceshipCommands;

/**
 * Lands the spaceship.
 * @author ovae.
 * @version 20150323.
 */
public class LandingMenu extends Menu implements FunctionalMenu{

	/**
	 * Creates a new landingMenu.
	 * @param name
	 */
	public LandingMenu() {
		super("land");
	}

	/**
	 * The functionality of the menu is activated by this method.
	 * @param command
	 * @param terminal
	 */
	@Override
	public String activate(String command, IMenuInterface terminal) {
		if(command.trim().isEmpty()){
			return "command can not be empty.";
		}
		if(command.equals(null)){
			return "command can not be null.";
		}

		ArrayList<BlockPos> lowestBlocks = new ArrayList<BlockPos>();
		ArrayList<BlockPos> spacehipBlocks = new ArrayList<BlockPos>();
		try{
			double x,y,z;
			World world = terminal.getChatRegisterEntity().getWorld();
			Spaceship ship = Shipyard.getShipyard(world).getShip(terminal.getChatRegisterEntity().getPos(), terminal.getChatRegisterEntity().getWorld());
			spacehipBlocks = ship.getPositions();

			x = terminal.getChatRegisterEntity().getPos().getX();
			y = terminal.getChatRegisterEntity().getPos().getY();
			z = terminal.getChatRegisterEntity().getPos().getZ();
			BlockPos minPos = ship.getMinPos();

			//Check all blocks of the spaceship, how many air locks are under the ship.
			for(BlockPos block : spacehipBlocks){
				int height=0;
				boolean run = true;
				int posY= block.getY()-2;
				IBlockState current;
				do{
					current = world.getBlockState(new BlockPos(x,posY,z));
					if(current == Blocks.air.getDefaultState()){
						run = true;
					}else{
						run = false;
					}
					posY--;
					height++;
				}while(run);
				if(!spacehipBlocks.contains(current)){
					lowestBlocks.add(new BlockPos(block.getX(), posY ,block.getZ()));
				}
			}

			//Get lowest block of all.
			BlockPos lowest = lowestBlocks.get(0);
			for(BlockPos block : lowestBlocks){
				if(!spacehipBlocks.contains(block)){
					if(block.getY() >= lowest.getY()){
						lowest = block;
					}
				}
			}
			BlockPos origin = ship.getOrigin();
			BlockPos min = ship.getMinPos();
			int level = origin.getY()-min.getY();
			if(level <=0){
				level*=1;
			}
			//(double)x, (double)y, (double)z
			BlockPos position = new BlockPos(x, lowest.getY()+level , z);
			ship.move(position);

			return "land successful.\nPress 'm' to get back.";
		}catch(Exception e){
			return "While trying to land an error occurred: "+e;
		}

	}

}
