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
	
	public void add(final BlockPos pos){
		map.put(pos, true);
	}
	
	public boolean contains(final BlockPos pos){
		return map.containsKey(pos);
	}
	
	public void remove(final BlockPos pos){
		map.remove(pos);
	}
	
	public boolean isNeighbor(final BlockPos pos) {
		for(int x = -1; x < 2; x++){
			for(int y = -1; y < 2; y++){
				for(int z = -1; z < 2; z++){
					if(x != 0 || y != 0 || z != 0){
						BlockPos neighbor = pos.add(x,y,z);
						if (this.contains(neighbor)) {
							return true;
						}
					}
				}
			}
		}

		return false;
	}
}
