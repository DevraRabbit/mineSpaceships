package com.minespaceships.mod.spaceship;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.minespaceships.util.Vec3Op;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSponge;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Vec3;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class BlockMap {
	/**
	 * blocks of the spaceship without blocks in inner room
	 */
	private HashMap<BlockPos, Boolean> map;
	private BlockPos maxPos;
	private BlockPos minPos;
	private BlockPos origin;
	private int blockCount;
	private Vec3 middle;
	/**
	 * all blocks which are not part of the spaceship or the room in the spaceship, but in the rectangular of the ship
	 */
	private HashMap<BlockPos, Boolean> outerBlocks; 
	/**
	 * all blocks at the edge of the rectangular
	 */
	private HashMap<BlockPos, Boolean> outerOutBlocks;
	/**
	 * all blocks in the rectangle, outerblocks and spaceshipblocks
	 */
	private HashMap<BlockPos, Boolean> spannedRectangle;
	/**
	 * blocks in inner room of spaceship
	 */
	private HashMap<BlockPos, Boolean> innerBlocks;
	/**
	 * if this variable is true, the hastoRefresh-method becomes active
	 */
	private boolean hasToRefresh = true;
			
	public BlockMap(BlockPos originPoint){
		map = new HashMap<BlockPos, Boolean>();
		outerBlocks = new HashMap<BlockPos, Boolean>();
		outerOutBlocks = new HashMap<BlockPos, Boolean>();
		innerBlocks = new HashMap<BlockPos, Boolean>();
		spannedRectangle = new HashMap<BlockPos, Boolean>();
	  	maxPos = new BlockPos(Integer.MIN_VALUE,Integer.MIN_VALUE,Integer.MIN_VALUE);
		minPos = new BlockPos(Integer.MAX_VALUE,Integer.MAX_VALUE,Integer.MAX_VALUE);
		origin = originPoint;
		middle = new Vec3(origin.getX(), origin.getY(), origin.getZ());
	}
	/**
	 * 
	 * @return absolute position of origin
	 */
	public BlockPos getOrigin(){
		return origin;
	}
	public BlockPos getMiddle(){
		return new BlockPos(middle).add(origin);
	}
	public Vec3 getMiddleVec(){
		return middle.add(new Vec3(origin.getX(), origin.getY(), origin.getZ()));
	}
	
	public void setOrigin(BlockPos pos){
		origin = pos;
	}
	public int getSize(){
		return map.size();
	}
	
	/**
	 * before spaceship is diving into water, this method stores blocks, which must be filled with water. 
	 * finds out which is the waterblock with the highest y-coordinate and safes all blocks of spaceship and 
	 * innerromm with lower y-coordinates
	 * @param world
	 * @return arraList of blocks
	 */
	public ArrayList<BlockPos> getBlocksToRefill(World world)
	{	
		HashMap<BlockPos, Boolean> nextToShipBlocks;
		int y = minPos.getY() - 1;
		boolean setToWater = false;
		ArrayList<BlockPos> toWater = new ArrayList();
		nextToShipBlocks= calculateFrameBlocks(new BlockPos(minPos.getX()-1, minPos.getY()-1, minPos.getZ()-1), new BlockPos(maxPos.getX()+1, maxPos.getY()+1, maxPos.getZ()+1));
		for(BlockPos p : nextToShipBlocks.keySet())
		{
			
			if(world.getBlockState(p.add(origin)).getBlock().getMaterial() == Material.water)
			{
				setToWater = true;
				if(p.getY() > y){
					y = p.getY();
				}
			}
		}
		if(setToWater)
		{
			for(BlockPos p : map.keySet())
			{
				if(p.getY() <= y)
				{
					toWater.add(p.add(origin));
				}
			}
			for(BlockPos p : innerBlocks.keySet())
			{
				if(p.getY() <= y)
				{
					toWater.add(p.add(origin));
				}
			}
		}
		return toWater;
	}
	public void add(BlockPos pos){
		add(pos, true);
	}
	public void add(BlockPos pos, boolean refresh){
		BlockPos coordPos = Vec3Op.subtract(pos, origin);
		if(!contains(pos)){
			blockCount++;
			middle = new Vec3((middle.xCoord*(blockCount-1)+coordPos.getX())/blockCount,
					(middle.yCoord*(blockCount-1)+coordPos.getY())/blockCount,
					(middle.zCoord*(blockCount-1)+coordPos.getZ())/blockCount);
		}

		map.put(coordPos, true);
		resize(coordPos);
		outerBlocks.remove(coordPos);
		innerBlocks.remove(coordPos);
		calculateOuterOutBlocks();
		boolean hasOuterBlock=false; 
		for(BlockPos p: getNeighbours(coordPos)){
			if(outerBlocks.containsKey(p)){
				hasOuterBlock=true;
				break;
			}
		}
		//checks if it is necessary to refresh all blocks
		if(hasOuterBlock || outerOutBlocks.containsKey(coordPos)){
			hasToRefresh = true;
		}		
		if(refresh)refreshVolumeBlocks();
	}
	
	public boolean contains(BlockPos pos){
		return map.containsKey(Vec3Op.subtract(pos, origin));
	}
	

	public void remove(BlockPos pos){
		BlockPos coordPos = Vec3Op.subtract(pos, origin);
		if(!contains(pos)){
			blockCount--;
			middle = new Vec3((middle.xCoord*(blockCount+1)-coordPos.getX())/blockCount,
					(middle.yCoord*(blockCount+1)+coordPos.getY())/blockCount,
					(middle.zCoord*(blockCount+1)+coordPos.getZ())/blockCount);
		}
		map.remove(Vec3Op.subtract(pos, origin));
		impendEdges(Vec3Op.subtract(pos, origin));
		pos = Vec3Op.subtract(pos, origin);
		ArrayList<BlockPos> neighbours= getNeighbours(pos);
		boolean hasOuterNeighbour= false;
		boolean hasInnerNeighbour=false;
		BlockPos innerNeighbour=null;
		for(BlockPos p : neighbours){
			if(outerBlocks.containsKey(p)){
				hasOuterNeighbour=true;						
			}
			if(innerBlocks.containsKey(p)){
				hasInnerNeighbour=true;	
				innerNeighbour=p;
			}
		}		
		//checks if it is necessary to refresh all blocks
		if(hasOuterNeighbour && hasInnerNeighbour || outerOutBlocks.containsKey(pos)){
			hasToRefresh = true;
		}		
		if(hasOuterNeighbour){
			outerBlocks.put(pos, true);
		}
		else{
			innerBlocks.put(pos, true);
		}
		refreshVolumeBlocks();
	}

	/**
	 * 
	 * @return absolute maxPos
	 */
	public BlockPos getMaxPos(){
		return maxPos.add(origin);
	}
	
	/**
	 * 
	 * @return absolute MinPos
	 */
	public BlockPos getMinPos(){
		return minPos.add(origin);
	}
	
	public boolean getHastoRefresh()
	{
		return hasToRefresh;
	}
	
	public HashMap<BlockPos, Boolean> getOuterBlocks()
	{
		return outerBlocks;
	}
	
	public HashMap<BlockPos, Boolean> getInnerBlocks()
	{
		return innerBlocks;
	}
		
	/** calculates and refreshes the innerBlocks and outerBlocks
	 * 
	 */
	public void refreshVolumeBlocks()
	{
		if(hasToRefresh || !hasToRefresh)              //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! 
		{
			calculateOuterOutBlocks();  //initializes outerBlocks
			spanRectangle();
			HashMap<BlockPos, Boolean> toCheckBlocks = outerOutBlocks;
			outerBlocks.clear();
			while(!toCheckBlocks.isEmpty())
			{
				HashMap<BlockPos, Boolean> checkNowBlocks = new HashMap<BlockPos, Boolean>();
				for(BlockPos pos : toCheckBlocks.keySet()){
					checkNowBlocks.put(pos, true);
				}
				toCheckBlocks.clear();
				for(BlockPos l : checkNowBlocks.keySet())
				{
					while(!map.containsKey(l) && !outerBlocks.containsKey(l) && spannedRectangle.containsKey(l))
					{
						outerBlocks.put(l, true);
						for(BlockPos p: getNeighbours(l)){
							toCheckBlocks.put(p, true);
						}
					}
				}
			}
			
			innerBlocks.clear(); 	//initializes innerBlocks
			for(BlockPos l : spannedRectangle.keySet())
			{
				if(!map.containsKey(l) && !outerBlocks.containsKey(l))           
				{
					innerBlocks.put(l, true);
				}
			}
		}
		hasToRefresh = false;
	}
	

//	
//	private void checkRoom(BlockPos aktPos)
//	{
//		if(!map.containsKey(aktPos) && !outerBlocks.containsKey(aktPos) && spannedRectangle.containsKey(aktPos))
//		{
//			outerBlocks.put(aktPos, true);
//			for(BlockPos p: getNeighbours(aktPos)){
//				checkRoom(p);
//			}
//		}
//	}
//	
	private void recalculateOuterBlocks()
	{
		for(BlockPos p : spannedRectangle.keySet()){
			if(!innerBlocks.containsKey(p) && !map.containsKey(p) && !outerBlocks.containsKey(p))
			{
				outerBlocks.put(p, true);
			}
		}
	}
	
//	private void checkInnerRoom(BlockPos aktPos){
//		ArrayList<BlockPos> juniorInnerBlocks= new ArrayList();
//		recCheckInnerRoom(aktPos, juniorInnerBlocks);
//		for(BlockPos p: juniorInnerBlocks){
//			innerBlocks.remove(p);
//			outerBlocks.put(p, true);
//		}
//	}
//	
//	private void recCheckInnerRoom(BlockPos aktPos, ArrayList<BlockPos> list){
//		if(!map.containsKey(aktPos)){
//			list.add(aktPos);
//			for(BlockPos p: getNeighbours(aktPos)){
//				recCheckInnerRoom(p, list);
//			}
//			 
//		}
//	}
	
//	private void checkAfterAdding(BlockPos pos){
//		  HashMap<BlockPos, Boolean> neighbours= new HashMap();
//		  for(BlockPos p: getNeighbours(pos)){
//			  neighbours.put(p, true);			  
//		  }
//		  HashMap<BlockPos, Boolean> possibleInnerRoom= new HashMap();
//		  for(BlockPos p: neighbours.keySet()){
//			recCheckAfterAdding(p, possibleInnerRoom);
//		  	for(BlockPos o: possibleInnerRoom.keySet()){
//		  		innerBlocks.put(o, true);
//		  	}
//		  }
//		  
//		   
//	}
	
//	private void recCheckAfterAdding(BlockPos pos, HashMap possibleInnerRoom){
//		if(!map.containsKey(pos)&& !possibleInnerRoom.containsKey(pos)){
//			if(outerOutBlocks.containsKey(pos)){
//				rekHelper = false;
//				possibleInnerRoom.clear();
//				return;
//			}
//			possibleInnerRoom.put(pos, true);
//			for(BlockPos p: getNeighbours(pos)){
//				if(rekHelper){
//					recCheckAfterAdding(p, possibleInnerRoom);
//				}
//			}
//			
//		}
//		
//		
//	}

	/**
	 * needs an absolute or relative blockpos and calculates neighbourblocks
	 * @param aktPos
	 * @return arrayList of Blockpos 
	 */
	private ArrayList<BlockPos> getNeighbours(BlockPos aktPos){
		ArrayList<BlockPos> toReturn= new ArrayList();
		toReturn.add(new BlockPos(aktPos.getX()-1, aktPos.getY(), aktPos.getZ()));
		toReturn.add(new BlockPos(aktPos.getX()+1, aktPos.getY(), aktPos.getZ()));
		toReturn.add(new BlockPos(aktPos.getX(), aktPos.getY()-1, aktPos.getZ()));
		toReturn.add(new BlockPos(aktPos.getX(), aktPos.getY()+1, aktPos.getZ()));
		toReturn.add(new BlockPos(aktPos.getX(), aktPos.getY(), aktPos.getZ()-1));
		toReturn.add(new BlockPos(aktPos.getX(), aktPos.getY(), aktPos.getZ()+1));
		return toReturn;
		
		
	}
	
	private void calculateOuterOutBlocks(){
		this.outerOutBlocks= calculateFrameBlocks(minPos, maxPos);
	}
	
	private HashMap<BlockPos, Boolean> calculateFrameBlocks(BlockPos minPos, BlockPos maxPos)
	{
		HashMap<BlockPos, Boolean> aktList = new HashMap();
		{
		int x = minPos.getX();
		for(int y = minPos.getY(); y < maxPos.getY(); y++)
		{
			for(int z = minPos.getZ(); z < maxPos.getZ(); z++)
			{
				aktList.put(new BlockPos(x, y, z), true);
			}
		}
		}
		{
		int y = minPos.getY();
		for(int x = minPos.getX(); x < maxPos.getX(); x++)
		{
			for(int z = minPos.getZ(); z < maxPos.getZ(); z++)
			{
				aktList.put(new BlockPos(x, y, z), true);
			}
		}
		}
		{
		int z = minPos.getZ();
		for(int x = minPos.getX(); x < maxPos.getX(); x++)
		{
			for(int y = minPos.getY(); y < maxPos.getY(); y++)
			{
				aktList.put(new BlockPos(x, y, z), true);
			}
		}
		}
		
		{
		int x = maxPos.getX();
		for(int y = maxPos.getY(); y >= minPos.getY(); y--)
		{
			for(int z = maxPos.getZ(); z >= minPos.getZ(); z--)
			{
				aktList.put(new BlockPos(x, y, z), true);
			}
		}
		}
		{
		int y = maxPos.getY();
		for(int x = maxPos.getX(); x >= minPos.getX(); x--)
		{
			for(int z = maxPos.getZ(); z >= minPos.getZ(); z--)
			{
				aktList.put(new BlockPos(x, y, z), true);
			}
		}
		}
		{
		int z = maxPos.getZ();
		for(int x = maxPos.getX(); x >= minPos.getX(); x--)
		{
			for(int y = maxPos.getY(); y >= minPos.getY(); y--)
			{
				aktList.put(new BlockPos(x, y, z), true);
			}
		}
		}
		return aktList;
	}
	
	private void spanRectangle()
	{
		HashMap<BlockPos, Boolean> aktList = new HashMap();
		for(int x = minPos.getX(); x <=maxPos.getX(); x++)
		{
			for(int y = minPos.getY(); y <=maxPos.getY(); y++)
			{
				for(int z = minPos.getZ(); z <=maxPos.getZ(); z++)
				{
					aktList.put(new BlockPos(x, y, z), true);
				}
			}
		}
		this.spannedRectangle = aktList;
	}
	
	
	
	public ArrayList<BlockPos> getPositions(){
		ArrayList<BlockPos> positions = new ArrayList<BlockPos>();
		Set<BlockPos> keys = map.keySet();
		for(BlockPos pos : keys){
			positions.add(pos.add(origin));
		}
		return positions;
	}
	
	public ArrayList<BlockPos> getPositionsWithInnerBlocks(){
		ArrayList<BlockPos> positions = new ArrayList<BlockPos>();
		Set<BlockPos> keys = map.keySet();
		for(BlockPos pos : keys){
			positions.add(pos.add(origin));
		}
		for(BlockPos pos : innerBlocks.keySet())
		{
			positions.add(pos.add(origin));          
		}
		return positions;
	}
	
	private void resize(BlockPos pos){
		if(maxPos.getX() < pos.getX()){
			maxPos = new BlockPos(pos.getX(), maxPos.getY(), maxPos.getZ());
		}
		if(maxPos.getY() < pos.getY()){
			maxPos = new BlockPos(maxPos.getX(), pos.getY(), maxPos.getZ());
		}
		if(maxPos.getZ() < pos.getZ()){
			maxPos = new BlockPos(maxPos.getX(), maxPos.getY(), pos.getZ());
		}
		if(minPos.getX() > pos.getX()){
			minPos = new BlockPos(pos.getX(), minPos.getY(), minPos.getZ());
		}
		if(minPos.getY() > pos.getY()){
			minPos = new BlockPos(minPos.getX(), pos.getY(), minPos.getZ());
		}
		if(minPos.getZ() > pos.getZ()){
			minPos = new BlockPos(minPos.getX(), minPos.getY(), pos.getZ());
		}
	}
	private void resizeAll(){
		minPos = new BlockPos(0,0,0);
		maxPos = new BlockPos(0,0,0);
		for(BlockPos p : map.keySet()){
			resize(p);
		}
	}
	
	private void impendEdges(BlockPos pos){
		BlockPos span = Vec3Op.subtract(maxPos, minPos);
		if(pos.getX() == maxPos.getX()){
			if(!otherInYZPane(span.getX())){
				resizeAll();
			}
		} else if(pos.getX() == minPos.getX()){
			if(!otherInYZPane(0)){
				resizeAll();
			}
		}
		if(pos.getY() == maxPos.getY()){
			if(!otherInXZPane(span.getY())){
				resizeAll();
			}
		} else if(pos.getY() == minPos.getY()){
			if(!otherInXZPane(0)){
				resizeAll();
			}
		}
		if(pos.getZ() == maxPos.getZ()){
			if(!otherInXYPane(span.getZ())){
				resizeAll();
			}
		} else if(pos.getZ() == minPos.getZ()){
			if(!otherInXYPane(0)){
				resizeAll();
			}
		}
	}
	
	private boolean otherInYZPane(int index){
		BlockPos span = Vec3Op.subtract(maxPos, minPos);
		for(int y = 0; y < span.getY(); y++){
			for(int z = 0; z < span.getZ(); z++){
				BlockPos pos = minPos.add(minPos.getX()+index, minPos.getY()+y, minPos.getZ()+z).add(origin);
				if(contains(pos)){
					return true;
				}
			}
		}
		return false;
	}
	
	private boolean otherInXZPane(int index){
		BlockPos span = Vec3Op.subtract(maxPos, minPos);
		for(int x = 0; x < span.getX(); x++){
			for(int z = 0; z < span.getZ(); z++){
				BlockPos pos = minPos.add(minPos.getX()+x, minPos.getY()+index, minPos.getZ()+z).add(origin);
				if(contains(pos)){
					return true;
				}
			}
		}
		return false;
	}
	
	private boolean otherInXYPane(int index){
		BlockPos span = Vec3Op.subtract(maxPos, minPos);
		for(int x = 0; x < span.getX(); x++){
			for(int y = 0; y < span.getY(); y++){
				BlockPos pos = minPos.add(minPos.getX()+x, minPos.getY()+y, minPos.getZ()+index).add(origin);
				if(contains(pos)){
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * Checks if block is next to the ship
	 * @param BlockPos position of block
	 */
	public boolean isNeighbor(final BlockPos pos) {
		for(int x = -1; x < 2; x++){
			for(int y = -1; y < 2; y++){
				for(int z = -1; z < 2; z++){
					if(x != 0 || y != 0 || z != 0){
						BlockPos neighbor = pos.add(x,y,z);
						if (this.contains(neighbor)) {
							return true;
						}
					}
				}
			}
		}
		return false;
	}
	
	public BlockMap getRotatedMap(BlockPos origin, BlockPos addDirection, int turn){
		if(turn == 0){
			this.origin = this.origin.add(addDirection);
			return this;
		}
		BlockMap nextMap = new BlockMap(this.origin.add(addDirection));
		ArrayList<BlockPos> positions = getPositions();
		for(BlockPos pos : positions){
			BlockPos nextPos = Turn.getRotatedPos(pos, origin, addDirection, turn);
			nextMap.add(nextPos);
		}
		return nextMap;
	}
	public float getHardnessSum(World world){
		try{
			Set<BlockPos> positions = map.keySet();
			float blastResistance = 0;
			for(BlockPos pos : positions){
				Block block = world.getBlockState(pos.add(origin)).getBlock();
				blastResistance += block.getBlockHardness(world, pos.add(origin));
			}
			return blastResistance;
		} catch(Exception e){
			e.printStackTrace();
			return 1;
		}
	}
	
	public HashMap<BlockPos, Boolean> getSpannedRectangle()
	{
		return this.spannedRectangle;
	}
	
	public void showDebug(World world){
		ArrayList<BlockPos> positions = getPositionsWithInnerBlocks();
		//calculateFrameBlocks(new BlockPos(minPos.getX()-1, minPos.getY()-1, minPos.getZ()-1),new BlockPos(minPos.getX()+1, minPos.getY()+1, minPos.getZ()+1)).keySet()
		for(BlockPos pos : positions)
		{
			world.setBlockState(pos, Block.getStateById(4));
		}		
		world.setBlockState(minPos.add(origin), Blocks.sea_lantern.getDefaultState());
		world.setBlockState(maxPos.add(origin), Blocks.sea_lantern.getDefaultState());
	}
	
	public void showDebugOld(World world){
		ArrayList<BlockPos> positions = getPositions();
		for(BlockPos pos : positions){
			world.setBlockState(pos, Block.getStateById(2));
		}
	}
}
