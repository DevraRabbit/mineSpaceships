package com.minespaceships.mod.items;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraft.client.renderer.texture.*;
import net.minecraftforge.fml.common.registry.*;
import net.minecraftforge.fml.common.functions.*;
import java.util.Random;

import com.minespaceships.mod.Generator.WorldGeneratorTest;
import com.minespaceships.mod.build.*;



public class ItemTest extends Item {
	
	private ConstructionBuilder Builder;
	private ItemStack itemStackToBeDropped = new ItemStack(Blocks.end_portal);
	private static final String __OBFID = "CL_00000072";
	
	private Random random = new Random();
	public ItemTest() {
		
		
		setCreativeTab(CreativeTabs.tabMisc);
		setMaxStackSize(1);
		setUnlocalizedName("TestItem");
		Builder= new com.minespaceships.mod.build.ConstructionBuilder();
	}
	
	
	
	
	
	
@Override
	public boolean onDroppedByPlayer(ItemStack item, EntityPlayer player) {
		BlockPos playerPos = player.getPosition();
		Builder.buildPyramid(player, player.getEntityWorld(), playerPos, player.getHorizontalFacing(), 8, true, WorldGeneratorTest.getRandomBlock("Core",random));
		 
		return super.onDroppedByPlayer(item, player);
	}
	
	@Override
	public boolean onBlockDestroyed(ItemStack stack, World worldIn,
			Block blockIn, BlockPos pos, EntityLivingBase playerIn) {
		
		Builder.buildWalls(worldIn, blockIn, pos.add(0, -1, 0));
		
		
		return super.onBlockDestroyed(stack, worldIn, blockIn, pos, playerIn);
	}
	
	public void SetTreppe(World worldIn, BlockPos pos,Block blockIn){
	
	Builder.buildStair(pos, worldIn, blockIn);	
		
		
	}
		
		
	@Override
	public boolean onBlockStartBreak(ItemStack itemstack, BlockPos pos,
			EntityPlayer player) {
		player.addPotionEffect(new PotionEffect(Potion.digSpeed.id, 6000,10));
		player.playSound("random.bow", 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
		return super.onBlockStartBreak(itemstack, pos, player);
	}
	
	@Override
	public void onArmorTick(World world, EntityPlayer player,
			ItemStack itemStack) {
		player.removePotionEffect(Potion.jump.id);
		player.removePotionEffect(Potion.moveSpeed.id);
		player.addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 6000, 2));
		super.onArmorTick(world, player, itemStack);
	}
	
	public boolean onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ)
    {
//		Builder.buildPyramid(playerIn, worldIn,Builder.SearchGround(worldIn,pos,1,1), side, 9,true, Blocks.sandstone);
//      Builder.buildStair(pos, worldIn,worldIn.getBlockState(pos).getBlock());
//		playerIn.dimension =2;
	
		playerIn.travelToDimension(2);
           return false;
        	
        	
            
        }			
		
		
		
		
	

	 public boolean hasEffect(ItemStack stack)
	    {
	        return stack.getMetadata() > 0;
	    }

	    public EnumRarity getRarity(ItemStack stack)
	    {
	        return stack.getMetadata() == 0 ? EnumRarity.RARE : EnumRarity.EPIC;
	    }

	    public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player,Entity entity)
	    {
	        if (true)
	        {
	            entity.travelToDimension(2);
	            
	        }

	        if (stack.getMetadata() > 0)
	        {
	            if (true)
	            {
	                }
	        }
	        else
	        {
	            
	        }
			return true;
	    }
  

}
