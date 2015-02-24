package com.minespaceships.mod.spaceship;

import net.minecraft.util.BlockPos;

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
}
