package com.minespaceships.util;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockState;
import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class BlockCopier {
	public static void copyBlock(World worldIn, BlockPos origin, BlockPos target){
		copyBlock(worldIn, origin, target, true);
	}
	public static void copyBlock(World worldIn, BlockPos origin, BlockPos target, boolean copyEntity){
		worldIn.setBlockState(target, worldIn.getBlockState(origin), 2);	
		if(worldIn.getTileEntity(origin) != null){
			moveEntityInformation(worldIn.getTileEntity(origin), worldIn.getTileEntity(target));
		}
	}
	public static void moveEntityInformation(TileEntity entOrigin, TileEntity entTarget){
		if(entTarget.getClass() == entOrigin.getClass()){
			if(entTarget instanceof IInventory){
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
	public static void removeBlock(World worldIn, BlockPos target){
		worldIn.setBlockToAir(target);
	}
}
