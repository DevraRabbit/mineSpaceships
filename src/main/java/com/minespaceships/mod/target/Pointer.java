package com.minespaceships.mod.target;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class Pointer extends TileEntity {
	public static final String idKey = "ID";
	private int id;
	public Pointer() {
		
	}
	
	public void update(){
		worldObj.spawnParticle(EnumParticleTypes.REDSTONE, (pos.getX()+0.5)*1.2, (pos.getY()+0.5)*1.2, (pos.getZ()+0.5)*1.2, 0, 0, 0, new int[0]);
	}

	@Override
	public void readFromNBT(NBTTagCompound tagCompund) {
		id = tagCompund.getInteger(idKey);
	}

	@Override
	public void writeToNBT(NBTTagCompound tagCompound) {
		tagCompound.setInteger(idKey, id);	
	}
}
