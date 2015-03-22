package com.minespaceships.mod.spaceship;

import java.util.ArrayList;
import java.util.HashMap;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class AllShipyards {
	private static ArrayList<Shipyard> shipyards = new ArrayList<Shipyard>();
	private static HashMap<Integer, String> bufferedData = new HashMap<Integer, String>();
	public static Shipyard getShipyard(World world){
		for(Shipyard s : shipyards){
			if(s.getWorld() == world){
				return s;
			}
		}
		Shipyard s = new Shipyard(world);
		shipyards.add(s);
		int id = world.provider.getDimensionId();
		//Load buffered Data
		if(bufferedData.containsKey(id)){
			s.loadShips(bufferedData.get(id));
			bufferedData.remove(id);
		}
		return s;
	}
	
	public static void putData(int dimension, String Data){
		Shipyard syard = getShipyardById(dimension);
		if(syard != null){
			syard.loadShips(Data);
		} else {
			if(!bufferedData.containsKey(dimension)){
				bufferedData.put(dimension, "");
			}
			String prevData = bufferedData.get(dimension);
			if(!prevData.contains(Data)){
				prevData += Data;
			}
			bufferedData.put(dimension, Data);
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
	
	public static void readFromNBT(NBTTagCompound nbt) {
		for(Shipyard shipyard : shipyards){
			shipyard.readFromNBT(nbt);	
		}
	}

	public static void writeToNBT(NBTTagCompound nbt) {
		for(Shipyard shipyard : shipyards){
			shipyard.writeToNBT(nbt);
		}
	}
}
