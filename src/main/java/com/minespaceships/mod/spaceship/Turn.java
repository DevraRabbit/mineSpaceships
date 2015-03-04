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

/**
 * This class contains static methods to transform coordinates of a Block in order to turn it
 * around an origin coordinate. Furthermore, it turns blocks facing in a certain direction
 * around themselves (e.g. pumpkins, pistons, tree trunks [TO DO]).
 * 
 * @author Marcel
 *
 */
public class Turn {
	public static final int LEFT = -1;
	public static final int RIGHT = 1;
	public static final int AROUND = 2;

	@Deprecated
	public static void around(final World world, final BlockPos origin) {
		if (world != null && origin != null) {
			BlockPos sourcePos, targetPos;
			// [TO DO] for-loops are yet to be replaced by spaceship measuring array or something.
			for (int x = -2; x <= 2; x++) {
				for (int y = -4; y <= 0; y++) {
					for (int z = -2; z <= 2; z++) {
						sourcePos = new BlockPos(origin.getX() + x, origin.getY() + y, origin.getZ() + z);
						targetPos = new BlockPos(origin.getX() - x, origin.getY() + y + 8, origin.getZ() - z);
						BlockCopier.copyBlock(world, sourcePos, targetPos, AROUND);
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
	 * @param steps Indicates, how many turning steps of 90 degrees are to be made (Turn.LEFT for a left
	 *        turn, Turn.RIGHT for a right turn, Turn.AROUND for a U-turn, 0 for no turn).
	 * @return the new "facing" property value
	 */
	private static EnumFacing changeValue(World world, IBlockState state, BlockPos pos, IProperty prop, int steps) {
		if (state == null) {
			throw new IllegalArgumentException("state must not be null!");
		} else if (prop == null) {
			throw new IllegalArgumentException("prop must not be null!");
		}
		while (steps < 0) {
			steps += 4;
		}
		EnumFacing currentFacing = (EnumFacing)state.getValue(prop);
		return getNextFacing(currentFacing, steps);
	}
	public static EnumFacing getNextFacing(EnumFacing currentFacing,  int steps){
		while (steps < 0) {
			steps += 4;
		}
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
			return currentFacing;
		}
	}

	/**
	 * Turns an index back to a horizontal EnumFacing element.
	 */
	public static EnumFacing direction(final int index) {
		switch(index) {
		case 0: return EnumFacing.NORTH;
		case 1: return EnumFacing.EAST;
		case 2: return EnumFacing.SOUTH;
		case 3: return EnumFacing.WEST;
		default: throw new IllegalArgumentException("Invalid direction index!");
		}
	}

	public static EnumFacing getFacing(final IBlockState state){
		IProperty facing = getFacingProperty(state);
		if(facing != null){
			return (EnumFacing)state.getValue(facing);		
		} else {
			return null;
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

	@Deprecated
	public static void ninetyDeg(final World world, final BlockPos origin, final int leftRight) {
		if (world != null && origin != null && (leftRight == LEFT || leftRight == RIGHT)) {
			BlockPos sourcePos, targetPos;
			for (int x = -2; x <= 2; x++) {
				for (int y = -4; y <= 0; y++) {
					for (int z = -2; z <= 2; z++) {
						sourcePos = new BlockPos(origin.getX() + x, origin.getY() + y, origin.getZ() + z);
						targetPos = new BlockPos(origin.getX() - leftRight * z, origin.getY() + y + 8, origin.getZ() + leftRight * x);
						BlockCopier.copyBlock(world, sourcePos, targetPos, leftRight);
					}
				}
			}
		} else {
			throw new IllegalArgumentException("Invalid argument(s)!");
		}
	}

	public static BlockPos getRotatedPos(final World world, final BlockPos pos, final BlockPos origin, final BlockPos moveTo, final int dir) {
		BlockPos sourcePos = new BlockPos(pos.subtract(origin));
		BlockPos targetPos;
		switch(dir) {
		case LEFT:
		case RIGHT:
			targetPos = new BlockPos(- sourcePos.getZ() * dir, sourcePos.getY(), sourcePos.getX() * dir);
			break;
		case AROUND:
			targetPos = new BlockPos(-sourcePos.getX(), sourcePos.getY(), -sourcePos.getZ());
			break;
		default:
			throw new IllegalArgumentException("dir must be Turn.LEFT, Turn.RIGHT or Turn.AROUND!");
		}
		return targetPos.add(origin).add(moveTo);
	}

	public static void turn(final World world, final BlockPos pos, final int dir) {
		Block block;
		IBlockState state;
		IProperty prop;
		EnumFacing facing, targetFacing;

		// make calculations for turning turnable blocks such as pistons, pumpkins etc.
		state = world.getBlockState(pos);
		block = state.getBlock();
		prop = getFacingProperty(state);
		// make sure this is a block that can be rotated
		if (prop != null) {
			// Set "facing" property value to which a block shall be turned.
			targetFacing = changeValue(world, state, pos, prop, dir);
			facing = (EnumFacing)state.getValue(prop);
			while (facing.compareTo(targetFacing) != 0) {
				world.setBlockState(pos, state.cycleProperty(prop));
				state = world.getBlockState(pos);
				prop = getFacingProperty(state);
				facing = (EnumFacing)state.getValue(prop);
			}
		}
	}
}
