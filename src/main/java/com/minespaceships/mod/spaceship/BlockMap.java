package com.minespaceships.mod.spaceship;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSponge;
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
			
	public BlockMap(BlockPos originPoint){
		map = new HashMap<BlockPos, Boolean>();
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
	
	public void add(BlockPos pos){
		map.put(pos.subtract(origin), true);
		resize(pos.subtract(origin));
	}
	
	public boolean contains(BlockPos pos){
		return map.containsKey(pos.subtract(origin));
	}
	
	public void remove(BlockPos pos, World world){
		map.remove(pos.subtract(origin));
		impendEdges(pos.subtract(origin), world);
	}
	
	public BlockPos getMaxPos(){
		return maxPos.add(origin);
	}
	
	public BlockPos getMinPos(){
		return minPos.add(origin);
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
		outerBlocks = new HashMap(); 	//initializes outerBlocks
		outerOutBlocks = new HashMap();
		spannedRectangle = new HashMap();
		calculateOuterOutBlocks();
		spanRectangle();
		for(BlockPos l : outerOutBlocks.keySet())
		{
			checkRoom(l);
		}
		innerBlocks = null; 	//initializes innerBlocks
		for(BlockPos l : spannedRectangle.keySet())
		{
			if(!map.containsKey(l) && !outerBlocks.containsKey(l))
			{
				innerBlocks.put(l, true);
			}
		}
	}
	
	
	
	
	
	private void checkRoom(BlockPos aktPos)
	{
		if(!map.containsKey(aktPos) && !outerBlocks.containsKey(aktPos) && spannedRectangle.containsKey(aktPos))
		{
			outerBlocks.put(aktPos, true);
			for(BlockPos p: getNeighboursInRectangle(aktPos)){
				checkRoom(p);
			}
		}
	}
	
	private ArrayList<BlockPos> getNeighboursInRectangle(BlockPos aktPos){
		ArrayList<BlockPos> toReturn= new ArrayList();
		toReturn.add(new BlockPos(aktPos.getX()-1, aktPos.getY(), aktPos.getZ()));
		toReturn.add(new BlockPos(aktPos.getX()+1, aktPos.getY(), aktPos.getZ()));
		toReturn.add(new BlockPos(aktPos.getX(), aktPos.getY()-1, aktPos.getZ()));
		toReturn.add(new BlockPos(aktPos.getX(), aktPos.getY()+1, aktPos.getZ()));
		toReturn.add(new BlockPos(aktPos.getX(), aktPos.getY(), aktPos.getZ()-1));
		toReturn.add(new BlockPos(aktPos.getX(), aktPos.getY(), aktPos.getZ()+1));
		return toReturn;
		
		
	}
	
	private void calculateOuterOutBlocks()
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
		this.outerOutBlocks = aktList;
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
	
	private void impendEdges(BlockPos pos, World world){
		BlockPos span = maxPos.subtract(minPos);
		if(pos.getX() == maxPos.getX()){
			if(!otherInYZPane(span.getX(), world)){
				maxPos = maxPos.subtract(new BlockPos(1,0,0));
			}
		} else if(pos.getX() == minPos.getX()){
			if(!otherInYZPane(0, world)){
				minPos = minPos.add(new BlockPos(1,0,0));
			}
		}
		if(pos.getY() == maxPos.getY()){
			if(!otherInXZPane(span.getY(), world)){
				maxPos = maxPos.subtract(new BlockPos(0,1,0));
			}
		} else if(pos.getY() == minPos.getY()){
			if(!otherInXZPane(0, world)){
				minPos = minPos.add(new BlockPos(0,1,0));
			}
		}
		if(pos.getZ() == maxPos.getZ()){
			if(!otherInXYPane(span.getZ(), world)){
				maxPos = maxPos.subtract(new BlockPos(0,0,1));
			}
		} else if(pos.getZ() == minPos.getZ()){
			if(!otherInXYPane(0, world)){
				minPos = minPos.add(new BlockPos(0,0,1));
			}
		}
	}
	
	private boolean otherInYZPane(int index, World world){
		BlockPos span = maxPos.subtract(minPos);
		for(int y = 0; y < span.getY(); y++){
			for(int z = 0; z < span.getZ(); z++){
				BlockPos pos = minPos.add(minPos.getX()+index, minPos.getY()+y, minPos.getZ()+z).add(origin);
				if(!world.isAirBlock(pos) && contains(pos)){
					return true;
				}
			}
		}
		return false;
	}
	
	private boolean otherInXZPane(int index, World world){
		BlockPos span = maxPos.subtract(minPos);
		for(int x = 0; x < span.getX(); x++){
			for(int z = 0; z < span.getZ(); z++){
				BlockPos pos = minPos.add(minPos.getX()+x, minPos.getY()+index, minPos.getZ()+z).add(origin);
				if(!world.isAirBlock(pos) && contains(pos)){
					return true;
				}
			}
		}
		return false;
	}
	
	private boolean otherInXYPane(int index, World world){
		BlockPos span = maxPos.subtract(minPos);
		for(int x = 0; x < span.getX(); x++){
			for(int y = 0; y < span.getY(); y++){
				BlockPos pos = minPos.add(minPos.getX()+x, minPos.getY()+y, minPos.getZ()+index).add(origin);
				if(!world.isAirBlock(pos) && contains(pos)){
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
		BlockPos rotateOrigin = origin.subtract(this.origin);
		Set<BlockPos> posSet = map.keySet();
		HashMap nextMap = new HashMap<BlockPos, Boolean>();
		for(Iterator<BlockPos> it = posSet.iterator(); it.hasNext();){
			BlockPos pos = it.next();
			BlockPos nextPos = Turn.getRotatedPos(null, pos, rotateOrigin, new BlockPos(0,0,0), turn);
			nextMap.put(nextPos, true);
		}
		map = nextMap;
		this.origin = Turn.getRotatedPos(null, this.origin, origin, new BlockPos(0,0,0), turn);
	}
	
	public void showDebug(World world){
		refreshOuterBlocks();
		ArrayList<BlockPos> positions = new ArrayList<BlockPos>();
		Set<BlockPos> keys = outerBlocks.keySet();
		for(BlockPos pos : keys){
			positions.add(pos.add(origin));
		}
		for(BlockPos po : positions)
		{
			world.setBlockState(po, Block.getStateById(4));
		}
	}
	
	public void showDebug11(World world){
		ArrayList<BlockPos> positions = getPositions();
		for(BlockPos pos : positions){
			world.setBlockState(pos, Block.getStateById(2));
		}
	}
}
