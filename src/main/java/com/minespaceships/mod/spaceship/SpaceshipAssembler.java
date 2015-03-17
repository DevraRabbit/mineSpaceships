package com.minespaceships.mod.spaceship;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.minespaceships.util.Vec3Op;

import net.minecraft.block.Block;
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
	public void put(Block state, BlockPos pos){
		BlockPos part = Vec3Op.subtract(pos, origin);
		Class c = state.getClass();
		if(!parts.containsKey(c)){
			parts.put(c, new ArrayList<BlockPos>());
		}
		if(!parts.get(c).contains(part)){
			parts.get(c).add(part);
		}
	}
	public void remove(Block state, BlockPos pos){
		BlockPos part = Vec3Op.subtract(pos, origin);
		ArrayList<BlockPos> toremove = parts.get(state.getClass());
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
