package com.minespaceships.util;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class BlockCopier {
	public static void copyBlock(World worldIn, BlockPos origin, BlockPos target){
		worldIn.setBlockState(target, worldIn.getBlockState(origin));
	}
	public static void removeBlock(World worldIn, BlockPos target){
		worldIn.setBlockToAir(target);
	}
}
