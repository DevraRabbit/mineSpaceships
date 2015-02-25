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

	public static void ninetyDeg(final World world, final BlockPos origin, final int leftRight) {
		if (world != null && origin != null && (leftRight == LEFT || leftRight == RIGHT)) {
			BlockPos source, target;
			for (int x = -10; x <= 10; x++) {
				for (int z = -10; z <= 10; z++) {
					source = new BlockPos(origin.getX() + x, origin.getY(), origin.getZ() + z);
					target = new BlockPos(origin.getX() - leftRight * z, origin.getY() + 1, origin.getZ() + leftRight * x);
					BlockCopier.copyBlock(world, source, target);
				}
			}
		} else {
			throw new IllegalArgumentException("Invalid argument(s)!");
		}
	}

	public static void around(final World world, final BlockPos origin) {
		if (world != null && origin != null) {
			BlockPos sourcePos, targetPos;
			Block targetBlock;
			IBlockState targetState;
			IProperty targetProp;
			EnumFacing facing, targetFacing;
			boolean hasRotated;
			for (int x = -10; x <= 10; x++) {
				for (int z = -10; z <= 10; z++) {
					sourcePos = new BlockPos(origin.getX() + x, origin.getY(), origin.getZ() + z);
					targetPos = new BlockPos(origin.getX() - x, origin.getY() + 1, origin.getZ() - z);
					// copy Blocks to new position
					BlockCopier.copyBlock(world, sourcePos, targetPos);
					// make calculations for turning turnable blocks such as pistons, pumpkins etc.
					targetState = world.getBlockState(targetPos);
					targetBlock = targetState.getBlock();
					targetProp = getFacingProperty(targetState);
					// make sure this is a block that can be rotated
					if (targetProp != null) {
						facing = changeValue(world, targetState, targetPos, targetProp, 2);
						targetFacing = (EnumFacing)targetState.getValue(targetProp);
						// player.addChatComponentMessage(new ChatComponentText("facing = " + facing.toString()));
						while (targetFacing.compareTo(facing) != 0) {
							// player.addChatComponentMessage(new ChatComponentText("compareTo = " + targetFacing.compareTo(facing)));
							world.setBlockState(targetPos, targetState.cycleProperty(targetProp));
							targetState = world.getBlockState(targetPos);
							targetProp = getFacingProperty(targetState);
							targetFacing = (EnumFacing)targetState.getValue(targetProp);
							// player.addChatComponentMessage(new ChatComponentText("targetFacing = " + targetFacing.toString()));
						}
					}
				}
			}
		} else {
			throw new IllegalArgumentException("Invalid argument(s)!");
		}
	}

	private static IProperty getFacingProperty(final IBlockState state) {
		java.util.Set<IProperty> propSet = (java.util.Set<IProperty>)state.getProperties().keySet();
		for (IProperty prop : propSet)
		{
			if (prop.getName().equals("facing"))
			{
				return prop;
			}
		}
		return null;
	}
	
	/**
	 * Changes the FACING value of a block.
	 * @param state current block state
	 * @param prop current FACING property
	 * @param steps Indicates, how many turning steps of 90 degrees are to be made (-1 for a left
	 *        turn, 1 for a right turn, 2 for a U-turn.
	 * @return the new FACING value
	 */
	private static EnumFacing changeValue(World world, IBlockState state, BlockPos pos, IProperty prop, int steps) {
		if (state != null && prop != null) {
			EnumFacing facing = (EnumFacing)state.getValue(prop);
			switch (facing) {
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

	private static EnumFacing direction(final int index) {
		switch(index) {
		case 0: return EnumFacing.NORTH;
		case 1: return EnumFacing.EAST;
		case 2: return EnumFacing.SOUTH;
		case 3: return EnumFacing.WEST;
		default: throw new IllegalArgumentException("Invalid direction index!");
		}
	}
}
