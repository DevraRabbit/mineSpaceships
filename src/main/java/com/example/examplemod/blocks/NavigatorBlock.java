package com.example.examplemod.blocks;

import com.example.examplemod.ExampleMod;
import com.example.examplemod.overhead.ChatRegisterEntity;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
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
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;

public class NavigatorBlock extends Block implements ITileEntityProvider{

    public NavigatorBlock(int par1, String text) {
    	super(Material.sponge);
    	this.setUnlocalizedName(text);
        this.setCreativeTab(CreativeTabs.tabMisc);
    }
    
    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ) {    	 
    	 ChatComponentText text = new ChatComponentText("Opening Console");
		 playerIn.addChatComponentMessage(text);
		 System.out.println("Test for opening a Console.");		 
		 TileEntity entity = worldIn.getTileEntity(pos);
		 if(entity instanceof ChatRegisterEntity){
			 ((ChatRegisterEntity)entity).Activate(playerIn);
		 }
    	 return true;         
    }

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new ChatRegisterEntity();
	}    
}
