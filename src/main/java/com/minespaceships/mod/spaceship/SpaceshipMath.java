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
		blockMap.setOrigin(pos);
		blockMap.addCoordinate(pos);		
		Vector<BlockPos> neighbors = new Vector<BlockPos>();
		neighbors.add(pos);
		int[] safety = {maxSize};
		while(safety[0] >= 0 && neighbors.size() != 0){
			Vector<BlockPos> nextNeighbors = new Vector<BlockPos>();
			for(BlockPos nPos : neighbors){
				nextNeighbors = writeValidNeighbors(nPos, worldIn, blockMap, nextNeighbors, safety);
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
	private static Vector<BlockPos> writeValidNeighbors(BlockPos pos, World worldIn, BlockMap blockMap, Vector<BlockPos> neighbors, int[] maxSize){
		maxSize[0]--;
		if(maxSize[0] >= 0){
			for(int x = -1; x < 2; x++){
				for(int y = -1; y < 2; y++){
					for(int z = -1; z < 2; z++){
						if(x != 0 || y != 0 || z != 0){
							BlockPos neighbor = pos.add(x,y,z);
							if(!blockMap.contains(neighbor)){
								if(!worldIn.isAirBlock(neighbor)){
									blockMap.addCoordinate(neighbor);
									neighbors.addElement(neighbor);
								}
							} else {
								int i = 0;
							}
						}
					}
				}
			}
		}
		return neighbors;
	}
	
}
