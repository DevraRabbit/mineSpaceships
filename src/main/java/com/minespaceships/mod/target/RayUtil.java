package com.minespaceships.mod.target;

import java.util.List;

import net.minecraft.block.material.Material;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class RayUtil {
	public static Vec3 getHit(Vec3 source, Vec3 direction, List<Material> pass, int maxRange, World world){
		Vec3 current = source;
		Vec3 dir = direction.normalize();
		while(pass.contains(world.getBlockState(new BlockPos(current)).getBlock().getMaterial())){
			current =current.add(dir);
		}
		return current;
	}
	public static Vec3 getTransparentHit(Vec3 source, Vec3 direction, int maxRange, World world){
		Vec3 current = source;
		Vec3 dir = direction.normalize();
		while(world.getBlockState(new BlockPos(current)).getBlock().isTranslucent()){
			current =current.add(dir);
		}
		return current;
	}
//	public static BlockPos getPreviousBlock(Vec3 source, Vec3 direction, List<Material> pass, int maxRange, World world){
//		Vec3 current = getHit(source, direction, pass, maxRange, world);
//		return new BlockPos(current.subtract(direction.normalize()));
//	}
}
