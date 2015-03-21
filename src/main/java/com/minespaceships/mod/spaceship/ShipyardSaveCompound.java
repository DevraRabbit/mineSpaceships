package com.minespaceships.mod.spaceship;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.WorldSavedData;

public class ShipyardSaveCompound  extends WorldSavedData{
	public ShipyardSaveCompound(String name) {
		super(name);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		AllShipyards.readFromNBT(nbt);		
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		AllShipyards.writeToNBT(nbt);
	}

}
