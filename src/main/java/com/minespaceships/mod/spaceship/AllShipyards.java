package com.minespaceships.mod.spaceship;

import java.util.ArrayList;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class AllShipyards {
	private static ArrayList<Shipyard> shipyards = new ArrayList<Shipyard>();
	public static Shipyard getShipyard(World world){
		for(Shipyard s : shipyards){
			if(s.getWorld() == world){
				return s;
			}
		}
		Shipyard s = new Shipyard(world);
		shipyards.add(s);
		return s;
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
