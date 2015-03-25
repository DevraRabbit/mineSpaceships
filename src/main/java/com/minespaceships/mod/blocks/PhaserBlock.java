package com.minespaceships.mod.blocks;

import net.minecraft.block.BlockRotatedPillar;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.EnumWorldBlockLayer;

public class PhaserBlock extends ActivatableBlock {

	public PhaserBlock() {
		super();
		this.setUnlocalizedName("Phaser");
        this.setCreativeTab(CreativeTabs.tabMisc);
	}
	
	@Override
	public int getEnergy() {
		return -1;
	}
}
