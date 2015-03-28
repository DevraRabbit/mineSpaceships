package com.minespaceships.mod.target;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class PositionTarget extends Target{
	private BlockPos position;
	private static final String positionKey = "posTargetPosition";
	public PositionTarget(BlockPos position){
		this.position = position;
	}
	public PositionTarget(NBTTagCompound tag) {
		position = BlockPos.fromLong(tag.getLong(positionKey));
	}
	@Override
	public BlockPos getTarget(World world) {
		return position;
	}
	@Override
	public void writeToNBT(NBTTagCompound c) {
		c.setLong(positionKey, position.toLong());		
	}

}
