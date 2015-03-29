package com.minespaceships.mod.menu.functionalMenus.targetMenus;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

import com.minespaceships.mod.spaceship.Shipyard;
import com.minespaceships.mod.spaceship.Spaceship;
import com.minespaceships.mod.target.PositionTarget;
import com.minespaceships.util.Vec3Op;

public class SpaceshipTarget extends PositionTarget{

	public SpaceshipTarget(BlockPos position) {
		super(position);
	}
	public SpaceshipTarget(NBTTagCompound position) {
		super(position);
	}
	@Override
	public BlockPos getNewTarget(World world) {
		BlockPos position = super.getNewTarget(world);
		Spaceship ship = Shipyard.getShipyard(world).getShip(position, world);
		if(ship != null){
			BlockPos span = Vec3Op.subtract(ship.getMaxPos(), ship.getMinPos());
			BlockPos div = Vec3Op.scale(span, 0.5);
			return super.getDiversedTarget(div, world);
		}
		return position;
	}

}
