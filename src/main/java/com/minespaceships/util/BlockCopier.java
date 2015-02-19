package com.minespaceships.util;

import java.lang.reflect.Field;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.chunk.Chunk;

public class BlockCopier {
	public static void copyBlock(World worldIn, BlockPos origin, BlockPos target){
		copyBlock(worldIn, origin, target, true);
	}
	public static void copyBlock(World worldIn, BlockPos origin, BlockPos target, boolean copyEntity){
		setBlockState(target, worldIn.getBlockState(origin), 2, worldIn);	
		TileEntity ent = worldIn.getTileEntity(origin);
		if(ent != null){
			worldIn.removeTileEntity(origin);
			moveEntityInformationByReference(ent, worldIn.getTileEntity(target));
		}
	}
	public static void moveEntityInformation(TileEntity entOrigin, TileEntity entTarget){
		if(entTarget.getClass() == entOrigin.getClass()){
			if(entOrigin instanceof IMoveable){
				((IMoveable)entOrigin).moveInformation((IMoveable)entTarget);
			} 
			else if(entTarget instanceof IInventory){
				IInventory invOrigin = (IInventory)entOrigin;
				IInventory invTarget = (IInventory)entTarget;
				for(int i = 0; i < invOrigin.getSizeInventory(); i++){
					invTarget.setInventorySlotContents(i, invOrigin.getStackInSlot(i));
				}
			}			
		} else {
			throw new IllegalArgumentException("Not the same entity types!");
		}
	}
	public static void moveEntityInformationByReference(TileEntity entOrigin, TileEntity entTarget){
		if(entTarget.getClass() == entOrigin.getClass()){
			Field[] originFields = entOrigin.getClass().getDeclaredFields();
			for(Field field : originFields){
				field.setAccessible(true);
				try {
					field.set(entTarget, field.get(entOrigin));
				} 
				catch (IllegalArgumentException e) {}
				catch (IllegalAccessException e) {}
			}
		}
	}
	public static void removeBlock(World worldIn, BlockPos target){
		worldIn.setBlockToAir(target);
	}
	public static boolean setBlockState(BlockPos pos, IBlockState newState, int flags, World world)
    {
        if (false)//!world.isValid(pos))
        {
            return false;
        }
        else if (false)//!world.isRemote && world.worldInfo.getTerrainType() == WorldType.DEBUG_WORLD)
        {
            return false;
        }
        else
        {
            Chunk chunk = world.getChunkFromBlockCoords(pos);
            Block block = newState.getBlock();

            net.minecraftforge.common.util.BlockSnapshot blockSnapshot = null;
            if (world.captureBlockSnapshots && !world.isRemote)
            {
                blockSnapshot = net.minecraftforge.common.util.BlockSnapshot.getBlockSnapshot(world, pos, flags);
                world.capturedBlockSnapshots.add(blockSnapshot);
            }

            IBlockState iblockstate1 = chunk.setBlockState(pos, newState);

            if (iblockstate1 == null)
            {
                if (blockSnapshot != null) world.capturedBlockSnapshots.remove(blockSnapshot);
                return false;
            }
            else
            {
                Block block1 = iblockstate1.getBlock();

                if (block.getLightOpacity() != block1.getLightOpacity() || block.getLightValue() != block1.getLightValue())
                {
                	world.theProfiler.startSection("checkLight");
                	world.checkLight(pos);
                	world.theProfiler.endSection();
                }

                if (blockSnapshot == null) // Don't notify clients or update physics while capturing blockstates
                {
                	//world.markAndNotifyBlock(pos, chunk, iblockstate1, newState, flags); // Modularize client and physic updates
                }

                return true;
            }
        }
    }
}
