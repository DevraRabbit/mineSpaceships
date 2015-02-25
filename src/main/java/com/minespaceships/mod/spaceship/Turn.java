package com.minespaceships.mod.spaceship;

import com.minespaceships.util.BlockCopier;

import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDirectional;
import net.minecraft.block.properties.*;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;

public class Turn {
	public static int LEFT = -1;
	public static int RIGHT = 1;
	public static int AROUND = 2;

	public static void around(final World world, final BlockPos origin) {
		if (world != null && origin != null) {
			BlockPos sourcePos, targetPos;
			// [TO DO] for-loops are yet to be replaced by spaceship measuring array or something.
			for (int x = -2; x <= 2; x++) {
				for (int y = -4; y <= 0; y++) {
					for (int z = -2; z <= 2; z++) {
						sourcePos = new BlockPos(origin.getX() + x, origin.getY() + y, origin.getZ() + z);
						targetPos = new BlockPos(origin.getX() - x, origin.getY() + y + 8, origin.getZ() - z);
						turn(world, sourcePos, targetPos, 2);
					}
				}
			}
		} else {
			throw new IllegalArgumentException("Invalid argument(s)!");
		}
	}

	/**
	 * Changes the "facing" property value of a block.
	 * @param state current block state
	 * @param prop current "facing" property
	 * @param steps Indicates, how many turning steps of 90 degrees are to be made (-1 for a left
	 *        turn, 1 for a right turn, 2 for a U-turn.
	 * @return the new "facing" property value
	 */
	private static EnumFacing changeValue(World world, IBlockState state, BlockPos pos, IProperty prop, int steps) {
		if (state != null && prop != null) {
			while (steps < 0) {
				steps += 4;
			}
			EnumFacing currentFacing = (EnumFacing)state.getValue(prop);
			switch (currentFacing) {
			case NORTH :
				return direction(steps % 4) ;
			case EAST :
				return direction((1 + steps) % 4);
			case SOUTH :
				return direction((2 + steps) % 4);
			case WEST :
				return direction((3 + steps) % 4);
			default:
				// UP and DOWN can be ignored since "before" and "after" look the same.
			}
		}
		throw new IllegalArgumentException("state and prop must not be null!");
	}

	/**
	 * Turns an index back to a horizontal EnumFacing element.
	 */
	private static EnumFacing direction(final int index) {
		switch(index) {
		case 0: return EnumFacing.NORTH;
		case 1: return EnumFacing.EAST;
		case 2: return EnumFacing.SOUTH;
		case 3: return EnumFacing.WEST;
		default: throw new IllegalArgumentException("Invalid direction index!");
		}
	}

	/**
	 * Loads the whole set of properties from an IBlockState and searches it for a "facing" property.
	 * @return The "facing" property of state if it exists. If not, null is returned.
	 */
	private static IProperty getFacingProperty(final IBlockState state) {
		java.util.Set<IProperty> propSet = (java.util.Set<IProperty>)state.getProperties().keySet();
		for (IProperty prop : propSet)
		{
			if (prop.getName().equals("facing"))
			{
				return prop;
			}
		}
		// No "facing" property found means that the block cannot be turned.
		return null;
	}

	public static void ninetyDeg(final World world, final BlockPos origin, final int leftRight) {
		if (world != null && origin != null && (leftRight == LEFT || leftRight == RIGHT)) {
			BlockPos sourcePos, targetPos;
			for (int x = -2; x <= 2; x++) {
				for (int y = -4; y <= 0; y++) {
					for (int z = -2; z <= 2; z++) {
						sourcePos = new BlockPos(origin.getX() + x, origin.getY() + y, origin.getZ() + z);
						targetPos = new BlockPos(origin.getX() - leftRight * z, origin.getY() + y + 8, origin.getZ() + leftRight * x);
						turn(world, sourcePos, targetPos, leftRight);
					}
				}
			}
		} else {
			throw new IllegalArgumentException("Invalid argument(s)!");
		}
	}

	private static void turn(final World world, final BlockPos sourcePos, final BlockPos targetPos, final int dir) {
		Block targetBlock;
		IBlockState targetState;
		IProperty targetProp;
		EnumFacing facing, targetFacing;

		// copy Blocks to new position
		BlockCopier.copyBlock(world, sourcePos, targetPos);
		// make calculations for turning turnable blocks such as pistons, pumpkins etc.
		targetState = world.getBlockState(targetPos);
		targetBlock = targetState.getBlock();
		targetProp = getFacingProperty(targetState);
		// make sure this is a block that can be rotated
		if (targetProp != null) {
			// Set "facing" property value to which a block shall be turned.
			facing = changeValue(world, targetState, targetPos, targetProp, dir);
			targetFacing = (EnumFacing)targetState.getValue(targetProp);
			while (targetFacing.compareTo(facing) != 0) {
				world.setBlockState(targetPos, targetState.cycleProperty(targetProp));
				targetState = world.getBlockState(targetPos);
				targetProp = getFacingProperty(targetState);
				targetFacing = (EnumFacing)targetState.getValue(targetProp);
			}
		}
	}
}
