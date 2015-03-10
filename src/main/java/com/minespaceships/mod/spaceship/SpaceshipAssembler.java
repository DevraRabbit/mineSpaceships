package com.minespaceships.mod.spaceship;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockPos;

public class SpaceshipAssembler {
	HashMap<Class, ArrayList<BlockPos>> parts;
	private BlockPos origin;
	
	public SpaceshipAssembler(BlockPos origin){
		this.origin = origin;
		parts = new HashMap<Class, ArrayList<BlockPos>>();
	}
	public void setOrigin(BlockPos pos){
		origin = pos;
	}
	public void put(IBlockState state, BlockPos pos){
		BlockPos part = pos.subtract(origin);
		Class c = state.getBlock().getClass();
		if(!parts.containsKey(c)){
			parts.put(c, new ArrayList<BlockPos>());
		}
		if(!parts.get(c).contains(part)){
			parts.get(c).add(part);
		}
	}
	public void remove(IBlockState state, BlockPos pos){
		BlockPos part = pos.subtract(origin);
		ArrayList<BlockPos> toremove = parts.get(state.getBlock().getClass());
		if(toremove != null){
			toremove.remove(part);
		}
	}
	public ArrayList<BlockPos> getParts(Class c){
		ArrayList<BlockPos> instances = new ArrayList<BlockPos>();
		Set<Class> classes = parts.keySet();
		for(Class cls : classes){
			if(cls.isAssignableFrom(c)){
				instances.addAll(parts.get(cls));
			}
		}
		for(BlockPos pos : instances){
			pos = pos.add(origin);
		}
		return instances;
	}
	public void clear(){
		parts.clear();
	}
}
