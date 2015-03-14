package com.minespaceships.mod.spaceship;

import java.io.Serializable;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Scanner;
import java.util.Set;
import java.util.Vector;

import javax.vecmath.Vector3d;

import com.google.common.collect.ImmutableList;
import com.minespaceships.mod.blocks.NavigatorBlock;
import com.minespaceships.mod.overhead.ChatRegisterEntity;
import com.minespaceships.util.BlockCopier;
import com.minespaceships.util.Vec3Op;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.BlockDoor.EnumDoorHalf;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.Vec3;
import net.minecraft.util.Vec3i;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class Spaceship implements Serializable{
	private BlockPos origin;
	private World world;
	private BlockMap blockMap;
	private SpaceshipAssembler assembler;
	
	private boolean canBeRemoved = true;
	
	public static final int maxShipSize = 27000;

	@Deprecated
	public Spaceship(final BlockPos minSpan, final BlockPos origin, final BlockPos maxSpan, World world){
		this.origin = origin;
		this.world = world;
		setMeasurements(((BlockPos) minSpan).add(origin), ((BlockPos) maxSpan).add(origin));		
		initializeBase();
	}
	@Deprecated
	public Spaceship(int[] originMeasurement){
		world = (WorldServer)MinecraftServer.getServer().getEntityWorld();
		readOriginMeasurementArray(originMeasurement);
		initializeBase();
	}
	
	public Spaceship(BlockPos initial, World world) throws Exception{
		blockMap = new BlockMap(initial);
		blockMap = SpaceshipMath.getConnectedPositions(initial, world, maxShipSize);
		if(blockMap == null){
			throw new Exception("Ship is too huge or connected to the Ground");
		}
		this.origin = initial;
		this.world = world;
		initializeBase();
	}
	public Spaceship(BlockMap blocks, World world){
		blockMap = blocks;
		this.world = world;
		initializeBase();
	}	
	public Spaceship(String s, World world)throws Exception {
		this.fromData(s);
		this.world = world;
		this.origin = blockMap.getOrigin();
		initializeBase();
	}
	private void initializeBase(){
		assembler = new SpaceshipAssembler(blockMap.getOrigin());
		refreshParts();
		Shipyard.getShipyard().addShip(this);
	}
	
	public BlockPos getOrigin(){
		return origin;
	}
	public BlockPos getMaxPos(){
		return blockMap.getMaxPos();
	}
	public BlockPos getMinPos(){
		return blockMap.getMinPos();
	}
	public boolean canBeRemoved(){
		return canBeRemoved;
	}
	
	public int getNavigatorCount(){
		return assembler.getParts(NavigatorBlock.class).size();
	}
	public ArrayList<BlockPos> getPositions(){
		return blockMap.getPositions();
	}
	
	public BlockMap getBlockMap(){
		return blockMap;
	}
	
	@Deprecated
	public int[] getOriginMeasurementArray(){
		BlockPos minSpan = blockMap.getMinPos().subtract(origin);
		BlockPos maxSpan = blockMap.getMaxPos().subtract(origin);
		int[] a = {minSpan.getX(), minSpan.getY(), minSpan.getZ(),
				origin.getX(), origin.getY(), origin.getZ(),
				maxSpan.getX(), maxSpan.getY(), maxSpan.getZ()};
		return a;
	}
	@Deprecated
	public void readOriginMeasurementArray(int[] array){
		try {
			BlockPos minSpan = new BlockPos(array[0], array[1], array[2]);
			BlockPos maxSpan = new BlockPos(array[6], array[7], array[8]);
			origin = new BlockPos(array[3], array[4], array[5]);
			setMeasurements(minSpan.add(origin), maxSpan.add(origin));
			origin = new BlockPos(array[3], array[4], array[5]);
		} catch (ArrayIndexOutOfBoundsException ex) {
			System.out.println("Could not read OriginMeasurementArray (probably an error with NBT). Try creating a new World.");
			System.out.println("Printing Exception Stack:");
			System.out.println(ex.getMessage());
		}
	}
	@Deprecated
	private void setMeasurements(final BlockPos minPos, final BlockPos maxPos){
		blockMap = new BlockMap(minPos);
		BlockPos span = ((BlockPos) maxPos).subtract(minPos);
		for(int x = 0; x <= span.getX(); x++){
			for(int y = 0; y <= span.getY(); y++){
				for(int z = 0; z <= span.getZ(); z++){
					//if(!world.isAirBlock(new BlockPos(x,y,z).add(minPos))){
						blockMap.add(new BlockPos(x,y,z).add(minPos));
					//}
				}
			}
		}
		origin = Vec3Op.scale(span, 0.5);
	}
	public void setTarget(BlockPos position){
		moveTo(position.subtract(origin), 0, world);
	}
	public void setTarget(BlockPos position, World world){
		moveTo(position.subtract(origin), world);
	}
	public void moveTo(BlockPos addDirection) {
		moveTo(addDirection, world, 0);
	}
	public void moveTo(BlockPos addDirection, World world) {
		moveTo(addDirection, world, 0);
	}
	public void moveTo(BlockPos addDirection, int turn, World world) {
		moveTo(addDirection, world, turn);
	}
	
	private void moveTo(BlockPos addDirection, World world, final int turn){
		//prevent it from being removed from the shipyard
		canBeRemoved = false;
		//list of positions that need to be removed in revers order to prevent other blocks from cracking
		Vector<BlockPos> removal = new Vector<BlockPos>();
		
		//get all positions that can't be placed right now
		BlockPos add = new BlockPos(addDirection);
		ArrayList<BlockPos> positions = blockMap.getPositions();	
		int i = 3;
		while(!positions.isEmpty() && i > 0){
			Iterator<BlockPos> it = positions.iterator();
			while(it.hasNext()){
				BlockPos Pos = it.next();
				IBlockState state = world.getBlockState(Pos);
				Block block = state.getBlock();
				BlockPos nextPos = Turn.getRotatedPos(Pos, this.origin, add, turn);			
				EnumFacing facing = Turn.getEnumFacing(state);
				BlockPos neighbor = null;
				IBlockState neighborState = null;
				if(facing != null){
					facing = (EnumFacing)Turn.getNextFacing(facing, turn);
					neighbor = nextPos.offset(facing.getOpposite());
					neighborState = world.getBlockState(neighbor);
				}
				if((facing == null || (facing != null && world.isSideSolid(neighbor, facing)))){
					//build the buildable block
					BlockCopier.copyBlock(world, Pos, nextPos, turn);					
					it.remove();
					//remember to remove it
					removal.add(Pos);
				} 
			}
			i--;
		}		
		//if there are blocks left
		if(!positions.isEmpty()){
			for(BlockPos Pos : positions){
				//force placement
				BlockPos nextPos = Turn.getRotatedPos(Pos, this.origin, add, turn);	
				BlockCopier.copyBlock(world, Pos, nextPos, turn);
				//again: remember to remove the Block. Now we need to append these at the front as they make problems when deleted last. This is cause of some deep Minecraft thingy
				removal.insertElementAt(Pos, 0);
			}
		}
		//remove the Blocks in reversed order, so that the most fragile ones are removed last.
		ListIterator<BlockPos> reverseRemoval = removal.listIterator(removal.size());
		while(reverseRemoval.hasPrevious()){
			BlockCopier.removeBlock(world, reverseRemoval.previous());
		}
		//move the entities and move the ships measurements  
		moveEntities(addDirection, turn);
		moveMeasurements(addDirection, turn);
		canBeRemoved = true;
	}
	
	private void moveMeasurements(BlockPos addDirection, int turn){
		if(turn != 0){blockMap.rotate(origin, turn);}
		blockMap.setOrigin(blockMap.getOrigin().add(addDirection));		
		assembler.setOrigin(blockMap.getOrigin().add(addDirection));
		origin = origin.add(addDirection);
	}
	@Deprecated
	private void moveEntities(BlockPos addDirection, int turn){
		List<Entity> entities = world.getEntitiesWithinAABB(Entity.class, new AxisAlignedBB(blockMap.getMinPos(), blockMap.getMaxPos().add(1,1,1)));
		for(Entity ent : entities){			
			if(ent instanceof EntityPlayer){
				((EntityPlayer)ent).addPotionEffect(new PotionEffect(Potion.blindness.getId(),10));
			}
			Vec3 addDir = new Vec3(addDirection.getX(), addDirection.getY(), addDirection.getZ());
			Vec3 orig = new Vec3(origin.getX(), origin.getY(), origin.getZ());
			Vec3 newPos = Turn.getRotatedPos(ent.getPositionVector(), orig, addDir, turn);
			switch(turn){
			case Turn.LEFT:
				ent.setRotationYawHead((float) (ent.rotationYaw+Math.PI/2));
				break;
			case Turn.RIGHT:
				ent.setRotationYawHead((float) (ent.rotationYaw-Math.PI/2));
				break;
			case Turn.AROUND:
				ent.setRotationYawHead((float) (ent.rotationYaw+Math.PI));
				break;
			}
			ent.setPositionAndUpdate(newPos.xCoord, newPos.yCoord, newPos.zCoord);
		}
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("minPosition: " + blockMap.getMinPos().toString());
		sb.append("\nmaxPosition: " + blockMap.getMaxPos().toString());
		sb.append("\norigin: " + origin.toString());
		sb.append("\nworlderver: " + world == null ? "Not Known.\n" : "Known\n");
		return sb.toString();
	}
	
	public World getWorld() {
		return this.world;
	}
	
	public Boolean containsBlock(BlockPos pos) {
		return this.blockMap.contains(pos);
	}
	
	public boolean removeBlock(BlockPos pos) {
		this.blockMap.remove(pos);
		removeSpaceshipPart(pos);
		if(getNavigatorCount() <= 0){
			return true;
		}
		return false;
	}
	private void removeSpaceshipPart(BlockPos pos){
		IBlockState state = world.getBlockState(pos);
		if(state.getBlock() instanceof ISpaceshipPart){
			assembler.remove(state.getBlock(), pos);
		}
	}
	
	public void addBlock(final BlockPos pos) {
		this.blockMap.add(pos);		
		addSpaceshipPart(pos);
	}
	private void addSpaceshipPart(BlockPos pos){
		IBlockState state = world.getBlockState(pos);
		if(state.getBlock() instanceof ISpaceshipPart){
			assembler.put(state.getBlock(), pos);
		}
	}
	
	public void refreshParts(){
		assembler.clear();
		ArrayList<BlockPos> position = blockMap.getPositions();
		for(BlockPos pos : position){
			addSpaceshipPart(pos);
		}
	}
	
	public boolean isNeighboringBlock(final BlockPos pos) {
		return this.blockMap.isNeighbor(pos);
	}
	public String toData(){
		String data = "";
		ArrayList<BlockPos> positions = blockMap.getPositions();
		data += blockMap.getOrigin().toLong()+"\n";
		for(BlockPos pos : positions){
			data += pos.toLong()+"\n";
		}
		return data;
	}
	public void fromData(String data) throws Exception{
		String[] lines = data.split("\n");		
		blockMap = new BlockMap(BlockPos.fromLong(Long.parseLong(lines[0])));
		for(int i = 1; i < lines.length; i++){
			blockMap.add(BlockPos.fromLong(Long.parseLong(lines[i])));			
		}
	}
	public boolean measuresEquals(Spaceship ship){
		return ship.blockMap.getMaxPos().equals(blockMap.getMaxPos()) &&
				ship.blockMap.getMinPos().equals(blockMap.getMinPos()) &&
				ship.getWorld() == world;
	}
	public void debugMap(){
		blockMap.showDebug(world);
	}

	/**
	 * Moves the spaceship to a target position.
	 * These method check also if the target position is a valid position.
	 * @param position
	 */
	public void move(final BlockPos position){
		if(position == null){
			throw new IllegalArgumentException("The target position can not be null");
		}

		double x = position.getX();
		double y = position.getY();
		double z = position.getZ();
		double maxWorldHeight = this.world.getHeight();
		BlockPos maxShipHeight = getMaxPos();
		BlockPos minShipHeight = getMinPos();
		double shipHeight = maxShipHeight.getY()-minShipHeight.getY();

		//Troubleshooting for the world height out of bounds.
		if(position.getY() >= maxWorldHeight){
			this.setTarget(new BlockPos(x,(maxWorldHeight-shipHeight),z));
		}
		if(position.getY() <= 0){
			this.setTarget(new BlockPos(x,(0+shipHeight),z));
		}

		//Valid position
		this.setTarget(new BlockPos(x,y,z));
	}
}
