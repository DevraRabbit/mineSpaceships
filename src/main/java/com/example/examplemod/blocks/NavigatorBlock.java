package com.example.examplemod.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IChatComponent;
import net.minecraft.world.World;

public class NavigatorBlock extends Block {

    public NavigatorBlock(int par1, String text) {
    	super(Material.sponge);
    	this.setUnlocalizedName(text);
        this.setCreativeTab(CreativeTabs.tabMisc);
    }


    
    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ) {
    	 if (worldIn.isRemote)
         {
             return true;
         }
         else
         {
        	 ChatComponentText text = new ChatComponentText("Yay, you clicked our block!");
			 playerIn.addChatComponentMessage(text);
        	 return true;
         }
    }    
}
