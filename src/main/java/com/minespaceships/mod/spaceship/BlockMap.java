package com.minespaceships.mod.spaceship;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import net.minecraft.client.Minecraft;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class BlockMap {
	private HashMap<BlockPos, Boolean> map;
	private BlockPos maxPos;
	private BlockPos minPos;
	private BlockPos origin;

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
							Minecraft.getMinecraft().thePlayer.sendChatMessage("true");
							return true;
						}
					}
				}
			}
		}
		Minecraft.getMinecraft().thePlayer.sendChatMessage("false");
		return false;
	}
}
