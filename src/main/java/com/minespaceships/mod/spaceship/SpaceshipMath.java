package com.minespaceships.mod.spaceship;

import java.util.ArrayList;

import net.minecraft.util.BlockPos;
import net.minecraft.util.Vec3i;
import net.minecraft.world.World;

public class SpaceshipMath {
	public static boolean isPointInShip(BlockPos pos, Spaceship ship){
		BlockPos minPos = ship.getMinPosition();
		BlockPos maxPos = ship.getMaxPosition();
		if(minPos.getX() <= pos.getX() &&
				minPos.getY() <= pos.getY() &&
				minPos.getZ() <= pos.getZ() &&
				maxPos.getX() > pos.getX() &&
				maxPos.getY() > pos.getY() &&
				maxPos.getZ() > pos.getZ()){
			return true;
		}
		return false;
	}
	/*
	public BlockMap getConnectedPositions(BlockPos pos, World worldIn){
		BlockMap blockMap = new BlockMap();
		BlockPos origin = pos;
		blockMap.addCoordinate(pos.subtract(origin));
		for(int x = -1; x < 2; x++){
			for(int y = -1; y < 2; y++){
				for(int z = -1; z < 2; z++){
					if(x != 0 && y != 0 && z != 0){
						BlockPos neighbor = pos.add(x,y,z);
						if(!blockMap.contains(neighbor) && !worldIn.isAirBlock(neighbor)){
							blockMap.addCoordinate(neighbor);
						}
					}
				}
			}
		}
	}
	*/
}
