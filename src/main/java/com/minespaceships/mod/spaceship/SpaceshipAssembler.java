package com.minespaceships.mod.spaceship;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
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
	public BlockPos getOrigin(){
		return origin;
	}
	public Set<Class> getTypes(){
		return parts.keySet();
	}
	public void put(Class c, BlockPos pos){
		BlockPos part = Vec3Op.subtract(pos, origin);
		if(!parts.containsKey(c)){
			parts.put(c, new ArrayList<BlockPos>());
		}
		if(!parts.get(c).contains(part)){
			parts.get(c).add(part);
		}
	}
	public void remove(Class c, BlockPos pos){
		BlockPos part = Vec3Op.subtract(pos, origin);
		ArrayList<BlockPos> toremove = parts.get(c);
		if(toremove != null){
			toremove.remove(part);
		}
	}
	public ArrayList<BlockPos> getParts(Class c){
		ArrayList<BlockPos> instances = new ArrayList<BlockPos>();
		Set<Class> classes = parts.keySet();
		for(Class cls : classes){
			if(c.isAssignableFrom(cls)){
				instances.addAll(parts.get(cls));
			}
		}
		for(ListIterator<BlockPos> it = instances.listIterator(); it.hasNext();){
			BlockPos pos = it.next();
			it.remove();
			it.add(pos.add(origin));
		}
		return instances;
	}
//	public void rotate(BlockPos origin, int turn){
//		if(turn == 0) return;
//		BlockPos rotateOrigin = Vec3Op.subtract(origin, this.origin);
//		Set<Class> classSet = parts.keySet();
//		for(Class c : classSet){
//			ArrayList<BlockPos> nextPositions = new ArrayList<BlockPos>();
//			List<BlockPos> momPositions = parts.get(c);
//			for(BlockPos pos : momPositions){
//				nextPositions.add(Turn.getRotatedPos(pos, rotateOrigin, new BlockPos(0,0,0), turn));
//			}
//			parts.put(c,  nextPositions);
//		}
//		this.origin = Turn.getRotatedPos(this.origin, rotateOrigin, new BlockPos(0,0,0), turn);
//	}
	public void clear(){
		parts.clear();
	}
}
