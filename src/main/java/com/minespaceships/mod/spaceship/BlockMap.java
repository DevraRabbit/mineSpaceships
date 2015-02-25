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
		resize(pos.subtract(origin));
	}
	public boolean contains(BlockPos pos){
		return map.containsKey(pos.subtract(origin));
	}
	public void remove(BlockPos pos){
		map.remove(pos.subtract(origin));
	}
	public BlockPos getMaxPos(){
		return maxPos.add(origin);
	}
	public BlockPos getMinPos(){
		return minPos.add(origin);
	}
	public ArrayList<BlockPos> getPositions(){
		ArrayList<BlockPos> positions = new ArrayList<BlockPos>();
		Set<BlockPos> keys = map.keySet();
		for(BlockPos pos : keys){
			positions.add(pos.add(origin));
		}
		return positions;
	}
	private void resize(BlockPos pos){
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
