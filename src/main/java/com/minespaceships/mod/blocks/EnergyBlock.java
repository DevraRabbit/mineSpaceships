package com.minespaceships.mod.blocks;

import net.minecraft.block.BlockRotatedPillar;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

public class EnergyBlock extends BlockRotatedPillar {

	public EnergyBlock() {
		super(Material.iron);
		this.setUnlocalizedName("Energy Generator");
        this.setCreativeTab(CreativeTabs.tabMisc);
	}

}
