package com.minespaceships.mod.spaceship;

import java.util.Vector;

import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

import com.minespaceships.mod.overhead.ChatRegisterEntity;

public class Shipyard {
	private Vector<Spaceship> ships;
	private Vector<ChatRegisterEntity> navigators;
	private static Shipyard singleton;
	
	protected Shipyard(){
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
		/*
		if(ship != null){
			if(!ships.contains(ship)){
				ships.add(ship);
				for(ChatRegisterEntity ent : navigators){
					if(SpaceshipMath.isPointInShip(ent.getPos(), ship)){
						ship.addNavigator(ent);
						if(ent.getShip() == null){
							ent.setShip(ship);
						}
					}
				}
			}
		}
		*/
	}
	
	public void addNavigator(ChatRegisterEntity entity){
		/*
		navigators.add(entity);
		for(Spaceship ship : ships){
			if(SpaceshipMath.isPointInShip(entity.getPos(), ship)){
				ship.addNavigator(entity);
			}
		}
		*/
	}
	
	public void removeNavigator(BlockPos entity){
		for(Spaceship ship : ships){
			ship.removeNavigator(entity);
			if(ship.getNavigatorCount() == 0){
				ships.remove(ship);
			}
		}
	}
	
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
	
	public void blockPlaced(final BlockPos pos, final World world) {
		if (ships.isEmpty()) return;
		
		for (Spaceship ship: ships) {
			if (ship.getWorld() == world) {
				if (ship.isNeighboringBlock(pos)) {
					ship.addBlock(pos);
					break;
				}
			}
		}		
	}
}
