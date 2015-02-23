package com.minespaceships.mod.blocks;

import net.minecraft.block.BlockRotatedPillar;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.EnumWorldBlockLayer;

public class EngineBlock extends BlockRotatedPillar {

	public EngineBlock() {
		super(Material.iron);
		this.setUnlocalizedName("Engine");
        this.setCreativeTab(CreativeTabs.tabMisc);
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public EnumWorldBlockLayer getBlockLayer() {
		return EnumWorldBlockLayer.CUTOUT;
	}
}
