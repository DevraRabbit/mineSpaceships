package com.minespaceships.mod.spaceship;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import net.minecraft.block.state.IBlockState;

public class SpaceshipAssembler {
	HashMap<Class, ArrayList<IBlockState>> parts;
	public SpaceshipAssembler(){
		parts = new HashMap<Class, ArrayList<IBlockState>>();
	}
	public void put(IBlockState part){
		Class c = part.getBlock().getClass();
		if(!parts.containsKey(c)){
			parts.put(c, new ArrayList<IBlockState>());
		}
		if(!parts.get(c).contains(part)){
			parts.get(c).add(part);
		}
	}
	public void remove(IBlockState part){
		ArrayList<IBlockState> toremove = parts.get(part.getBlock().getClass());
		if(toremove != null){
			toremove.remove(part);
		}
	}
	public ArrayList<IBlockState> getParts(Class c){
		ArrayList<IBlockState> instances = new ArrayList<IBlockState>();
		Set<Class> classes = parts.keySet();
		for(Class cls : classes){
			if(cls.isAssignableFrom(c)){
				instances.addAll(parts.get(cls));
			}
		}
		return instances;
	}
	public void clear(){
		parts.clear();
	}
}
