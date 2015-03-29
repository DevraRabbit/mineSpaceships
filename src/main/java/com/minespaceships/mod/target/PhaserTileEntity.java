package com.minespaceships.mod.target;

import java.util.Random;

import com.minespaceships.mod.blocks.PhaserBlock;
import com.minespaceships.mod.spaceship.AllShipyards;
import com.minespaceships.mod.spaceship.Shipyard;
import com.minespaceships.mod.spaceship.Spaceship;
import com.minespaceships.util.PhaserUtils;
import com.minespaceships.util.Vec3Op;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.server.gui.IUpdatePlayerListBox;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PhaserTileEntity extends TileEntity implements IUpdatePlayerListBox{
	public static final int phaserDelay = 25;
	
	public static String delayKey = "delay";
	public static String strengthKey = "delay";
	public static String targetKey = "target";
	public static String hasTargetKey = "hasTarget";
	
	int delay;
	float strength;
	Target target;
	
	public PhaserTileEntity(){
		target = null;
		delay = 0;
	}
	
	public boolean shoot(Target futureTarget, float strength, Spaceship ship){		
		if(canShoot(futureTarget, ship)){
			this.target = futureTarget;
			delay = phaserDelay;
			this.strength = strength;
			return true;
		}
		return false;
	}
	public boolean canShoot(Target futureTarget, Spaceship ship){
		if(ship != null && futureTarget != null){
			BlockPos targetPos = futureTarget.getTarget(ship.getWorld());
			if(targetPos != null){
				BlockPos direction = Vec3Op.subtract(targetPos, pos);
				Vec3 dirVec = new Vec3(direction.getX(), direction.getY(), direction.getZ());
				return PhaserUtils.canShoot(pos, dirVec, ship);
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	public void stopShooting(){
		target = null;
		strength = 0;
		delay = 0;
	}
	public void updateRender(Random rand){
		if (delay != 0) {
			if(worldObj != null){
				float field = 1.5f;
				worldObj.spawnParticle(EnumParticleTypes.REDSTONE, pos.getX()+rand.nextFloat()*field, pos.getY()+rand.nextFloat()*field, pos.getZ()+rand.nextFloat()*field, 0, 0, 0, new int[0]);
			}
		}		
	}
	
	@Override
	public void writeToNBT(NBTTagCompound par1){
		if(target != null){
			par1.setBoolean(hasTargetKey, true);
			target.writeToNBT(par1);
			par1.setInteger(delayKey, delay);
		} else {
			par1.setBoolean(hasTargetKey, false);
			par1.setLong(targetKey, 0);
			par1.setInteger(delayKey, 0);
		}
		super.writeToNBT(par1); 
		this.markDirty();
	}

	@Override
	public void readFromNBT(NBTTagCompound par1){
		if(par1.getBoolean(hasTargetKey)){
			target = Target.targetFromNBT(par1);
			delay = par1.getInteger(delayKey);
		} else{
			target = null;
			delay = 0;
		}
		super.readFromNBT(par1);
		this.markDirty();
	}

	@Override
	public Packet getDescriptionPacket(){
		NBTTagCompound syncData = new NBTTagCompound();
		this.writeToNBT(syncData);
		return new S35PacketUpdateTileEntity(this.pos, 1, syncData);
	}

	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt){
		readFromNBT(pkt.getNbtCompound());
	}
	
	@Override
	public void update() {
		if(delay <= 0){
			if(target != null && worldObj != null){
				BlockPos targetPos = target.getTarget(worldObj);
				if(targetPos != null){
					PhaserUtils.shoot(pos, targetPos, strength, PhaserBlock.phaserMaxRange, worldObj);
				}
				Target nextTarget = target;
				stopShooting();
				Spaceship ship = Shipyard.getShipyard(worldObj).getShip(pos, worldObj);
				if(ship != null){
					ship.shootPhaserAt(nextTarget);
				}
			}
		} else {
			delay--;
		}
	}
}
