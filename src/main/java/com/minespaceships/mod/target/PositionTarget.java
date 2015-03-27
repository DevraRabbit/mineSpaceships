package com.minespaceships.mod.target;

import net.minecraft.util.BlockPos;

public class PositionTarget extends Target{
	private BlockPos position;
	public PositionTarget(BlockPos position){
		this.position = position;
	}
	@Override
	public BlockPos getTarget() {
		return position;
	}

}
