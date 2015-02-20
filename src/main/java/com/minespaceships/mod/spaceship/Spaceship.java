package com.minespaceships.mod.spaceship;

import java.util.List;

import javax.vecmath.Vector3d;

import com.minespaceships.util.BlockCopier;
import com.minespaceships.util.Vec3Op;

import net.minecraft.block.Block;
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
	
	/**
	 * Server world
	 */
	private final WorldServer worldS;
	
	/**
	 * Spaceship constructor
	 * @param minPosition 
	 * @param maxPosition 
	 * @param worldS      Server world
	 */
	public Spaceship(final BlockPos minPosition,final BlockPos maxPosition, final WorldServer worldS){
		setMeasurements(minPosition, maxPosition);
		this.worldS = worldS;
	}
	
	/**
	 * Spaceship constructor
	 * @param minPosition 
	 * @param dimX        
	 * @param dimY        
	 * @param dimZ        
	 * @param worldS      Server world
	 */
	public Spaceship(final BlockPos minPosition, final int dimX, final int dimY, final int dimZ, final WorldServer worldS){
		BlockPos recSpan = new BlockPos(dimX, dimY, dimZ);
		setMeasurements(minPosition, ((BlockPos) recSpan).add(minPosition));
		this.worldS = worldS;
	}
	
	/**
	 * Spaceship constructor
	 * @param minSpan 
	 * @param origin  
	 * @param maxSpan 
	 * @param worldS  Server world
	 */
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
	
	/**
	 * 
	 * @param position
	 */
	public void setTarget(BlockPos position){
		moveTo(position.subtract(origin));
	}
	
	/**
	 * 
	 * @param addDirection
	 */
	public void moveTo(BlockPos addDirection){
		moveTo(addDirection, worldS);
	}
	
	/**
	 * move whole spaceship
	 * @param addDirection 
	 * @param world        
	 */
	private void moveTo(BlockPos addDirection, World world){
		BlockPos add = new BlockPos(addDirection);
		
		// 1. copy all fullcube blocks first (e.g. signs are going to drop otherwise
		for(int x = 0; x < span.getX(); x++){
			for(int y = 0; y < span.getY(); y++){
				for(int z = 0; z < span.getZ(); z++){
					BlockPos Pos = new BlockPos(x,y,z);
					Pos = Pos.add(minPosition);
					Block block = world.getBlockState(Pos).getBlock();
					if(block.isFullCube()){
						BlockCopier.copyBlock(world, Pos, Pos.add(add));
					}
				}
			}
		}
		
		// 2. copy all other blocks e.g. signs
		// 3. delete all non fullcube blocks, so they aren't going to drop
		for(int x = 0; x < span.getX(); x++){
			for(int y = 0; y < span.getY(); y++){
				for(int z = 0; z < span.getZ(); z++){
					BlockPos Pos = new BlockPos(x,y,z);
					Pos = Pos.add(minPosition);
					Block block = world.getBlockState(Pos).getBlock();
					if(!block.isFullCube()){
						BlockCopier.copyBlock(world, Pos, Pos.add(add));
						BlockCopier.removeBlock(world, Pos);
					}
				}
			}
		}
		
		// 4. delete all full blocks
		for(int x = 0; x < span.getX(); x++){
			for(int y = 0; y < span.getY(); y++){
				for(int z = 0; z < span.getZ(); z++){
					BlockPos Pos = new BlockPos(x,y,z);
					Pos = Pos.add(minPosition);
					Block block = world.getBlockState(Pos).getBlock();
					if(block.isFullCube()){
						BlockCopier.removeBlock(world, Pos);
					}
				}
			}
		}
		
		world.markBlockRangeForRenderUpdate(minPosition, maxPosition);  
		moveEntities(addDirection);
		moveMeasurements(addDirection);
	}
	
	/**
	 * moveMeasurements
	 * @param addDirection
	 */
	private void moveMeasurements(BlockPos addDirection){
		minPosition = minPosition.add(addDirection);
		maxPosition = maxPosition.add(addDirection);
		origin = origin.add(addDirection);
	}
	
	/**
	 * moveEntities
	 * @param addDirection
	 */
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

	/**
	 * toString
	 * @return String Spaceship object state information
	 */
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
