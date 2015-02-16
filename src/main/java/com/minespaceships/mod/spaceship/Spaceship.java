package com.minespaceships.mod.spaceship;

import javax.vecmath.Vector3d;

import net.minecraft.util.Vec3;

public class Spaceship {
	private Vector3d minPosition;
	private Vector3d maxPosition;
	private Vector3d span;
	private Vector3d origin;
	
	public Spaceship(Vector3d minPosition, Vector3d maxPosition){
		setMeasurements(minPosition, maxPosition);
	}
	public Spaceship(Vector3d minPosition, double dimX, double dimY, double dimZ){
		Vector3d recSpan = new Vector3d(dimX, dimY, dimZ);
		recSpan.add(minPosition);
		setMeasurements(minPosition, recSpan);
	}
	public Spaceship(Vector3d minSpan, Vector3d origin, Vector3d maxSpan){
		Vector3d minPos = (Vector3d) minSpan.clone();
		minPos.add(origin);
		Vector3d maxPos = (Vector3d) maxSpan.clone();
		maxPos.add(origin);
		setMeasurements(minPos, maxPos);
		this.origin = (Vector3d) origin.clone();
	}
	
	private void setMeasurements(Vector3d minPos, Vector3d maxPos){
		minPosition = (Vector3d) minPos.clone();
		maxPosition = (Vector3d) maxPos.clone();
		span = (Vector3d) maxPosition.clone();
		span.sub(minPosition);
		origin = (Vector3d)maxPosition.clone();
		Vector3d halfDist = (Vector3d)span.clone();
		halfDist.scale(1/2);
		origin.add(halfDist);
	}
}
