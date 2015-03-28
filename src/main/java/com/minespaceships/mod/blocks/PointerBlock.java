package com.minespaceships.mod.blocks;

import java.util.ArrayList;

import com.minespaceships.mod.target.Pointer;
import com.minespaceships.mod.target.RayUtil;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class PointerBlock extends Block implements ITileEntityProvider {
	public static final ArrayList<Material> transparent = new ArrayList<Material>();
	public static final int range = 200;

	protected PointerBlock() {
		super(Material.air);
		if(transparent.isEmpty()){
			transparent.add(Material.air);
			transparent.add(Material.glass);
			transparent.add(Material.water);
		}
	}
	
	public static void placeBy(EntityPlayer player){
		World world = player.getEntityWorld();
		Vec3 eyePos = player.getPositionVector().addVector(0, 1, 0);
		BlockPos pos = new BlockPos(RayUtil.getHit(eyePos, player.getLookVec(), transparent, range, world));
		//while(world.getBlockState(pos))
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new Pointer();
	}

}
