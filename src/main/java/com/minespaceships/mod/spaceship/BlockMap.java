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
		map.put(pos, true);
	}
	public boolean contains(BlockPos pos){
		return map.containsKey(pos);
	}
	public void remove(BlockPos pos){
		map.remove(pos);
	}
}
