package com.minespaceships.mod.blocks;

import net.minecraft.block.BlockRotatedPillar;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

public class PhaserBlock extends BlockRotatedPillar {

	public PhaserBlock() {
		super(Material.iron);
		this.setUnlocalizedName("Phaser");
        this.setCreativeTab(CreativeTabs.tabMisc);
	}

}
