package com.minespaceships.mod.spaceship;

import java.util.Vector;

import scala.collection.Iterator;
import net.minecraft.client.Minecraft;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

import com.minespaceships.mod.overhead.ChatRegisterEntity;

public class Shipyard {
	private Vector<Spaceship> ships;
	private Vector<ChatRegisterEntity> navigators;
	private static Shipyard singleton;
	
	protected Shipyard() {
		ships = new Vector<Spaceship>();
		navigators = new Vector<ChatRegisterEntity>();
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
				for(ChatRegisterEntity ent : navigators){
					if(ship.containsBlock(ent.getPos())){
						ship.addNavigator(ent);
						if(ent.getShip() == null){
							ent.setShip(ship);
						}
					}
				}
			}
		}
	}
	
	public void addNavigator(ChatRegisterEntity entity){
		if(entity != null && !navigators.contains(entity)){
			navigators.add(entity);
			for(Spaceship ship : ships){
				if(ship.isNeighboringBlock(entity.getPos())){
					ship.addNavigator(entity);
				}
			}
		}
	}
	
	public void removeNavigator(ChatRegisterEntity entity){
		navigators.remove(entity);
		for(java.util.Iterator<Spaceship> shipIt = ships.iterator(); shipIt.hasNext();){
			Spaceship ship = shipIt.next();
			if(ship.getNavigatorCount() == 0 && ship.canBeRemoved()){
				ship.removeNavigator(entity);
				shipIt.remove();
			}
		}
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
