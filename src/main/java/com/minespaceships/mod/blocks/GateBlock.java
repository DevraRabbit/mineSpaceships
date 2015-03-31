package com.minespaceships.mod.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockPortal;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class GateBlock extends BlockPortal {

	public GateBlock() {
		super();
		
	}
	@Override
	public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn)
    {
        if (entityIn.ridingEntity == null && entityIn.riddenByEntity == null)
        {
        	if (entityIn.timeUntilPortal> 0) {
				entityIn.timeUntilPortal = 10;
			}
        	else {
				if (entityIn.dimension!=2) {
					entityIn.timeUntilPortal = 10;
					  entityIn.travelToDimension(2);
				}
				else {
					entityIn.timeUntilPortal = 10;
					  entityIn.travelToDimension(0);
				}
			}
          
        }
    }
	
	
	public void onNeighborBlockChange(World worldIn, BlockPos pos, IBlockState state, Block neighborBlock)
    {
       
    } 

}
