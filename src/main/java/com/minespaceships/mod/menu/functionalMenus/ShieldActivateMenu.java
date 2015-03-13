package com.minespaceships.mod.menu.functionalMenus;

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
 * A menu for the shield activate functionality.
 * @author ovae.
 * @version 20150312.
 */
public class ShieldActivateMenu extends Menu implements FunctionalMenu{

	/**
	 * Creates a new menu, for the shield activate functionality.
	 * @param name
	 */
	public ShieldActivateMenu(String name) {
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
			World world = terminal.getChatRegisterEntity().getWorld();
			Spaceship ship = Shipyard.getShipyard().getShip(terminal.getChatRegisterEntity().getPos(), terminal.getChatRegisterEntity().getWorld());
			if(ship == null) {
				terminal.display("move: Please initialise the Spaceship first", true);
			}
			BlockPos maxPos = ship.getMaxPos();
			BlockPos minPos = ship.getMinPos();

			//IBlockState shieldBlock = Blocks.barrier.getDefaultState();
			IBlockState shieldBlock = Blocks.glass.getDefaultState();

			int abstand = 2;
			//Right shield side
			for(int i=minPos.getX()-abstand; i<maxPos.getX()+abstand; i++){
				for(int j=minPos.getY()-abstand; j<maxPos.getY()+abstand; j++){
					world.setBlockState(new BlockPos(i,j,maxPos.getZ()+abstand), shieldBlock);
				}
			}

			//Left shield side
			for(int i=minPos.getX()-abstand; i<maxPos.getX()+abstand; i++){
				for(int j=minPos.getY()-abstand; j<maxPos.getY()+abstand+1; j++){
					world.setBlockState(new BlockPos(i,j,minPos.getZ()-abstand), shieldBlock);
				}
			}

			//roof shield side
			for(int i=minPos.getX()-abstand; i<maxPos.getX()+abstand+1; i++){
				for(int j=minPos.getZ()-abstand; j<maxPos.getZ()+abstand+1; j++){
					world.setBlockState(new BlockPos(i,maxPos.getY()+abstand,j), shieldBlock);
				}
			}

			//bottom shield side
			for(int i=minPos.getX()-abstand; i<maxPos.getX()+abstand+1; i++){
				for(int j=minPos.getZ()-abstand; j<maxPos.getZ()+abstand+1; j++){
					world.setBlockState(new BlockPos(i,minPos.getY()-abstand-1,j), shieldBlock);
				}
			}

			//front shield side
			for(int i=minPos.getY()-abstand; i<maxPos.getY()+abstand; i++){
				for(int j=minPos.getZ()-abstand; j<maxPos.getZ()+abstand+1; j++){
					world.setBlockState(new BlockPos(maxPos.getX()+abstand,i,j), shieldBlock);
				}
			}

			//back shield side
			for(int i=minPos.getY()-abstand; i<maxPos.getY()+abstand; i++){
				for(int j=minPos.getZ()-abstand; j<maxPos.getZ()+abstand+1; j++){
					world.setBlockState(new BlockPos(minPos.getX()-abstand,i,j), shieldBlock);
				}
			}

			return ">> shield activated <<\nPress 'm' to get back.";
		}catch(Exception e){
			System.err.println("ship is broken");
		}
		return "shield activate failed!\nPress 'm' to get back.";
		/*
		terminal.getChatRegisterEntity().getWorld().setRainStrength( 0f);
		return ">> shield activated <<\nPress 'm' to get back.";
		*/
	}

}