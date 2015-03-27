package com.minespaceships.mod.blocks;

import java.util.Random;

import com.minespaceships.mod.spaceship.Shipyard;
import com.minespaceships.mod.spaceship.Spaceship;
import com.minespaceships.mod.target.PhaserTileEntity;
import com.minespaceships.mod.target.Target;

import net.minecraft.block.BlockRotatedPillar;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PhaserBlock extends ActivatableBlock implements ITileEntityProvider {
	public static final float phaserStrength = 5;
	public static final int phaserMaxRange = 200;

	public PhaserBlock() {
		super();
		this.setUnlocalizedName("Phaser");
        this.setCreativeTab(CreativeTabs.tabMisc);
        this.setTickRandomly(true);
	}
	
	public void shoot(BlockPos pos, World world, float strength, Target target){
		if(getStatus(pos, world)){
			Spaceship ship = Shipyard.getShipyard(world).getShip(pos, world);
			if(ship != null){
				TileEntity entity = world.getTileEntity(pos);
				if(entity instanceof PhaserTileEntity){
					((PhaserTileEntity)entity).shoot(target, ship);
				}
			}
		}
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new PhaserTileEntity();
	}
	@SideOnly(Side.CLIENT)
    public void randomDisplayTick(World worldIn, BlockPos pos, IBlockState state, Random rand){
		TileEntity entity = worldIn.getTileEntity(pos);
		if(entity instanceof PhaserTileEntity){
			((PhaserTileEntity)entity).updateRender(rand);
		}
	}
	
	@Override
	public int getEnergy() {
		return -1;
	}
}
