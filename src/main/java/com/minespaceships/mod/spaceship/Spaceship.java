package com.minespaceships.mod.spaceship;

import javax.vecmath.Vector3d;

import com.minespaceships.util.BlockCopier;
import com.minespaceships.util.Vec3Op;

import net.minecraft.util.BlockPos;
import net.minecraft.util.Vec3;
import net.minecraft.util.Vec3i;
import net.minecraft.world.World;

public class Spaceship {
	private Vec3i minPosition;
	private Vec3i maxPosition;
	private Vec3i span;
	private Vec3i origin;
	private World world;
	
	public Spaceship(final Vec3i minPosition,final Vec3i maxPosition, World world){
		setMeasurements(minPosition, maxPosition);
		this.world = world;
	}
	public Spaceship(final Vec3i minPosition, int dimX, int dimY, int dimZ, World world){
		Vec3i recSpan = new Vec3i(dimX, dimY, dimZ);
		setMeasurements(minPosition, ((BlockPos) recSpan).add(minPosition));
		this.world = world;
	}
	public Spaceship(final Vec3i minSpan, final Vec3i origin, final Vec3i maxSpan, World world){
		setMeasurements(((BlockPos) minSpan).add(origin), ((BlockPos) maxSpan).add(origin));
		this.origin = origin;
		this.world = world;
	}
	
	private void setMeasurements(final Vec3i minPos, final Vec3i maxPos){
		minPosition = minPos;
		maxPosition = maxPos;
		span = ((BlockPos) maxPos).subtract(minPos);
		origin = Vec3Op.scale(span, 0.5);
	}
	
	public void copyTo(Vec3i addDirection){
		BlockPos add = new BlockPos(addDirection);
		for(int x = 0; x < span.getX(); x++){
			for(int y = 0; y < span.getY(); y++){
				for(int z = 0; z < span.getZ(); z++){
					BlockPos Pos = new BlockPos(x,y,z);
					BlockCopier.copyBlock(world, Pos, Pos.add(add));
				}
			}
		}
	}
}
