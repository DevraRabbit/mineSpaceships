package com.minespaceships.mod.spaceshipcreation;

import java.util.ArrayList;
import java.util.Random;

import com.minespaceships.mod.spaceship.BlockMap;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public abstract class BuildElement {
	protected BlockPos min;
	protected BlockPos max;
	public boolean isFilling = true;
	public BuildElement(BlockPos min, BlockPos max){
		this.min = min;
		this.max = max;
	}
	public BlockPos getSpan(){
		return max.subtract(min);
	}
	public abstract BlockPos getRandomBuildPosition(Random random);	
	public abstract BlockMap getPositions();
	public abstract BuildElement getTransposedElement(BlockPos addDirection);
	public abstract boolean overlaps(BuildElement element);
}
