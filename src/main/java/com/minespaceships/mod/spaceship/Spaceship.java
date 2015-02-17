package com.minespaceships.mod.spaceship;

import javax.vecmath.Vector3d;

import net.minecraft.util.Vec3;

public class Spaceship {
	private Vec3 minPosition;
	private Vec3 maxPosition;
	private Vec3 span;
	private Vec3 origin;
	
	public Spaceship(final Vec3 minPosition,final Vec3 maxPosition){
		setMeasurements(minPosition, maxPosition);
	}
	public Spaceship(final Vec3 minPosition, final double dimX, final double dimY, final double dimZ){
		Vec3 recSpan = new Vec3(dimX, dimY, dimZ);
		setMeasurements(minPosition, recSpan.add(minPosition));
	}
	public Spaceship(final Vec3 minSpan, final Vec3 origin, final Vec3 maxSpan){
		setMeasurements(minSpan.add(origin), maxSpan.add(origin));
		this.origin = origin;
	}
	
	private void setMeasurements(final Vec3 minPos, final Vec3 maxPos){
		minPosition = minPos;
		maxPosition = maxPos;
		span = maxPos.subtract(minPos);
		origin = ;
		Vector3d halfDist = (Vector3d)span.clone();
		halfDist.scale(1/2);
		origin.add(halfDist);
	}
	
	
}
