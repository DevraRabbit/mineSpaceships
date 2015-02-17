package com.minespaceships.mod.spaceship;

import javax.vecmath.Vector3d;

import com.minspaceships.util.BlockCopier;
import com.minspaceships.util.Vec3Op;

import net.minecraft.util.BlockPos;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class Spaceship {
	private Vec3 minPosition;
	private Vec3 maxPosition;
	private Vec3 span;
	private Vec3 origin;
	private World world;
	
	public Spaceship(final Vec3 minPosition,final Vec3 maxPosition, World world){
		setMeasurements(minPosition, maxPosition);
		this.world = world;
	}
	public Spaceship(final Vec3 minPosition, final double dimX, final double dimY, final double dimZ, World world){
		Vec3 recSpan = new Vec3(dimX, dimY, dimZ);
		setMeasurements(minPosition, recSpan.add(minPosition));
		this.world = world;
	}
	public Spaceship(final Vec3 minSpan, final Vec3 origin, final Vec3 maxSpan, World world){
		setMeasurements(minSpan.add(origin), maxSpan.add(origin));
		this.origin = origin;
		this.world = world;
	}
	
	private void setMeasurements(final Vec3 minPos, final Vec3 maxPos){
		minPosition = minPos;
		maxPosition = maxPos;
		span = maxPos.subtract(minPos);
		origin = Vec3Op.scale(span, 0.5);
	}
	
	public void copyTo(Vec3 addDirection){
		BlockPos add = new BlockPos(addDirection);
		for(int x = 0; x < span.xCoord; x++){
			for(int y = 0; y < span.yCoord; y++){
				for(int z = 0; z < span.zCoord; z++){
					BlockPos Pos = new BlockPos(x,y,z);
					BlockCopier.copyBlock(world, Pos, Pos.add(add));
				}
			}
		}
	}
}
