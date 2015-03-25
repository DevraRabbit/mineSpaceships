package com.minespaceships.mod.spaceship;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.WorldSavedData;

public class ShipyardSaveCompound  extends WorldSavedData{
	Shipyard shipyard;
	NBTTagCompound lastnbt;
	public ShipyardSaveCompound(String name) {
		super(name);
	}
	public void setShipyard(Shipyard shipyard){
		this.shipyard = shipyard;
		if(lastnbt != null){
			this.shipyard.readFromNBT(lastnbt);
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		if(shipyard != null){
			shipyard.readFromNBT(nbt);	
		} 
		lastnbt = nbt;
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		shipyard.writeToNBT(nbt);
	}

}
