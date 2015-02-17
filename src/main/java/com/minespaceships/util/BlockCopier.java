package com.minespaceships.util;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class BlockCopier {
	public static void copyBlock(World worldIn, BlockPos origin, BlockPos target){
		worldIn.setBlockState(target, worldIn.getBlockState(origin));
		TileEntity ent = worldIn.getTileEntity(origin);
		if(ent != null){
			ent.setPos(target);
		}
	}
	public static void removeBlock(World worldIn, BlockPos target){
		worldIn.setBlockToAir(target);
	}
}
