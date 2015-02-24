package com.minespaceships.mod.spaceship;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import net.minecraft.util.BlockPos;

public class BlockMap {
	private HashMap<BlockPos, Boolean> map;
	private BlockPos maxPos;
	private BlockPos minPos;
	private BlockPos origin;
	public BlockMap(){
		map = new HashMap<BlockPos, Boolean>();
		maxPos = new BlockPos(0,0,0);
		minPos = new BlockPos(0,0,0);
	}
	public BlockPos getOrigin(){
		return origin;
	}
	public void setOrigin(BlockPos pos){
		origin = pos;
	}
	public void addCoordinate(BlockPos pos){
		map.put(pos.subtract(origin), true);
	}
	public boolean contains(BlockPos pos){
		return map.containsKey(pos.subtract(origin));
	}
	public void remove(BlockPos pos){
		map.remove(pos.subtract(origin));
	}
	public BlockPos getMaxPos(){
		return maxPos;
	}
	public BlockPos getMinPos(){
		return minPos;
	}
	public ArrayList<BlockPos> getPositions(){
		ArrayList<BlockPos> positions = new ArrayList<BlockPos>();
		Set<BlockPos> keys = map.keySet();
		for(BlockPos pos : keys){
			positions.add(pos.add(origin));
		}
		return positions;
	}
}
