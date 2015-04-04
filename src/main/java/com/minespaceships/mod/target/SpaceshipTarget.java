package com.minespaceships.mod.target;

import java.util.Random;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

import com.minespaceships.mod.spaceship.Shipyard;
import com.minespaceships.mod.spaceship.Spaceship;
import com.minespaceships.util.Vec3Op;

public class SpaceshipTarget extends PositionTarget{

	public SpaceshipTarget(BlockPos position, Spaceship ship) {
		super(position);
		if(ship != null){
			super.position = ship.getBlockMapOrigin();
			super.lastTarget = super.position;
		}
	}
	public SpaceshipTarget(NBTTagCompound position) {
		super(position);
	}
	@Override
	public void writeToNBT(NBTTagCompound c) {
		super.writeToNBT(c);
		c.setString(super.classKey, this.getClass().getName());
	}
	@Override
	public BlockPos getNewTarget(World world) {
		Spaceship ship = Shipyard.getShipyard(world).getShipByBlockMapOrigin(super.position, world);
		if(ship != null){
			super.lastTarget = ship.getRandomPos();
			return super.lastTarget;
		}
		return position;
	}
	public BlockPos getBasePos(){
		return super.getTarget(null);
	}

}
