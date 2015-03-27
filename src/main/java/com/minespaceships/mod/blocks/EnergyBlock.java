package com.minespaceships.mod.blocks;

import java.rmi.activation.ActivateFailedException;

import net.minecraft.block.Block;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.BlockRotatedPillar;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.World;

public class EnergyBlock extends ActivatableBlock {

	public EnergyBlock() {
		super();
		this.setUnlocalizedName("Energy Generator");
        this.setCreativeTab(CreativeTabs.tabMisc);
	}

	@Override
	public EnumWorldBlockLayer getBlockLayer() {
		return EnumWorldBlockLayer.TRANSLUCENT;
	}

	@Override
	public int getEnergy() {
		return 1;
	}
}
