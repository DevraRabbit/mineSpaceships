package com.minespaceships.mod.menu.functionalMenus.spaceshipNavigation;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

import com.minespaceships.mod.CommandMessage;
import com.minespaceships.mod.MineSpaceships;
import com.minespaceships.mod.menu.FunctionalMenu;
import com.minespaceships.mod.menu.Menu;
import com.minespaceships.mod.menu.functionalMenus.energyMenus.TerminalUtil;
import com.minespaceships.mod.overhead.CustomGuiChat;
import com.minespaceships.mod.overhead.IMenuInterface;
import com.minespaceships.mod.spaceship.Shipyard;
import com.minespaceships.mod.spaceship.Spaceship;
import com.minespaceships.mod.spaceship.SpaceshipCommands;

/**
 * Lands the spaceship.
 * @author ovae.
 * @version 20150402.
 */
public class LandingMenu extends Menu implements FunctionalMenu{

	/**
	 * Creates a new landingMenu.
	 * @param name
	 */
	public LandingMenu() {
		super("Land");
	}

	/**
	 * The functionality of the menu is activated by this method.
	 * @param command
	 * @param terminal
	 */
	@Override
	public String activate(IMenuInterface terminal, String data) {
		Spaceship ship = TerminalUtil.getShip(terminal);
		if(ship != null){
			int distToGround = ship.getDistanceToGround();
			ship.setTarget(ship.getOrigin().add(0, -distToGround, 0));
			return EnumChatFormatting.GREEN+"Landing!";
		}
		return "No Spaceship...";
	}
	
	public String landShip(String command, IMenuInterface terminal){
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

			/* Checks if the block beneath every spaceship block is air or something different.
			 * If the block beneath the spaceship block is an other spaceship block nothing happens.
			 * Otherwise the block above the first block that is not air is added to the lowestBlock list.
			 */
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

			String out ="";
			out =	 ""+ship.getMaxPos()+" Max \n" 
					+""+ship.getOrigin()+" Origin \n"
					+""+ship.getMinPos()+" Min \n"
					+""+position+" Landing postion";
			terminal.display(out, terminal.getPlayerEntity(), false);
			return out;

		}catch(ConcurrentModificationException f){
			return "ConcurrentModificationException: "+f.getStackTrace();
		}catch(Exception e){
			return "While trying to land an error occurred: "+e;
		}
	}

	@Override
	public String getData() {
		// TODO Auto-generated method stub
		return null;
	}

}
