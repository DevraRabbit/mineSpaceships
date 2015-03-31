package com.minespaceships.mod.spaceship;

import java.io.Serializable;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;
import java.util.Vector;

import javax.vecmath.Vector3d;

import com.google.common.collect.ImmutableList;
import com.minespaceships.mod.blocks.EnergyBlock;
import com.minespaceships.mod.blocks.EngineBlock;
import com.minespaceships.mod.blocks.NavigatorBlock;
import com.minespaceships.mod.blocks.PhaserBlock;
import com.minespaceships.mod.blocks.ShieldBlock;
import com.minespaceships.mod.overhead.ChatRegisterEntity;
import com.minespaceships.mod.target.Target;
import com.minespaceships.mod.worldanalysation.WorldMock;
import com.minespaceships.util.BlockCopier;
import com.minespaceships.util.PhaserUtils;
import com.minespaceships.util.Vec3Op;

import energyStrategySystem.EnergyStrategySystem;
import energyStrategySystem.IEnergyC;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.BlockDoor.EnumDoorHalf;
import net.minecraft.block.BlockWallSign;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.util.Vec3i;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;

public class Spaceship implements Serializable{
	private World world;
	private BlockMap blockMap;
	private SpaceshipAssembler assembler;
	private EnergyStrategySystem energySystem;
	private MovementTarget target;
	private Vec3 position;
	private boolean isResolved = true;
	
	private static final String positionsKey = "Positions";
	private static final String containsTargetKey = "containsTarget";
	private static final String targetPositionKey = "TargetPos";
	private static final String targetTurnKey = "TargetTurn";
	private static final String actualPositionKey = "APK";
	
	private boolean canBeRemoved = true;
	
	public static final int maxShipSize = 27000;
	
	public Spaceship(BlockPos initial, World world) throws Exception{
		blockMap = new BlockMap(initial);
		blockMap = SpaceshipMath.getConnectedPositions(initial, world, maxShipSize);
		if(blockMap == null){
			throw new Exception("Ship is too huge or connected to the Ground");
		}
		this.world = world;
		initializeBase();
	}
	public Spaceship(BlockMap blocks, World world){
		blockMap = blocks;
		this.world = world;
		initializeBase();
	}	
	public Spaceship(NBTTagCompound s, String firstKey, World world)throws Exception {
		this.world = world;
		//world needs to be loaded first to prevent null pointer
		this.readFromNBT(s, firstKey);
		initializeBase();
	}
	private void initializeBase(){
		if(assembler == null){
			assembler = new SpaceshipAssembler(blockMap.getOrigin());
			refreshParts();
		}
		energySystem = new EnergyStrategySystem(assembler, world);
		if(position == null){
			position = blockMap.getMiddleVec();
		}
		//Shipyard.getShipyard(world).addShip(this);
	}
	
	public BlockPos getOrigin(){
		return blockMap.getMiddle();
	}
	public Vec3 getOriginVec(){
		return blockMap.getMiddleVec();
	}
	public BlockPos getBlockMapOrigin(){
		return blockMap.getOrigin();
	}
	public BlockPos getRandomPos(Random rand){
		ArrayList<BlockPos> positions = getPositions();
		float index = (float)(positions.size()-1)*rand.nextFloat();
		return positions.get((int)(index));
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
	
	public ArrayList<BlockPos> getPositions(){
		return blockMap.getPositions();
	}
	
	public BlockMap getBlockMap(){
		return blockMap;
	}
	public int getSize(){
		return blockMap.getSize();
	}
	
	
	
	public float getHardness(){
		return blockMap.getHardnessSum(world);
	}	
	public int getNavigatorCount(){
		return assembler.getParts(NavigatorBlock.class).size();
	}
	public int getPhaserCount(){
		return assembler.getParts(PhaserBlock.class).size();
	}
	public int getShieldsCount(){
		return assembler.getParts(ShieldBlock.class).size();
	}
	public int getEnginesCount(){
		return assembler.getParts(EngineBlock.class).size();
	}
	public int getActiveGeneratorsCount(){
		return energySystem.getActive(EnergyBlock.class, true).size();
	}
	public int getActivePhaserCount(){
		return energySystem.getActive(PhaserBlock.class, true).size();
	}
	public int getActiveShieldsCount(){
		return energySystem.getActive(ShieldBlock.class, true).size();
	}
	public int getActiveEnginesCount(){
		return energySystem.getActive(EngineBlock.class, true).size();
	}	
	
	public float getCapacity(){
		return energySystem.getEnergy(false);
	}
	public float getEnergy(){
		return energySystem.getEnergy();
	}
	
	public void activatePhasers(){
		energySystem.changeAll(PhaserBlock.class, true);
	}
	public void deactivatePhasers(){
		energySystem.changeAll(PhaserBlock.class, false);
	}
	public void activateShields(){
		energySystem.changeAll(ShieldBlock.class, true);
	}
	public void deactivateShields(){
		energySystem.changeAll(ShieldBlock.class, false);
	}
	public void activateEngines(){
		energySystem.changeAll(EngineBlock.class, true);
	}
	public void deactivateEngines(){
		energySystem.changeAll(EngineBlock.class, false);
	}
	
	public void balanceEnergy(){
		energySystem.refresh(true);
	}
	public void deactivateEverything(){
		energySystem.changeAll(IEnergyC.class, false);
	}
	
	public int getDistanceToGround(){
		ArrayList<BlockPos> positions = getPositions();
		int minHeight = Integer.MAX_VALUE;
		for(BlockPos pos : positions){
			BlockPos current = pos.add(0,-1,0);
			int height = 1;
			while(current.getY() > 0 && world.isAirBlock(current)){
				current = current.add(0,-1,0);
				height++;
			}
			if(!containsBlock(current)){
				if(height < minHeight){
					minHeight = height;
				}
			}
		}
		if(minHeight != Integer.MAX_VALUE){
			return minHeight-1;
		} else {
			return 0;
		}
	}
	
	public void shootPhaserAt(Target target){
		ArrayList<BlockPos> phasers = energySystem.getActive(PhaserBlock.class, true);
		Random rand = new Random();
		boolean hasShot = false;
		target.getNewTarget(world);
		while(!phasers.isEmpty()){
			int index = (int)((float)phasers.size()*rand.nextFloat());
			BlockPos pos = phasers.get(index);
			phasers.remove(index);
			Block block = world.getBlockState(pos).getBlock();
			if(block instanceof PhaserBlock){
				((PhaserBlock)block).stopShooting(pos, world);
				if(!hasShot){
					hasShot = ((PhaserBlock)block).shoot(pos, world, ShipInformation.getShipStrength(this), target);
				}
			}
		}
	}

	public void setTarget(BlockPos position){
		//moveTo(Vec3Op.subtract(position, origin), 0, world);
		this.position = getOriginVec();
		target = new MovementTarget(position, 0, world);
	}
	public void setTarget(BlockPos position, int turn){
		//moveTo(Vec3Op.subtract(position, origin), world);
		this.position = getOriginVec();
		target = new MovementTarget(position, turn, world);
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
	public void moveToTarget(){
		moveTo(target.getTarget(), target.getWorld(), target.getTurn());
		target = null;
	}
	

	public BlockPos getShipLengthToAdd(EntityPlayer player){
		int length = 0;
		if(getMaxPos().getZ()-getMinPos().getZ() > getMaxPos().getX()-getMinPos().getX())
		{
			length = getMaxPos().getZ()-getMinPos().getZ() + 1;
		}
		else
		{
			length = getMaxPos().getX()-getMinPos().getX() + 1;
		}
		System.out.println("DIIIIIEEE LÄÄÄÄNGEEE IST :   " + length);
		if (getFacing()==EnumFacing.EAST){
			return new BlockPos (length,0,0);			
		} else if (getFacing()==EnumFacing.WEST){
			return new BlockPos (-(length),0,0);			
		} else if (getFacing()==EnumFacing.NORTH){
			return new BlockPos (0,0,-(length));			
		} else if (getFacing()==EnumFacing.SOUTH){
			return new BlockPos (0,0,length);
		}
		else {
			int playerRotation = MathHelper.floor_double((double)(player.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
			if (playerRotation==EnumFacing.EAST.getHorizontalIndex()){
				return new BlockPos (length,0,0);			
			} else if (playerRotation==EnumFacing.WEST.getHorizontalIndex()){
				return new BlockPos (-(length),0,0);			
			} else if (playerRotation==EnumFacing.NORTH.getHorizontalIndex()){
				return new BlockPos (0,0,-(length));			
			} else{
				return new BlockPos (0,0,length);
			}
		}
	}
	
	public EnumFacing getFacing(){
		int north=0;
		int south=0;
		int east=0;
		int west=0;
		ArrayList<BlockPos> facingListActive= energySystem.getActive(EngineBlock.class, true);
		ArrayList<BlockPos> facingListDeactive= energySystem.getActive(EngineBlock.class, false);
		for (BlockPos p: facingListActive){			
			EnumFacing e=Turn.getEnumFacing(world.getBlockState(p));
			if(e==EnumFacing.NORTH){
				north+=1;
			} else if (e==EnumFacing.SOUTH){
				south+=1;
			} else if( e==EnumFacing.EAST){
				east+=1;
			}
			else if( e==EnumFacing.WEST){
				west+=1;
			}		
		}
		for (BlockPos p: facingListDeactive){			
			EnumFacing e=Turn.getEnumFacing(world.getBlockState(p));
			if(e==EnumFacing.NORTH){
				north+=1;
			} else if (e==EnumFacing.SOUTH){
				south+=1;
			} else if( e==EnumFacing.EAST){
				east+=1;
			}
			else if( e==EnumFacing.WEST){
				west+=1;
			}		
		}
		if(north>south&& north>west&& north> east){
			return EnumFacing.NORTH.getOpposite();
		} else if(south>north&&south>west&&south>east){
			return EnumFacing.SOUTH.getOpposite();
		} else if (east>north&&east>south&&east>west){
			return EnumFacing.EAST.getOpposite();
		} else if (west>north&&west>south&&west>east){
			return EnumFacing.WEST.getOpposite();
		}		
		
		return EnumFacing.UP;		
	}
	
	private void moveTo(BlockPos addDirection, World world, final int turn){
		if(!canMove(addDirection, world, turn)){
			return;
		}
		ArrayList<BlockPos> harderBlocks= new ArrayList(); //************Added collision
		ArrayList<BlockPos> softerBlocks= new ArrayList(); //************* Added for collison		
		blockMap.refreshVolumeBlocks(); //******************************ADDED
		Side side = FMLCommonHandler.instance().getEffectiveSide();
		WorldMock startMock = new WorldMock(world);
		//move the entities first to avoid long waiting times and weird bugs
		if(side == Side.CLIENT)moveEntities(addDirection, turn);
		//prevent it from being removed from the shipyard
		canBeRemoved = false;
		//list of positions that need to be removed in reverse order to prevent other blocks from cracking
		Vector<BlockPos> removal = new Vector<BlockPos>();
		
		//get all positions that can't be placed right now
		BlockPos add = new BlockPos(addDirection);
		ArrayList<BlockPos> positions = blockMap.getPositionsWithInnerBlocks();	
		int i = 3;
		ArrayList<BlockPos> toRefill = blockMap.getBlocksToRefill(world);  
		while(!positions.isEmpty() && i > 0){
			Iterator<BlockPos> it = positions.iterator();
			while(it.hasNext()){
				BlockPos Pos = it.next();
				IBlockState state = world.getBlockState(Pos);
				Block block = state.getBlock();
				BlockPos nextPos = Turn.getRotatedPos(Pos, getOrigin(), add, turn);			
				EnumFacing facing = Turn.getEnumFacing(state);
				BlockPos neighbor = null;
				IBlockState neighborState = null;
				facing = null;
//				if(facing != null){
//					facing = (EnumFacing)Turn.getNextFacing(facing, turn);
//					neighbor = nextPos.offset(facing.getOpposite());
//					neighborState = world.getBlockState(neighbor);
//				}
				if((facing == null || (facing != null && world.isSideSolid(neighbor, facing)))){
					if(tryCopy(startMock, Pos, nextPos, turn)){
						if(world.getBlockState(nextPos).getBlock().getMaterial() != Material.water && world.getBlockState(nextPos).getBlock().getMaterial() != Material.air){
							if(world.getBlockState(nextPos).getBlock().getBlockHardness(world, nextPos) >= world.getBlockState(Pos).getBlock().getBlockHardness(world, Pos)){
								harderBlocks.add(nextPos);
							}
							else{
								softerBlocks.add(nextPos);
							}
						}
						//build the buildable block
						BlockCopier.copyBlock(world, Pos, nextPos, turn);					
						it.remove();
						//remember to remove it
						removal.add(Pos);
					}
				} 
			}
			i--;
		}		
		//if there are blocks left
		if(!positions.isEmpty()){
			for(BlockPos Pos : positions){
				//force placement
				BlockPos nextPos = Turn.getRotatedPos(Pos, getOrigin(), add, turn);	
				BlockCopier.copyBlock(world, Pos, nextPos, turn);
				//again: remember to remove the Block. Now we need to append these at the front as they make problems when deleted last. This is cause of some deep Minecraft thingy
				removal.insertElementAt(Pos, 0);
			}
		}
		for(int j = 0; j < 3 && !removal.isEmpty(); j++){
			//remove the Blocks in reversed order, so that the most fragile ones are removed last.
			ListIterator<BlockPos> reverseRemoval = removal.listIterator(removal.size());
			while(reverseRemoval.hasPrevious()){
				BlockPos prev = reverseRemoval.previous();
				if(tryRemove(startMock, prev)){
					BlockCopier.removeBlock(world, prev);
				}
			}
		}
		//remove the ones that didn't pass
		ListIterator<BlockPos> reverseRemoval = removal.listIterator(removal.size());
		while(reverseRemoval.hasPrevious()){
			BlockPos prev = reverseRemoval.previous();
			BlockCopier.removeBlock(world, prev);
		}

		for(BlockPos pos : toRefill)
		{
			world.setBlockState(pos, Block.getStateById(8)); //************************************************************ADDED
		}
		//move the entities and move the ships measurements move serverside last as it is somehow faster than client side.
		if(side == Side.SERVER)moveEntities(addDirection, turn);
		//if(side == Side.CLIENT)world.markBlockRangeForRenderUpdate(getMinPos(), getMaxPos());
		moveMeasurements(addDirection, turn);
		canBeRemoved = true;
		
		for(BlockPos p : harderBlocks){
			world.createExplosion(null, (float)p.getX(), (float) p.getY(), (float)p.getZ(), 1.0F, true);
		}
		for(BlockPos p : softerBlocks){
			world.createExplosion(null, (float)p.getX(), (float)p.getY(), (float)p.getZ(), 0.5F, true);
		}
	}
	

	
	private boolean tryCopy(WorldMock startWorld, BlockPos start, BlockPos end, int turn){
		try{
			BlockCopier.copyBlock(startWorld, start, end, turn);	
		} catch (Exception e){
			System.out.println("An Error occured during Block Check. Moving anyway");
		}
		startWorld.nextSetBlocks();
		boolean out = startWorld.nextRemovedBlocks().size() == 0;
		return out;
	}
	private boolean tryRemove(WorldMock startWorld, BlockPos end){
		try{
//			if(startWorld.getBlockState(end).getBlock() instanceof BlockWallSign){
//				int i = 0;
//			}
			BlockCopier.removeBlock(startWorld, end);			
			startWorld.notifyNeighborsOfStateChange(end, Block.getStateById(0).getBlock());
		} catch (Exception e){
			System.out.println("An Error occured during Block Check. Moving anyway");
		}
		startWorld.nextSetBlocks();
		int size = startWorld.nextRemovedBlocks().size();
		if(size ==1){
			return size == 1;
		} else {
			IBlockState state = startWorld.getBlockState(end);
			return size == 1;
		}
	}
		
	private void moveMeasurements(BlockPos addDirection, int turn){
		blockMap.rotate(getOrigin(), turn);
		blockMap.setOrigin(blockMap.getOrigin().add(addDirection));	
		assembler.rotate(getOrigin(),  turn);
		assembler.setOrigin(assembler.getOrigin().add(addDirection));
	}
	@Deprecated
	private void moveEntities(BlockPos addDirection, int turn){
		List<Entity> entities = world.getEntitiesWithinAABB(Entity.class, new AxisAlignedBB(blockMap.getMinPos(), blockMap.getMaxPos().add(1,1,1)));
		for(Entity ent : entities){			
			if(ent instanceof EntityPlayer){
				((EntityPlayer)ent).addPotionEffect(new PotionEffect(Potion.blindness.getId(),10));
			}
			Vec3 addDir = new Vec3(addDirection.getX(), addDirection.getY(), addDirection.getZ());
			BlockPos origin = getOrigin();
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
			//ent.setPositionAndUpdate(newPos.xCoord, newPos.yCoord, newPos.zCoord);
			ent.setPositionAndUpdate(newPos.xCoord, newPos.yCoord, newPos.zCoord);
		}
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("minPosition: " + blockMap.getMinPos().toString());
		sb.append("\nmaxPosition: " + blockMap.getMaxPos().toString());
		sb.append("\norigin: " + getOrigin().toString());
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
			assembler.remove(state.getBlock().getClass(), pos);
		}
	}
	
	public void addBlock(final BlockPos pos) {
		this.blockMap.add(pos);		
		addSpaceshipPart(pos);
	}
	private void addSpaceshipPart(BlockPos pos){
		IBlockState state = world.getBlockState(pos);
		if(state.getBlock() instanceof ISpaceshipPart){
			assembler.put(state.getBlock().getClass(), pos);
		}
	}
	public void onEnergyChange(){
		energySystem.refresh();
	}
	public boolean hasEnergyFor(IEnergyC producer){
		return energySystem.canBeActivated(producer);
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
	public String positionsToString(){
		String data = "";
		ArrayList<BlockPos> positions = blockMap.getPositions();
		data += blockMap.getOrigin().toLong()+"\n";
		for(BlockPos pos : positions){
			data += pos.toLong()+"\n";
		}
		Set<Class> parts = assembler.getTypes();
		for(Class c : parts){
			data += c.getName()+"\n";
			positions = assembler.getParts(c);
			for(BlockPos pos : positions){
				data += pos.toLong()+"\n";
			}
		}
		return data;
	}
	public void positionsFromString(String data) throws Exception{
		String[] lines = data.split("\n");		
		BlockPos ori = BlockPos.fromLong(Long.parseLong(lines[0]));
		blockMap = new BlockMap(ori);
		assembler = new SpaceshipAssembler(ori);
		Class addedClass = null;
		for(String s : lines){
			if(addedClass == null){
				try{
					//need to press this directly into the block map. Otherwise minecraft tries to lead the chunk and ends up in an infinite loop.
					this.blockMap.add(BlockPos.fromLong(Long.parseLong(s)));		
				} catch(Exception e){
					addedClass = Class.forName(s);
				}
			} else {
				try{
					assembler.put(addedClass, BlockPos.fromLong(Long.parseLong(s)));		
				} catch(Exception e){
					addedClass = Class.forName(s);
				}
			}
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
	public void move(BlockPos position){
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
	public boolean canMove(BlockPos addDirection, World targetWorld, int turn){
		if(world != targetWorld){
			return true;
		}
		double maxWorldHeight = this.world.getHeight();
		BlockPos maxPos = getMaxPos();
		BlockPos minPos = getMinPos();
		BlockPos nextMaxPos = Turn.getRotatedPos(maxPos, getOrigin(), addDirection, turn);
		BlockPos nextMinPos = Turn.getRotatedPos(minPos, getOrigin(), addDirection, turn);
		if(nextMaxPos.getY() > maxWorldHeight || nextMinPos.getY() < 0){
			return false;
		}
		return !isInsideShipRectangle(nextMaxPos) && !isInsideShipRectangle(nextMinPos);
	}
	public boolean isInsideShipRectangle(BlockPos pos){
		BlockPos max = getMaxPos();
		BlockPos min = getMinPos();
		return pos.getX() >= min.getX() &&
				pos.getY() >= min.getY() &&
				pos.getZ() >= min.getZ() &&
				pos.getX() <= max.getX() &&
				pos.getY() <= max.getY() &&
				pos.getZ() <= max.getZ();
	}
	public void update(){
		Side side = FMLCommonHandler.instance().getEffectiveSide();
		
		Vector<BlockPos> movedPositions = move();
		if(side == Side.CLIENT){
			if(movedPositions != null){
				Random rand = new Random();
				for(BlockPos pos : movedPositions){					
					world.spawnParticle(EnumParticleTypes.PORTAL, pos.getX()+rand.nextFloat(), pos.getY()+rand.nextFloat(), pos.getZ()+rand.nextFloat(), 0, 0, 0, new int[0]);
					world.playSound(pos.getX(), pos.getY(), pos.getZ(), EngineBlock.engineSound, EngineBlock.engineSoundVolume, EngineBlock.engineSoundPitch, true);
				}
			}
		}
	}
	private Vector<BlockPos> move(){
		if(target != null){
			if(world != target.getWorld()){
				moveToTarget();
				return null;
			}
			Vec3 targetVec = new Vec3(target.getTarget().getX(), target.getTarget().getY(), target.getTarget().getZ());
			Vec3 direction = targetVec.subtract(position);
			float distance = (float) direction.lengthVector();			
			float traveledDistance = 0;
			float speed = ShipInformation.getShipSpeed(this);
			if(speed <= 0){
				stop();
				return null;
			}
			float directionAbs = 1;
			if(speed <= 1){
				direction = Vec3Op.scale(direction.normalize(), speed);
				directionAbs = speed;
			} else {
				direction = direction.normalize();
			}
			Vector<BlockPos> movedPositions = new Vector<BlockPos>();
			while(traveledDistance < speed && distance > 0) {
				position = position.add(direction);
				traveledDistance += directionAbs;
				distance -= directionAbs;
				movedPositions.add(new BlockPos(position));
				BlockPos worldPos = new BlockPos(position);
				Block block = world.getBlockState(worldPos).getBlock();
				if(!containsBlock(worldPos) &&
						block.getMaterial() != Material.air &&
						!block.getMaterial().isLiquid()){
					stop();
					return movedPositions;
				}				
			}
			if(distance <= 0){
				stop();
			}
			return movedPositions;
			
		} else {
			return null;
		}
	}
	public void stop(){
		BlockPos positionPos = new BlockPos(position);
		BlockPos addDirection = Vec3Op.subtract(positionPos, getOrigin());
		moveTo(addDirection, world, target.getTurn());
		target = null;
	}
	
	public void readFromNBT(NBTTagCompound c, String firstKey){
		String data = c.getString(firstKey+positionsKey);
		BlockPos pos = BlockPos.fromLong(c.getLong(firstKey+actualPositionKey));
		position = new Vec3(pos.getX(), pos.getY(), pos.getZ());
		if(c.getBoolean(firstKey+containsTargetKey)){
			BlockPos targetPos = BlockPos.fromLong(c.getLong(firstKey+targetPositionKey));
			int targetTurn = c.getInteger(firstKey+targetTurnKey);
			target = new MovementTarget(targetPos, targetTurn, world);
		}
		try {
			positionsFromString(data);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void writeToNBT(NBTTagCompound c, String firstKey){
		c.setString(firstKey+positionsKey, positionsToString());
		BlockPos pos = new BlockPos(position);
		c.setLong(firstKey+actualPositionKey, pos.toLong());
		if(target != null){
			c.setBoolean(firstKey+containsTargetKey, true);
			c.setLong(firstKey+targetPositionKey, target.getTarget().toLong());
			c.setInteger(firstKey+targetTurnKey, target.getTurn());
		} else {
			c.setBoolean(firstKey+containsTargetKey, false);
		}
	}
	
	
}
