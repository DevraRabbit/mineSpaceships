package com.minespaceships.mod.target;

import java.util.Random;

import net.minecraft.util.BlockPos;

public abstract class Target {
	public abstract BlockPos getTarget();
	public BlockPos getDiversedTarget(BlockPos range){
		BlockPos target = getTarget();
		Random rand = new Random();
		return new BlockPos(target.getX()+(2*(float)range.getX()*rand.nextFloat()-range.getX()),
				target.getY()+(2*(float)range.getY()*rand.nextFloat()-range.getY()),
				target.getZ()+(2*(float)range.getZ()*rand.nextFloat()-range.getZ()));
	}
}
