package com.minespaceships.mod.blocks;

import net.minecraft.block.BlockRotatedPillar;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

public class ShieldBlock extends BlockRotatedPillar {

	public ShieldBlock() {
		super(Material.iron);
		this.setUnlocalizedName("Shield");
        this.setCreativeTab(CreativeTabs.tabMisc);
	}

}
