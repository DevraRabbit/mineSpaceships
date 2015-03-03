package com.minespaceships.mod.spaceshipcreation;

import java.util.Random;

import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class SpaceshipCreator {
	public static void onCommand(String command, World worldIn, BlockPos pos){
		if(command.equals("build ship")){
			//createSpaceship(worldIn, pos.subtract(new BlockPos(10,10,10)), pos.add(new BlockPos(10,10,10)), 3, 0.2f, 0.9f, new Random(123));
		}
	}
	public static void FillElement(World worldIn, BuildElement element, int subsets, float splitChance, float symmetryChance, Random random){
		while(subsets > 0){
			BlockPos pos = element.getRandomBuildPosition(random);
			
		}
	}
	public static BuildElement getSmallerElement(BuildElement element, BlockPos pos, Random random){
		BlockPos span = element.max.subtract(element.min);
		BlockPos newSpan = new BlockPos((float)(span.getX())*random.nextFloat(), (float)(span.getX())*random.nextFloat(), (float)(span.getX())*random.nextFloat());
		return new RectangleBuildElement(
				new BlockPos(pos.getX()-newSpan.getX()/2,
				pos.getY()-newSpan.getY()/2,
				pos.getZ()-newSpan.getZ()/2),
				new BlockPos(pos.getX()+newSpan.getX()/2,
				pos.getY()+newSpan.getY()/2,
				pos.getZ()+newSpan.getZ()/2));
	}
}
