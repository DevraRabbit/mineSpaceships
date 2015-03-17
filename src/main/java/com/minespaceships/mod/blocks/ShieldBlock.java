package com.minespaceships.mod.blocks;

import net.minecraft.block.BlockRotatedPillar;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.EnumWorldBlockLayer;

public class ShieldBlock extends ActivatableBlock {

	public ShieldBlock() {
		super();
		this.setUnlocalizedName("Shield");
        this.setCreativeTab(CreativeTabs.tabMisc);
	}

	@Override
	public EnumWorldBlockLayer getBlockLayer() {
		return EnumWorldBlockLayer.TRANSLUCENT;
	}
}
