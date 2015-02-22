package com.minespaceships.mod.blocks;

import net.minecraft.block.BlockRotatedPillar;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

public class EngineBlock extends BlockRotatedPillar {

	public EngineBlock() {
		super(Material.iron);
		this.setUnlocalizedName("Engine");
        this.setCreativeTab(CreativeTabs.tabMisc);
	}

}
