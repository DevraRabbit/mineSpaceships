package com.example.examplemod.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class NavigatorBlock extends Block {

    public NavigatorBlock(int par1, String text) {
    	super(Material.sponge);
    	this.setUnlocalizedName(text);
        this.setCreativeTab(CreativeTabs.tabMisc);
    }


    /*
    @Override
    public ItemStack onItemRightClick(ItemStack arg0, World arg1,
            EntityPlayer arg2) {
        return super.onItemRightClick(arg0, arg1, arg2);

        // Console öffnenen und auf tolle mineSpace Befehle warten.
    }
    */
}
