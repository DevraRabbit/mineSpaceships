package com.minespaceships.mod.spaceship;

import com.minespaceships.util.BlockCopier;

import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDirectional;
import net.minecraft.block.BlockLever;
import net.minecraft.block.BlockLever.EnumOrientation;
import net.minecraft.block.BlockLog;
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
	private static final int X_ORD = BlockLog.EnumAxis.X.ordinal();
	// --not needed yet-- private static final int Y_ORD = BlockLog.EnumAxis.Y.ordinal();
	private static final int Z_ORD = BlockLog.EnumAxis.Z.ordinal();
	// --not needed yet-- private static final int NONE_ORD = BlockLog.EnumAxis.NONE.ordinal();

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

	private static BlockLog.EnumAxis axis(final int index) {
		return BlockLog.EnumAxis.values()[index];
	}

	/**
	 * Changes the "rotatable" property value of a block.
	 * @param state current block state
	 * @param prop current "rotatable" property
	 * @param steps Indicates, how many turning steps of 90 degrees are to be made (Turn.LEFT for a left
	 *        turn, Turn.RIGHT for a right turn, Turn.AROUND for a U-turn, 0 for no turn).
	 * @return the new "rotatable" property value
	 */
	private static IStringSerializable changeValue(World world, IBlockState state, BlockPos pos, IProperty prop, int steps) {
		if (state == null) {
			throw new IllegalArgumentException("state must not be null!");
		} else if (prop == null) {
			throw new IllegalArgumentException("prop must not be null!");
		}		
		IStringSerializable currentFacing = (IStringSerializable)state.getValue(prop);
		return getNextFacing(currentFacing, steps);
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

	/**
	 * This method is only important for torches and other unstable blocks!
	 */
	public static EnumFacing getEnumFacing(final IBlockState state) {
		IProperty facing = getFacingProperty(state);
		if(facing != null) {
			if (facing.getName().equals("facing")) {
				if (state.getBlock() instanceof BlockLever) {
					return ((EnumOrientation)state.getValue(facing)).getFacing();
				} else {
					return (EnumFacing)state.getValue(facing);		
				}
			} else if (facing.getName().equals("rotation")){
				return EnumFacing.UP;
			}
		}
		return null;
	}

	/**
	 * Loads the whole set of properties from an IBlockState and searches it for a "rotatable" property.
	 * @return The "rotatable" property of the IBlockState if it exists. If not, null is returned.
	 */
	private static IProperty getFacingProperty(final IBlockState state) {
		java.util.Set<IProperty> propSet = (java.util.Set<IProperty>)state.getProperties().keySet();
		for (IProperty prop : propSet)
		{
			if (prop.getName().equals("facing") || prop.getName().equals("axis") || prop.getName().equals("rotation"))
			{
				return prop;
			}
		}
		// No "rotatable" property found means that the block cannot be turned.
		return null;
	}

	public static IStringSerializable getNextFacing(IStringSerializable currentFacing,  int steps){
		if (currentFacing != null) {
			steps = posMod4(steps);
			if (currentFacing instanceof EnumFacing) {
				switch ((EnumFacing)currentFacing) {
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
			} else if (currentFacing instanceof BlockLog.EnumAxis){
				/* If a log is rotated by 180 degrees or not at all, the facing axis stays the same (X or Z).
				 * If it is turned left or right by 90 degrees, it changes from X to Z (indices 0 and 2) or the other way round. */
				int axisOrd = ((BlockLog.EnumAxis) currentFacing).ordinal();
				return steps % 2 != 0 && (isHorizontal((BlockLog.EnumAxis)currentFacing)) ? axis(Z_ORD - axisOrd) : currentFacing;
			}
		}
		return null;
	}

	private static boolean isHorizontal(final BlockLog.EnumAxis axis) {
		return axis != null && (axis.equals(BlockLog.EnumAxis.X) || axis.equals(BlockLog.EnumAxis.Z));
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

	/**
	 * Calculates a rotated position of a block turned around an origin BlockPos and moved by a given vector.
	 * @param pos		Original absolute coordinates that are to be transformed.
	 * @param origin	Absolute coords around which the block is to be rotated.
	 * @param moveTo	Relative (!) vector by which the block is to be moved.
	 * 					Example: pos coords are 28 50 197 and moveTo coords are 10 0 -5.
	 * 					The target position of the Block (without rotating) would be 38 50 192 (28+10 50+0 197-5).
	 * @param dir		Direction index:
	 * 					0 = no rotation,
	 * 					Turn.LEFT = 90 degrees left,
	 * 					Turn.RIGHT = 90 degrees right,
	 * 					Turn.AROUND = 180 degrees.
	 * @return			The new absolute position after rotation and moving.
	 */
	public static BlockPos getRotatedPos(final BlockPos pos, final BlockPos origin, final BlockPos moveTo, final int dir) {
		Vec3 vecPos = new Vec3(pos.getX(), pos.getY(), pos.getZ());
		Vec3 vecOrigin = new Vec3(origin.getX(), origin.getY(), origin.getZ());
		Vec3 vecMoveTo = new Vec3(moveTo.getX(), moveTo.getY(), moveTo.getZ());		
		Vec3 rotation = getRotatedPos(vecPos, vecOrigin, vecMoveTo, dir);
		return new BlockPos(rotation.xCoord, rotation.yCoord, rotation.zCoord);
	}

	public static Vec3 getRotatedPos(final Vec3 pos, final Vec3 origin, final Vec3 moveTo, final int dir) {
		Vec3 sourcePos = pos.subtract(origin);
		Vec3 targetPos;
		switch(dir) {
		case LEFT:
		case RIGHT:
			targetPos = new Vec3(- sourcePos.zCoord * dir, sourcePos.yCoord, sourcePos.xCoord * dir);
			break;
		case AROUND:
			targetPos = new Vec3(-sourcePos.xCoord, sourcePos.yCoord, -sourcePos.zCoord);
			break;
		case 0:
			targetPos = sourcePos;
			break;
		default:
			throw new IllegalArgumentException("dir must Turn.LEFT, Turn.RIGHT, Turn.AROUND or 0!");
		}
		return targetPos.add(origin).add(moveTo);
	}

	/**
	 * Calculates i mod 4 with positive return values only (i.e. 0 to 3).
	 */
	private static int posMod4(int i) {
		while (i < 0) {
			i += 4;
		}
		return i % 4;
	}
	
	/**
	 * Rotates a block horizontally around itself by altering its BlockState.
	 * @param world	The world in which the block is located.
	 * @param pos	The current absolute position of the block.
	 * @param dir	The direction in which the block is to be rotated:
	 * 				0 = no rotation,
	 * 				Turn.LEFT = 90 degrees left,
	 * 				Turn.RIGHT = 90 degrees right,
	 * 				Turn.AROUND = 180 degrees.
	 */
	public static IBlockState turn(final World world, final BlockPos pos, final int dir) {
		if (world != null && pos != null) {
			Block block;
			IBlockState state;
			IProperty prop;

			// make calculations for rotating blocks such as pistons, pumpkins, logs etc.
			state = world.getBlockState(pos);
			block = state.getBlock();
			prop = getFacingProperty(state);
			// make sure this is a block that can be rotated
			if (prop != null) {
				if (!prop.getName().equals("rotation")) {
					// This section is for all Blocks with a "facing" or "axis" property (pumpkins, pistons, logs etc.)
					// Set "facing" property value to which a block shall be turned.
					IStringSerializable targetFacing = changeValue(world, state, pos, prop, dir);
					IStringSerializable facing = (IStringSerializable)state.getValue(prop);
					while (!facing.equals(targetFacing)) {
						state = state.cycleProperty(prop);
						prop = getFacingProperty(state);
						facing = (IStringSerializable)state.getValue(prop);
					}
				} else {
					// This section is for blocks with "rotation" properties (signs, banners etc.)
					Integer rotation = (Integer)state.getValue(prop);
					Integer targetRotation = (rotation + posMod4(dir) * 4) % 16;
					while ((int)rotation != (int)targetRotation) {
						state = state.cycleProperty(prop);
						rotation = (Integer)state.getValue(prop);
					}
				}
			}
			return state;
		}
		return null;
	}
}
