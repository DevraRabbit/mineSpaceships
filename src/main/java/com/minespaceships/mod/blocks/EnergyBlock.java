package com.minespaceships.mod.blocks;

import java.rmi.activation.ActivateFailedException;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRotatedPillar;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.EnumWorldBlockLayer;

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
}
