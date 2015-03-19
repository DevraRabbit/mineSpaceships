package com.minespaceships.mod.blocks;

import com.google.common.base.Predicate;
import com.minespaceships.mod.spaceship.ISpaceshipPart;
import com.minespaceships.mod.spaceship.Shipyard;
import com.minespaceships.mod.spaceship.Spaceship;

import energyStrategySystem.IEnergyC;
import net.minecraft.block.Block;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.World;

public abstract class ActivatableBlock extends Block implements ISpaceshipPart, IEnergyC {
	public static final String activatedName = "activated";
	public static final PropertyBool ACTIVATED = PropertyBool.create(activatedName);
	public static final PropertyDirection FACING = PropertyDirection.create("facing",  new Predicate()
    {
        private static final String __OBFID = "CL_10002054";
		@Override
		public boolean apply(Object input) {
			return input instanceof EnumFacing;
		}
    });
	
	public ActivatableBlock() {
		super(Material.iron);
		this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.UP).withProperty(ACTIVATED, false));
		this.setUnlocalizedName("Activatable");
        this.setCreativeTab(CreativeTabs.tabMisc);
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public EnumWorldBlockLayer getBlockLayer() {
		return EnumWorldBlockLayer.CUTOUT;
	}
	@Override
	public IBlockState getStateFromMeta(int meta)
    {
        IBlockState iblockstate = this.getDefaultState();
        int i = meta & 7;
        boolean power = (meta & 8) == 0 ? false : true;

        switch (meta)
        {
            case 1:
                iblockstate = iblockstate.withProperty(FACING, EnumFacing.EAST).withProperty(ACTIVATED, power);
                break;
            case 2:
                iblockstate = iblockstate.withProperty(FACING, EnumFacing.WEST).withProperty(ACTIVATED, power);
                break;
            case 3:
                iblockstate = iblockstate.withProperty(FACING, EnumFacing.SOUTH).withProperty(ACTIVATED, power);
                break;
            case 4:
                iblockstate = iblockstate.withProperty(FACING, EnumFacing.NORTH).withProperty(ACTIVATED, power);
                break;
            case 5:
            	iblockstate = iblockstate.withProperty(FACING, EnumFacing.DOWN).withProperty(ACTIVATED, power);
                break;
            case 6:
            	iblockstate = iblockstate.withProperty(FACING, EnumFacing.UP).withProperty(ACTIVATED, power);
                break;
            default:
                iblockstate = iblockstate.withProperty(FACING, EnumFacing.UP).withProperty(ACTIVATED, power);
        }

        return iblockstate;
    }
	@Override
	public int getMetaFromState(IBlockState state)
    {
        byte b0 = 0;
        int i;

        switch (ActivatableBlock.SwitchEnumFacing.FACING_LOOKUP[((EnumFacing)state.getValue(FACING)).ordinal()])
        {
            case 1:
                i = b0 | 1;
                break;
            case 2:
                i = b0 | 2;
                break;
            case 3:
                i = b0 | 3;
                break;
            case 4:
                i = b0 | 4;
                break;
            case 5:
            	i = b0 | 5;
                break;
            case 6:
            	i = b0 | 6;
                break;
            default:
                i = b0 | 0;
        }
        if((Boolean)state.getValue(ACTIVATED)){
        	i = i | 8;
        } else {
        	i = i & 7;
        }

        return i;
    }
	static final class SwitchEnumFacing
    {
        static final int[] FACING_LOOKUP = new int[EnumFacing.values().length];
        private static final String __OBFID = "CL_00002053";

        static
        {
            try
            {
                FACING_LOOKUP[EnumFacing.EAST.ordinal()] = 1;
            }
            catch (NoSuchFieldError var6)
            {
                ;
            }

            try
            {
                FACING_LOOKUP[EnumFacing.WEST.ordinal()] = 2;
            }
            catch (NoSuchFieldError var5)
            {
                ;
            }

            try
            {
                FACING_LOOKUP[EnumFacing.SOUTH.ordinal()] = 3;
            }
            catch (NoSuchFieldError var4)
            {
                ;
            }

            try
            {
                FACING_LOOKUP[EnumFacing.NORTH.ordinal()] = 4;
            }
            catch (NoSuchFieldError var3)
            {
                ;
            }

            try
            {
                FACING_LOOKUP[EnumFacing.DOWN.ordinal()] = 5;
            }
            catch (NoSuchFieldError var2)
            {
                ;
            }

            try
            {
                FACING_LOOKUP[EnumFacing.UP.ordinal()] = 6;
            }
            catch (NoSuchFieldError var1)
            {
                ;
            }
        }
    }
	
	@Override
	public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
    {
        return this.getDefaultState().withProperty(FACING, BlockPistonBase.getFacingFromEntity(worldIn, pos, placer)).withProperty(ACTIVATED, false);
    }
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ)
    {
		Spaceship ship = Shipyard.getShipyard().getShip(pos, worldIn);
		if((Boolean) state.getValue(ACTIVATED)){
			setStatus(false, pos, worldIn);
		} else {
			if(ship != null && ship.hasEnergyFor(this)){
				setStatus(true, pos, worldIn);	
			}
		}
		if(ship != null) ship.onEnergyChange();
        return false;
    }
	@Override
	protected BlockState createBlockState()
    {
        return new BlockState(this, new IProperty[] {FACING, ACTIVATED});
    }
	public IBlockState getNewBlockState(IBlockState state, boolean active){
		return this.getDefaultState().withProperty(FACING, state.getValue(FACING)).withProperty(ACTIVATED, active);
	}

	@Override
	public boolean getStatus(BlockPos pos, World world) {
		return (Boolean) world.getBlockState(pos).getValue(ACTIVATED);
	}

	@Override
	public void setStatus(boolean b, BlockPos pos, World world) {
		world.setBlockState(pos, getNewBlockState(world.getBlockState(pos), b));		
	}	
}
