package com.minespaceships.mod.spaceshipcreation;

import java.util.ArrayList;
import java.util.Random;

import com.minespaceships.util.Vec3Op;

import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class SpaceshipCreator {
	public static void onCommand(String command, World worldIn, BlockPos pos){
		if(command.equals("build ship")){
			//createSpaceship(worldIn, Vec3Op.subtract(pos, new BlockPos(10,10,10)), pos.add(new BlockPos(10,10,10)), 3, 0.2f, 0.9f, new Random(123));
		}
	}
	public static ArrayList<BuildElement> FillElement(World worldIn, BuildElement element, float subsetChance, float fillingChance, float symmetryChance, Random random){
		ArrayList<BuildElement> nextElements = new ArrayList<BuildElement>();
		while(random.nextFloat() < subsetChance){
			BlockPos pos = element.getRandomBuildPosition(random);
			BuildElement smallerElement = getSmallerElement(element, pos, random);
			if(random.nextFloat() < fillingChance){
				smallerElement.isFilling = true;
			} else {
				smallerElement.isFilling = false;
			}
			nextElements.add(smallerElement);
			if(random.nextFloat() < symmetryChance){
				//BlockPos symmetryPos = new BlockPos(element.getSpan().getX() - Vec3Op.subtract(pos, element.min).getX(), 0, 0);    DOES NOT WORK!
				//nextElements.add(smallerElement.getTransposedElement(symmetryPos));
			}
		}
		return nextElements;
	}
	public static BuildElement getSmallerElement(BuildElement element, BlockPos pos, Random random){
		BlockPos span = Vec3Op.subtract(element.max, element.min);
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
