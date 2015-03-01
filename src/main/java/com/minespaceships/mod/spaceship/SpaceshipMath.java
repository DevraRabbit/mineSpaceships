package com.minespaceships.mod.spaceship;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;

import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Vec3i;
import net.minecraft.world.World;

public class SpaceshipMath {
	public static BlockMap getConnectedPositions(BlockPos pos, World worldIn, int maxSize){
		BlockMap blockMap = new BlockMap(pos);
		blockMap.add(pos);		
		Vector<BlockPos> neighbors = new Vector<BlockPos>();
		neighbors.add(pos);
		int[] safety = {maxSize};
		while(safety[0] >= 0 && neighbors.size() != 0){
			Vector<BlockPos> nextNeighbors = new Vector<BlockPos>();
			for(BlockPos nPos : neighbors){
				nextNeighbors = writeValidNeighbors(nPos, worldIn, blockMap, nextNeighbors, safety);
			}
			neighbors.clear();
			neighbors.addAll(nextNeighbors);
		}
		if(safety[0] < 0){
			return null;
		} else {
			return blockMap;
		}
		
	}
	private static Vector<BlockPos> writeValidNeighbors(BlockPos pos, World worldIn, BlockMap blockMap, Vector<BlockPos> neighbors, int[] maxSize){
		maxSize[0]--;
		if(maxSize[0] >= 0){
			for(int x = -1; x < 2; x++){
				for(int y = -1; y < 2; y++){
					for(int z = -1; z < 2; z++){
						if(x != 0 || y != 0 || z != 0){
							BlockPos neighbor = pos.add(x,y,z);
							if(!blockMap.contains(neighbor)){
								if(!worldIn.isAirBlock(neighbor)){
									blockMap.add(neighbor);
									neighbors.addElement(neighbor);
								}
							} else {
								int i = 0;
							}
						}
					}
				}
			}
		}
		return neighbors;
	}
	
	public static ArrayList<EnumFacing> getPossibleConnections(World worldIn, BlockPos pos){
		ArrayList<EnumFacing> facings = new ArrayList<EnumFacing>();
		if(!worldIn.canBlockBePlaced(worldIn.getBlockState(pos).getBlock(), pos, true, EnumFacing.NORTH, null, null)){facings.add(EnumFacing.NORTH);}
		if(!worldIn.canBlockBePlaced(worldIn.getBlockState(pos).getBlock(), pos, true, EnumFacing.EAST, null, null)){facings.add(EnumFacing.EAST);}
		if(!worldIn.canBlockBePlaced(worldIn.getBlockState(pos).getBlock(), pos, true, EnumFacing.SOUTH, null, null)){facings.add(EnumFacing.SOUTH);}
		if(!worldIn.canBlockBePlaced(worldIn.getBlockState(pos).getBlock(), pos, true, EnumFacing.WEST, null, null)){facings.add(EnumFacing.WEST);}
		if(!worldIn.canBlockBePlaced(worldIn.getBlockState(pos).getBlock(), pos, true, EnumFacing.UP, null, null)){facings.add(EnumFacing.UP);}
		if(!worldIn.canBlockBePlaced(worldIn.getBlockState(pos).getBlock(), pos, true, EnumFacing.DOWN, null, null)){facings.add(EnumFacing.DOWN);}
		return facings;
	}
	
}
