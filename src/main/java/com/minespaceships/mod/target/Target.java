package com.minespaceships.mod.target;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Comparator;
import java.util.Random;

import com.minespaceships.mod.menu.functionalMenus.targetMenus.SpaceshipTarget;
import com.minespaceships.util.Vec3Op;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public abstract class Target {
	public static String classKey = "Class";
	private BlockPos lastTarget;
	
	public BlockPos getTarget(World world){
		if(lastTarget != null){
			return lastTarget;
		} else {
			lastTarget = getNewTarget(world);
			return lastTarget;
		}
	}
	public abstract BlockPos getNewTarget(World world);
	public BlockPos getDiversedTarget(BlockPos range, World world){
		BlockPos target = getTarget(world);
		Random rand = new Random();
		return new BlockPos(target.getX()+(2*(float)range.getX()*rand.nextFloat()-range.getX()),
				target.getY()+(2*(float)range.getY()*rand.nextFloat()-range.getY()),
				target.getZ()+(2*(float)range.getZ()*rand.nextFloat()-range.getZ()));
	}
	public float distanceSqTo(Target target, World world){
		return (float) target.getTarget(world).distanceSq(getTarget(world));
	}
	
	public static class distanceComparator implements Comparator<Target>{
		private Target firstTarget;
		private World world;
		public distanceComparator(Target firstTarget, World world) {
			this.firstTarget = firstTarget;
			this.world = world;
		}
		@Override
		public int compare(Target o1, Target o2) {
			float dist1 = o1.distanceSqTo(firstTarget, world);
			float dist2 = o2.distanceSqTo(firstTarget, world);
			if(dist1 > dist2){
				return 1;
			} else if (dist1 == dist2){
				return 0;
			}
			return -1;
		}		
	}
	public abstract void writeToNBT(NBTTagCompound c);
	
	public static Target targetFromNBT(NBTTagCompound tag){
		String classname = tag.getString(classKey);
		if(classname.equals(PositionTarget.class.getName())){
			return new PositionTarget(tag);
		} else if (classname.equals(EntityTarget.class.getName())){
			return new EntityTarget(tag);
		} else if (classname.equals(SpaceshipTarget.class.getName())){
			return new SpaceshipTarget(tag);
		}
		return null;
	}
}
