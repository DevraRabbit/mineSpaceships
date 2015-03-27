package com.minespaceships.mod.target;

import java.util.Comparator;
import java.util.Random;

import com.minespaceships.util.Vec3Op;

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
	public float distanceSqTo(Target target){
		return (float) target.getTarget().distanceSq(getTarget());
	}
	
	public static class distanceComparator implements Comparator<Target>{
		private Target firstTarget;
		public distanceComparator(Target firstTarget) {
			this.firstTarget = firstTarget;
		}
		@Override
		public int compare(Target o1, Target o2) {
			float dist1 = o1.distanceSqTo(firstTarget);
			float dist2 = o2.distanceSqTo(firstTarget);
			if(dist1 > dist2){
				return 1;
			} else if (dist1 == dist2){
				return 0;
			}
			return -1;
		}
		
	}
}
