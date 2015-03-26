package com.minespaceships.mod.spaceship;

import java.util.ArrayList;
import java.util.HashMap;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class AllShipyards {
	private static ArrayList<Shipyard> shipyards = new ArrayList<Shipyard>();
	private static HashMap<Integer, HashMap<NBTTagCompound, String>> bufferedData = new HashMap<Integer, HashMap<NBTTagCompound, String>>();
	
	public static Shipyard getShipyard(World world){
		for(Shipyard s : shipyards){
			if(s.getWorld().provider.getDimensionId() == world.provider.getDimensionId()){
				//System.out.println("Got Shipyard with id "+  world.provider.getDimensionId()+ " out of " +shipyards.size()+ "Shipyards");
				return s;				
			}
		}
		System.out.println("Making new shipyard with id "+ world.provider.getDimensionId());
		Shipyard s = new Shipyard(world);
		shipyards.add(s);
		int id = world.provider.getDimensionId();
		//Load buffered Data
		if(bufferedData.containsKey(id)){
			System.out.println("Loading buffered data into new shipyard");
			s.loadShips(bufferedData.get(id));
			bufferedData.remove(id);
		}
		return s;
	}
	
	public static void putData(int dimension, NBTTagCompound compound, String key){
		Shipyard syard = getShipyardById(dimension);
		if(syard != null){
			System.out.println("Loaded recieved data directly into shipyard with id "+dimension);
			syard.loadShip(compound, key);
		} else {
			if(!bufferedData.containsKey(dimension)){
				bufferedData.put(dimension, new HashMap<NBTTagCompound, String>());
			}
			bufferedData.get(dimension).put(compound, key);
			System.out.println("Buffered shipdata on id "+dimension);
		}
	}
	public static Shipyard getShipyardById(int id){
		for(Shipyard syard : shipyards){
			if(syard.getWorld().provider.getDimensionId() == id){
				return syard;
			}
		}
		return null;
	}
	public static void clearAll(){
		for(Shipyard s: shipyards){
			s.clear();
		}
		shipyards.clear();
		bufferedData.clear();
	}
	
//	public static void readFromNBT(NBTTagCompound nbt) {
//		for(Shipyard shipyard : shipyards){
//			shipyard.readFromNBT(nbt);	
//		}
//	}
//
//	public static void writeToNBT(NBTTagCompound nbt) {
//		for(Shipyard shipyard : shipyards){
//			shipyard.writeToNBT(nbt);
//		}
//	}
}
