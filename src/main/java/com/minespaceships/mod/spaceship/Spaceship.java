package com.minespaceships.mod.spaceship;

import javax.vecmath.Vector3d;

import com.minespaceships.util.BlockCopier;
import com.minespaceships.util.Vec3Op;

import net.minecraft.util.BlockPos;
import net.minecraft.util.Vec3;
import net.minecraft.util.Vec3i;
import net.minecraft.world.World;

public class Spaceship {
	private BlockPos minPosition;
	private BlockPos maxPosition;
	private BlockPos span;
	private BlockPos origin;
	private World world;
	
	public Spaceship(final BlockPos minPosition,final BlockPos maxPosition, World world){
		setMeasurements(minPosition, maxPosition);
		this.world = world;
	}
	public Spaceship(final BlockPos minPosition, int dimX, int dimY, int dimZ, World world){
		BlockPos recSpan = new BlockPos(dimX, dimY, dimZ);
		setMeasurements(minPosition, ((BlockPos) recSpan).add(minPosition));
		this.world = world;
	}
	public Spaceship(final BlockPos minSpan, final BlockPos origin, final BlockPos maxSpan, World world){
		setMeasurements(((BlockPos) minSpan).add(origin), ((BlockPos) maxSpan).add(origin));
		this.origin = origin;
		this.world = world;
	}
	
	private void setMeasurements(final BlockPos minPos, final BlockPos maxPos){
		minPosition = minPos;
		maxPosition = maxPos;
		span = ((BlockPos) maxPos).subtract(minPos);
		origin = Vec3Op.scale(span, 0.5);
	}
	
	public void copyTo(BlockPos addDirection){
		BlockPos add = new BlockPos(addDirection);
		for(int x = 0; x < span.getX(); x++){
			for(int y = 0; y < span.getY(); y++){
				for(int z = 0; z < span.getZ(); z++){
					BlockPos Pos = new BlockPos(x,y,z);
					Pos = Pos.add(minPosition);
					BlockCopier.copyBlock(world, Pos, Pos.add(add));
				}
			}
		}
	}
}
