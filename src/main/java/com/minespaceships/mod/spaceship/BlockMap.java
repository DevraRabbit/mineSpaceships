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
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class BlockMap {
	private HashMap<BlockPos, Boolean> map;
	private BlockPos maxPos;
	private BlockPos minPos;
	private BlockPos origin;
	private HashMap<BlockPos, Boolean> outerBlocks; 
	private HashMap<BlockPos, Boolean> outerOutBlocks;
	private HashMap<BlockPos, Boolean> spannedRectangle;
	
	
	private HashMap<BlockPos, Boolean> innerBlocks;
	private boolean rekHelper = true;
	private static int ff = 0;   //*****************REMOVE
	private boolean hasToRefresh = true;
			
	public BlockMap(BlockPos originPoint){
		map = new HashMap<BlockPos, Boolean>();
		outerBlocks = new HashMap<BlockPos, Boolean>();
		outerOutBlocks = new HashMap<BlockPos, Boolean>();
		innerBlocks = new HashMap<BlockPos, Boolean>();
		spannedRectangle = new HashMap<BlockPos, Boolean>();
	  	maxPos = new BlockPos(0,0,0);
		minPos = new BlockPos(0,0,0);
		origin = originPoint;
	}
	
	public BlockPos getOrigin(){
		return origin;
	}
	
	public void setOrigin(BlockPos pos){
		origin = pos;
	}
	
	public ArrayList<BlockPos> getBlocksToRefill(World world)
	{	HashMap<BlockPos, Boolean> nextToShipBlocks;
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

		map.put(Vec3Op.subtract(pos, origin), true);
		resize(Vec3Op.subtract(pos, origin));
		pos = Vec3Op.subtract(pos, origin);
		outerBlocks.remove(pos);
		innerBlocks.remove(pos);
		calculateOuterOutBlocks();
		boolean hasOuterBlock=false;
		for(BlockPos p: getNeighbours(pos)){
			if(outerBlocks.containsKey(p)){
				hasOuterBlock=true;
				break;
			}
		}
		if(hasOuterBlock || outerOutBlocks.containsKey(pos)){
			hasToRefresh = true;
		}		
		refreshVolumeBlocks();

	}
	
	public boolean contains(BlockPos pos){
		return map.containsKey(Vec3Op.subtract(pos, origin));
	}
	

	public void remove(BlockPos pos){
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

	
	public BlockPos getMaxPos(){
		return maxPos.add(origin);
	}
	
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
		if(hasToRefresh) 
		{
			calculateOuterOutBlocks();  //initializes outerBlocks
			spanRectangle();
			outerBlocks.clear();
			for(BlockPos l : outerOutBlocks.keySet())
			{
				checkRoom(l);
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
	

	
	private void checkRoom(BlockPos aktPos)
	{
		if(!map.containsKey(aktPos) && !outerBlocks.containsKey(aktPos) && spannedRectangle.containsKey(aktPos))
		{
			outerBlocks.put(aktPos, true);
			for(BlockPos p: getNeighbours(aktPos)){
				checkRoom(p);
			}
		}
	}
	
	private void recalculateOuterBlocks()
	{
		for(BlockPos p : spannedRectangle.keySet()){
			if(!innerBlocks.containsKey(p) && !map.containsKey(p) && !outerBlocks.containsKey(p))
			{
				outerBlocks.put(p, true);
			}
		}
	}
	
	private void checkInnerRoom(BlockPos aktPos){
		ArrayList<BlockPos> juniorInnerBlocks= new ArrayList();
		recCheckInnerRoom(aktPos, juniorInnerBlocks);
		for(BlockPos p: juniorInnerBlocks){
			innerBlocks.remove(p);
			outerBlocks.put(p, true);
		}
	}
	
	private void recCheckInnerRoom(BlockPos aktPos, ArrayList<BlockPos> list){
		if(!map.containsKey(aktPos)){
			list.add(aktPos);
			for(BlockPos p: getNeighbours(aktPos)){
				recCheckInnerRoom(p, list);
			}
			 
		}
	}
	
	private void checkAfterAdding(BlockPos pos){
		  HashMap<BlockPos, Boolean> neighbours= new HashMap();
		  for(BlockPos p: getNeighbours(pos)){
			  neighbours.put(p, true);			  
		  }
		  HashMap<BlockPos, Boolean> possibleInnerRoom= new HashMap();
		  for(BlockPos p: neighbours.keySet()){
			recCheckAfterAdding(p, possibleInnerRoom);
		  	for(BlockPos o: possibleInnerRoom.keySet()){
		  		innerBlocks.put(o, true);
		  	}
		  }
		  
		   
	}
	
	private void recCheckAfterAdding(BlockPos pos, HashMap possibleInnerRoom){
		if(!map.containsKey(pos)&& !possibleInnerRoom.containsKey(pos)){
			if(outerOutBlocks.containsKey(pos)){
				rekHelper = false;
				possibleInnerRoom.clear();
				return;
			}
			possibleInnerRoom.put(pos, true);
			for(BlockPos p: getNeighbours(pos)){
				if(rekHelper){
					recCheckAfterAdding(p, possibleInnerRoom);
				}
			}
			
		}
		
		
	}
	
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
	
	private void impendEdges(BlockPos pos){
		BlockPos span = Vec3Op.subtract(maxPos, minPos);
		if(pos.getX() == maxPos.getX()){
			if(!otherInYZPane(span.getX())){
				maxPos = Vec3Op.subtract(maxPos, new BlockPos(1,0,0));
			}
		} else if(pos.getX() == minPos.getX()){
			if(!otherInYZPane(0)){
				minPos = minPos.add(new BlockPos(1,0,0));
			}
		}
		if(pos.getY() == maxPos.getY()){
			if(!otherInXZPane(span.getY())){
				maxPos = Vec3Op.subtract(maxPos, new BlockPos(0,1,0));
			}
		} else if(pos.getY() == minPos.getY()){
			if(!otherInXZPane(0)){
				minPos = minPos.add(new BlockPos(0,1,0));
			}
		}
		if(pos.getZ() == maxPos.getZ()){
			if(!otherInXYPane(span.getZ())){
				maxPos = Vec3Op.subtract(maxPos, new BlockPos(0,0,1));
			}
		} else if(pos.getZ() == minPos.getZ()){
			if(!otherInXYPane(0)){
				minPos = minPos.add(new BlockPos(0,0,1));
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
	
	public void rotate(BlockPos origin, int turn){
		BlockPos rotateOrigin = Vec3Op.subtract(origin, this.origin);
		Set<BlockPos> posSet = map.keySet();
		HashMap nextMap = new HashMap<BlockPos, Boolean>();
		for(Iterator<BlockPos> it = posSet.iterator(); it.hasNext();){
			BlockPos pos = it.next();
			BlockPos nextPos = Turn.getRotatedPos(pos, rotateOrigin, new BlockPos(0,0,0), turn);
			nextMap.put(nextPos, true);
		}
		map = nextMap;
		this.origin = Turn.getRotatedPos(this.origin, origin, new BlockPos(0,0,0), turn);
		this.maxPos = Turn.getRotatedPos(this.maxPos, origin, new BlockPos(0,0,0), turn);
		this.minPos = Turn.getRotatedPos(this.minPos, origin, new BlockPos(0,0,0), turn);
	}
	
	public void showDebug(World world){
		
	
				
				ArrayList<BlockPos> positions = new ArrayList<BlockPos>();
	
				for(BlockPos pos : calculateFrameBlocks(new BlockPos(minPos.getX()-1, minPos.getY()-1, minPos.getZ()-1),new BlockPos(minPos.getX()+1, minPos.getY()+1, minPos.getZ()+1)).keySet()){
					positions.add(pos.add(origin));
				}
				for(BlockPos po : positions)
				{
					world.setBlockState(po, Block.getStateById(4));
				}
				
			
			

		
		
	}
	
	public void showDebugOld(World world){
		ArrayList<BlockPos> positions = getPositions();
		for(BlockPos pos : positions){
			world.setBlockState(pos, Block.getStateById(2));
		}
	}
}
