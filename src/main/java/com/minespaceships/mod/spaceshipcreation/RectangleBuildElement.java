package com.minespaceships.mod.spaceshipcreation;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import com.minespaceships.mod.spaceship.BlockMap;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class RectangleBuildElement extends BuildElement {

	public RectangleBuildElement(BlockPos min, BlockPos max) {
		super(min, max);
	}

	@Override
	public BlockMap getPositions() {
		BlockMap map = new BlockMap(min);
		BlockPos span = max.subtract(min);
		for(int x = 0; x < span.getX(); x++){
			for(int y = 0; y < span.getY(); y++){
				for(int z = 0; z < span.getZ(); z++){
					map.add(min.add(new BlockPos(x,y,z)));
				}
			}
		}
		return map;
	}

	@Override
	public boolean overlaps(BuildElement element) {
		BlockMap thisPositions = getPositions();
		BlockMap elementPositions = element.getPositions();
		Iterator<BlockPos> it = elementPositions.getPositions().iterator();
		while(it.hasNext()){
			if(thisPositions.contains(it.next())){
				return true;
			}
		}
		return false;
	}

	@Override
	public BlockPos getRandomBuildPosition(Random random) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BuildElement getTransposedElement(BlockPos addDirection) {
		// TODO Auto-generated method stub
		return null;
	}
}
