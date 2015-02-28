package com.minespaceships.mod.spaceship;

import java.util.Vector;

import scala.collection.Iterator;
import net.minecraft.client.Minecraft;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

import com.minespaceships.mod.overhead.ChatRegisterEntity;

public class Shipyard {
	private Vector<Spaceship> ships;
	private static Shipyard singleton;
	
	protected Shipyard() {
		ships = new Vector<Spaceship>();
	}
	
	public static Shipyard getShipyard(){
		if(singleton == null){
			singleton = new Shipyard();
		}
		return singleton;
	}
	
	public void addShip(Spaceship ship){
		if(ship != null){
			if(!ships.contains(ship)){
				ships.add(ship);
			}
		}
	}
	public void removeShip(Spaceship ship){
		ships.remove(ship);
	}
	
	public Spaceship getShip(BlockPos pos, WorldServer world){
		for(Spaceship ship : ships){
			if(ship.containsBlock(pos) && ship.getWorld() == world){
				return ship;
			}
		}
		return null;
	}	
	
	@Deprecated
	public void createShip(BlockPos minSpan, final BlockPos origin, final BlockPos maxSpan, WorldServer worldS){
		addShip(new Spaceship(minSpan, origin, maxSpan, worldS));
	}
	public void createShip(BlockPos initial, WorldServer worldS) throws Exception{
		addShip(new Spaceship(initial, worldS));
	}
	
	/**
	 * Called to handle actions on block broken
	 * @param pos BlockPos
	 * @param world World
	 */
	public void blockBroken(final BlockPos pos, final World world) {
		if (ships.isEmpty()) return;
		
		for (Spaceship ship: ships) {
			if (ship.getWorld() == world) {
				if (ship.containsBlock(pos)) {
					ship.removeBlock(pos);
					break;
				}
			}
		}
	}
	
	/**
	 * Called to handle actions on block placed
	 * @param pos BlockPos
	 * @param world World
	 */
	public void blockPlaced(final BlockPos pos, final World world) {
		if (ships.isEmpty()) return;
		
		for (Spaceship ship: ships) {
			if (ship.getWorld() == world) {
				if (ship.isNeighboringBlock(pos)) {
					Minecraft.getMinecraft().thePlayer.sendChatMessage("Block is added");
					ship.addBlock(pos);
					break;
				}
			}
		}		
	}
	
	/**
	 * Return debugging information to player
	 * @param pos BlockPos
	 * @param world World
	 */
	public void getBlockInfo(final BlockPos pos, final World world) {
		if (ships.isEmpty()) {
			Minecraft.getMinecraft().thePlayer.sendChatMessage("false - no ships existing");
			return;
		}
		
		for (Spaceship ship: ships) {
			if (ship.containsBlock(pos)) {
				Minecraft.getMinecraft().thePlayer.sendChatMessage("block part of \""+ship.toString()+"\"");
				return;
			}
		}
		
		Minecraft.getMinecraft().thePlayer.sendChatMessage("block not part of a ship");
	}
}
