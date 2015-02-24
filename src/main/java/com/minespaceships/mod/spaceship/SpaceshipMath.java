package com.minespaceships.mod.spaceship;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;

import net.minecraft.util.BlockPos;
import net.minecraft.util.Vec3i;
import net.minecraft.world.World;

public class SpaceshipMath {
	public static BlockMap getConnectedPositions(BlockPos pos, World worldIn, int maxSize){
		BlockMap blockMap = new BlockMap();
		BlockPos origin = pos;
		blockMap.addCoordinate(pos.subtract(origin));		
		Vector<BlockPos> neighbors = new Vector<BlockPos>();
		neighbors.add(pos.subtract(origin));
		int[] safety = {maxSize};
		while(safety[0] >= 0 && neighbors.size() != 0){
			Vector<BlockPos> nextNeighbors = new Vector<BlockPos>();
			for(BlockPos nPos : neighbors){
				nextNeighbors = writeValidNeighbors(nPos, origin, worldIn, blockMap, nextNeighbors, safety);
			}
			neighbors.clear();
			neighbors.addAll(nextNeighbors);
		}
		if(safety[0] < 0){
			return null;
		} else {
			return blockMap;
		}
		
	}
	private static Vector<BlockPos> writeValidNeighbors(BlockPos pos, BlockPos origin, World worldIn, BlockMap blockMap, Vector<BlockPos> neighbors, int[] maxSize){
		maxSize[0]--;
		if(maxSize[0] >= 0){
			for(int x = -1; x < 2; x++){
				for(int y = -1; y < 2; y++){
					for(int z = -1; z < 2; z++){
						if(x != 0 && y != 0 && z != 0){
							BlockPos neighbor = pos.add(x,y,z);
							BlockPos originNeighbor = neighbor.subtract(origin);
							if(!blockMap.contains(originNeighbor) && !worldIn.isAirBlock(neighbor)){
								blockMap.addCoordinate(originNeighbor);
								neighbors.addElement(neighbor);
							}
						}
					}
				}
			}
		}
		return neighbors;
	}
	
}
