package com.minespaceships.mod.build;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class ConstructionBuilder {

	
	public static void buildWalls(World worldIn,Block blockIn, BlockPos pos) {
		
		
		BlockPos newPos=pos;
		BlockPos newPos1=pos;
		BlockPos newPos2=pos;
		BlockPos newPos3=pos;
		
		for (int j = 0; j <=4; j++) {
			
		
		for (int i = -4; i <=4; i++) {
		
			worldIn.setBlockState(newPos,blockIn.getDefaultState());
			worldIn.setBlockState(newPos1,blockIn.getDefaultState());
			newPos=pos.add(i,j,4);
			newPos1=pos.add(i,j,-4);	
		}
		for (int i = -4; i <=4; i++) {
			
			worldIn.setBlockState(newPos2,blockIn.getDefaultState());
			worldIn.setBlockState(newPos3,blockIn.getDefaultState());
			newPos2=pos.add(4,j,i);
			newPos3=pos.add(-4,j,i);
	}
   }
	}
	public void buildStair(BlockPos pos, World worldIn,Block blockIn) {
		
		BlockPos newPos=pos;
		BlockPos newPos1=pos;
		BlockPos newPos2=pos;
		BlockPos newPos3=pos;
		
		for (int j = 0; j <=8; j++) {
			
		
		for (int i = -4; i <=8; i++) {
			
			worldIn.setBlockState(newPos, Blocks.ice.getDefaultState());
			worldIn.setBlockState(newPos1, Blocks.ice.getDefaultState());
			worldIn.setBlockState(newPos2, Blocks.ice.getDefaultState());
			worldIn.setBlockState(newPos3, Blocks.ice.getDefaultState());
			newPos=newPos.add(i,j,4);
			newPos1=newPos1.add(i,j,-4);
			newPos2=newPos2.add(4,j,i);
			newPos3=newPos3.add(-4,j,i);
		}
		}
		
		
		
		
		
	}
	public void buildPyramid (EntityPlayer playerIn, World worldIn, BlockPos pos, EnumFacing side,int pyramidsize,boolean hollow,Block blocktype){
		
		
		 pos = pos.offset(side);

        int pyramidRange=pyramidsize;
    	
    	for (int z = -1; pyramidRange>=0; pyramidRange--) {
				z++;
			
    	for (int x =-pyramidRange; x <= pyramidRange; x++) {
			
    		
    		for (int y =-pyramidRange; y <= pyramidRange; y++) {
				
    			   worldIn.setBlockState(pos.add(x,z,y),blocktype.getDefaultState());
    		
    		
    		if (hollow) {
				buildPyramid(playerIn, worldIn, pos.add(0, -1, 0), side, pyramidsize-1,false,Blocks.air);
			}	
    		}
    	}
    	}
    	
    	
    }	
    	public BlockPos SearchGround (World worldIn,BlockPos pos,int shipsize,int pyramidsize){
    		boolean foundBlock = false;
    		BlockPos foundpos=pos.add(0,-shipsize-pyramidsize,0);
    		while (!foundBlock) {
			if (worldIn.getBlockState(foundpos).getBlock().getMaterial() == Material.air) {
    			foundpos=foundpos.add(0,-1,0);
			} else {
					foundBlock =true;
				}
			}
			return foundpos;
  }
    	
    	
    	
    	public boolean TestforPlain(World worldIn,BlockPos pos,int pyramidsize){
    		
    		
    		boolean doesFit=true;
    		for (int x = 0; x < 2; x++) {
    			for (int y = 0; y < 2; y++) {
    				
    				if (worldIn.getBlockState(pos.add(x, 0, y)).getBlock().getMaterial() == Material.air) {
    					
    					doesFit=false;
						
					}
					
				}
    			
    			
				
			}
    		
    		return doesFit;
    		
    	}
		
    }
