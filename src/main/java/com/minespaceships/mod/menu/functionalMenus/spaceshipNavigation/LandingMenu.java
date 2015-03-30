package com.minespaceships.mod.menu.functionalMenus.spaceshipNavigation;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
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
		ArrayList<BlockPos> spaceshipBlocks = new ArrayList<BlockPos>();
		//ArrayList<BlockPos> spaceshipBottom = new ArrayList<BlockPos>();
		try{
			double x,y,z;
			World world = terminal.getChatRegisterEntity().getWorld();
			Spaceship ship = Shipyard.getShipyard(world).getShip(terminal.getChatRegisterEntity().getPos(), terminal.getChatRegisterEntity().getWorld());
			spaceshipBlocks = ship.getPositions();

			x = terminal.getChatRegisterEntity().getPos().getX();
			y = terminal.getChatRegisterEntity().getPos().getY();
			z = terminal.getChatRegisterEntity().getPos().getZ();
			BlockPos min = ship.getMinPos();

			/*
			//Get the bottom of the spaceship
			BlockPos temp = spaceshipBlocks.get(0);
			for(BlockPos blockOut: spaceshipBlocks){
				for(BlockPos blockIn: spaceshipBlocks){
					if(blockIn.getX() == blockOut.getX() && blockIn.getZ() == blockOut.getZ()){
						if(blockIn.getY() < temp.getY()){
							temp = blockIn;
						}
					}else{
						temp = blockOut;
					}
				}
				spaceshipBottom.add(temp);
				System.out.println("BOTTOM: "+temp);
			}*/

			//Check all blocks of the spaceship, how many air locks are under the ship.
			/*for(BlockPos block : spaceshipBlocks){
				int height=0;
				boolean run = true;
				boolean contains = false;
				int posY= block.getY()-1;
				IBlockState current;
				do{
					current = world.getBlockState(new BlockPos(x,posY,z));
					if(current == Blocks.air.getDefaultState()){
						run = true;
					}else{
						run = false;
					}
					if(spaceshipBlocks.contains(current)){
						run = false;
					}
					posY--;
					height++;
				}while(run);
				if(!contains){
					lowestBlocks.add(new BlockPos(block.getX(), posY ,block.getZ()));
				}
				contains = false;
			}*/


			for(BlockPos block : spaceshipBlocks){
				boolean run = true;
				boolean contains = false;
				int posY= block.getY();
				IBlockState current;
				while(run){
					current = world.getBlockState(new BlockPos(x, --posY ,z));
					if(!(current == Blocks.air.getDefaultState())){
						run = false;
					}
					if(spaceshipBlocks.contains(current)){
						run = false;
						contains = true;
					}
				}

				if(!contains){
					BlockPos b = new BlockPos(block.getX(), ++posY ,block.getZ());
					lowestBlocks.add(b);
					contains = false;
				}
			}

			BlockPos highest = lowestBlocks.get(0);
			BlockPos lowest = lowestBlocks.get(0);
			for(BlockPos block : lowestBlocks){
				if(block.getY() < lowest.getY()){
					lowest = block;
				}
				if(block.getY() > highest.getY()){
					highest = block;
				}
				System.out.println(""+block);
			}
			BlockPos origin = ship.getOrigin();
			int level = origin.getY()-min.getY();
			BlockPos position = new BlockPos(origin.getX(), (y-(highest.getY()-lowest.getY()))+level+2 , origin.getZ());
			ship.move(position);
			return "test"+ position+"Origin"+origin;

/*
			//Double check if a block is a part of the ship
			for(BlockPos block : lowestBlocks){
				if(spaceshipBlocks.contains(block)){
					lowestBlocks.remove(block);
					System.out.println("RMBlock : "+block+"");
				}else{
					System.out.println("Block : "+block+"");
				}
			}

			//Get highest block of all.
			BlockPos highest = lowestBlocks.get(0);
			for(BlockPos block : lowestBlocks){
				if(block.getY() >= highest.getY()){
					if(!spaceshipBlocks.contains(block)){
						highest = block;
					}
				}
			}

			BlockPos origin = ship.getOrigin();
			int level = origin.getY()-min.getY();
			if(level <=0){
				level*=1;
			}

			//(double)x, (double)y, (double)z
			BlockPos position = new BlockPos(x, highest.getY()+level+3 , z);
*/
/*
			if(spaceshipBlocks.contains(position)){
				String out ="";
				out = "Cant land\n"
						+"Origin  : "+ship.getOrigin()+"\n"
						+"Min     : "+ship.getMinPos()+"\n"
						+"Landing : "+highest+"\n"
						+"Postion : "+position;
				return out;
			}

			//Finally move the spaceship.
			ship.move(position);
			//return "land successful.\nPress 'm' to get back.";

			String out ="";
			out =	"Origin  : "+ship.getOrigin()+"\n"
					+"Min     : "+ship.getMinPos()+"\n"
					+"Landing : "+highest+"\n"
					+"Postion : "+position;
			return out;
*/
		}catch(ConcurrentModificationException f){
			return "ConcurrentModificationException: "+f.getStackTrace();
		}catch(Exception e){
			return "While trying to land an error occurred: "+e;
		}

	}

}
