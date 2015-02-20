package com.minespaceships.mod.spaceship;

import java.util.List;

import javax.vecmath.Vector3d;

import com.minespaceships.util.BlockCopier;
import com.minespaceships.util.Vec3Op;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Vec3;
import net.minecraft.util.Vec3i;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class Spaceship {
	private BlockPos minPosition;
	private BlockPos maxPosition;
	private BlockPos span;
	private BlockPos origin;
	private WorldServer worldS;
	
	public Spaceship(final BlockPos minPosition,final BlockPos maxPosition, WorldServer worldS){
		setMeasurements(minPosition, maxPosition);
		this.worldS = worldS;
	}
	public Spaceship(final BlockPos minPosition, int dimX, int dimY, int dimZ, WorldServer worldS){
		BlockPos recSpan = new BlockPos(dimX, dimY, dimZ);
		setMeasurements(minPosition, ((BlockPos) recSpan).add(minPosition));
		this.worldS = worldS;
	}
	public Spaceship(final BlockPos minSpan, final BlockPos origin, final BlockPos maxSpan, WorldServer worldS){
		setMeasurements(((BlockPos) minSpan).add(origin), ((BlockPos) maxSpan).add(origin));
		this.origin = origin;
		this.worldS = worldS;
	}
	
	private void setMeasurements(final BlockPos minPos, final BlockPos maxPos){
		minPosition = minPos;
		maxPosition = maxPos;
		span = ((BlockPos) maxPos).subtract(minPos);
		origin = Vec3Op.scale(span, 0.5);
	}
	public void moveTo(BlockPos addDirection){
		//copyTo(addDirection, worldC);
		moveTo(addDirection, worldS);
	}
	private void moveTo(BlockPos addDirection, World world){
		BlockPos add = new BlockPos(addDirection);
		for(int x = 0; x < span.getX(); x++){
			for(int y = 0; y < span.getY(); y++){
				for(int z = 0; z < span.getZ(); z++){
					BlockPos Pos = new BlockPos(x,y,z);
					Pos = Pos.add(minPosition);
					BlockCopier.copyBlock(world, Pos, Pos.add(add));
					BlockCopier.removeBlock(world, Pos);
				}
			}
		}
		world.markBlockRangeForRenderUpdate(minPosition, maxPosition);  
		moveEntities(addDirection);
		moveMeasurements(addDirection);
	}
	private void moveMeasurements(BlockPos addDirection){
		minPosition = minPosition.add(addDirection);
		maxPosition = maxPosition.add(addDirection);
		origin = origin.add(addDirection);
	}
	private void moveEntities(BlockPos addDirection){
		List<Entity> entities = worldS.getEntitiesWithinAABB(Entity.class, new AxisAlignedBB(minPosition, maxPosition));
		for(Entity ent : entities){			
			if(ent instanceof EntityPlayer){
				((EntityPlayer)ent).addPotionEffect(new PotionEffect(Potion.blindness.getId(),10));
			}
			Vec3 newPos = ent.getPositionVector().add(new Vec3(addDirection.getX(), addDirection.getY(), addDirection.getZ()));
			ent.setPositionAndUpdate(newPos.xCoord, newPos.yCoord, newPos.zCoord);
		}
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("minPosition: " + minPosition.toString());
		sb.append("\nmaxPosition: " + maxPosition.toString());
		sb.append("\nspan: " + span.toString());
		sb.append("\norigin: " + origin.toString());
		sb.append("\nworldServer: " + worldS == null ? "Not Known.\n" : "Known\n");
		return sb.toString();
	}
}
