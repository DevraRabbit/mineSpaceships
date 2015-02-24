package com.minespaceships.mod.spaceship;

import java.util.HashMap;

import net.minecraft.util.BlockPos;

public class BlockMap {
	HashMap<BlockPos, Boolean> map;
	BlockPos maxPos;
	BlockPos minPos;
	public BlockMap(){
		map = new HashMap<BlockPos, Boolean>();
		maxPos = new BlockPos(0,0,0);
		minPos = new BlockPos(0,0,0);
	}
	public void addCoordinate(BlockPos pos){
		expandCoodrinates(pos);
		map.put(pos, true);
	}
	public boolean contains(BlockPos pos){
		return map.containsKey(pos);
	}
	public void remove(BlockPos pos){
		map.remove(pos);
	}
	private void expandCoodrinates(BlockPos pos){
		if(maxPos.getX() < pos.getX()){
			maxPos = new BlockPos(pos.getX(), maxPos.getY(), maxPos.getZ());
		}
		if(maxPos.getY() < pos.getY()){
			maxPos = new BlockPos(maxPos.getX(), pos.getY(), maxPos.getZ());
		}
		if(maxPos.getZ() < pos.getZ()){
			maxPos = new BlockPos(maxPos.getX(), maxPos.getY(), pos.getZ());
		}
		if(minPos.getX() > pos.getX()){
			minPos = new BlockPos(pos.getX(), minPos.getY(), minPos.getZ());
		}
		if(minPos.getY() > pos.getY()){
			minPos = new BlockPos(minPos.getX(), pos.getY(), minPos.getZ());
		}
		if(minPos.getZ() > pos.getZ()){
			minPos = new BlockPos(minPos.getX(), minPos.getY(), pos.getZ());
		}
	}
}
