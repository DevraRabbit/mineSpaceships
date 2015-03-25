package com.minespaceships.util;

import net.minecraft.util.BlockPos;
import net.minecraft.util.Vec3;
import net.minecraft.util.Vec3i;

public class Vec3Op{
	public static Vec3 scale(Vec3 vec, double ratio){
		return new Vec3(vec.xCoord * ratio, vec.yCoord * ratio, vec.zCoord * ratio);
	}
	public static Vec3i scale(Vec3i vec, double ratio){
		return new Vec3i((double)vec.getX() * ratio, (double)vec.getY() * ratio, (double)vec.getY() * ratio);
	}
	public static BlockPos scale(BlockPos vec, double ratio){
		return new BlockPos((double)vec.getX() * ratio, (double)vec.getY() * ratio, (double)vec.getY() * ratio);
	}
	public static BlockPos subtract(BlockPos vec1, BlockPos vec2){
		return new BlockPos(vec1.getX() - vec2.getX(), vec1.getY() - vec2.getY(), vec1.getZ() - vec2.getZ());
	}
}
