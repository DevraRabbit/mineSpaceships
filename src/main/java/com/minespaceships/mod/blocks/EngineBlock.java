package com.minespaceships.mod.blocks;

import java.util.Iterator;
import java.util.Random;

import paulscode.sound.SoundSystem;

import com.google.common.base.Predicate;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLog;
import net.minecraft.block.BlockOldLog;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.BlockRotatedPillar;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.BlockTorch;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EngineBlock extends ActivatableBlock {	
	private int sound = 0;
	public EngineBlock() {
		super();
		this.setUnlocalizedName("Engine");
        this.setCreativeTab(CreativeTabs.tabMisc);
        this.setTickRandomly(true);
	}

	@Override
	public int getEnergy() {
		return -1;
	}
	
	@SideOnly(Side.CLIENT)
    public void randomDisplayTick(World worldIn, BlockPos pos, IBlockState state, Random rand)
    {		
        EnumFacing enumfacing = (EnumFacing)state.getValue(FACING);
        boolean isActive = (Boolean)state.getValue(ACTIVATED);

        if (isActive)
        {
        	double d0 = (double)pos.getX() + 0.5D;
            double d1 = (double)pos.getY() + 0.7D;
            double d2 = (double)pos.getZ() + 0.5D;
            double d3 = 0.22D;
            double d4 = 0.27D;
            //worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0, d1, d2, d3*(double)enumfacing.getFrontOffsetX(), -0.05D, d4*(double)enumfacing.getFrontOffsetZ(), new int[]{5});
            worldIn.spawnParticle(EnumParticleTypes.CLOUD, d0, d1, d2, d3*(double)enumfacing.getFrontOffsetX(), 0, d4*(double)enumfacing.getFrontOffsetZ(), new int[0]);
            Random posRand = new Random(pos.toLong());
            if ((worldIn.getTotalWorldTime()+posRand.nextInt(50)) % 50 == 0)
            {
            	sound = 0;
                worldIn.playSound(d0, d1, d2, "portal.travel", 10.0F, -300F, false);
            }
            sound++;
        }
    }
}
