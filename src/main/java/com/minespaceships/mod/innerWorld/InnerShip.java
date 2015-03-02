package com.minespaceships.mod.innerWorld;

import com.minespaceships.mod.overhead.CustomGuiChat;
import com.minespaceships.mod.spaceship.BlockMap;

import java.util.ArrayList;

import net.minecraft.util.BlockPos;
/**
 * 
 * @author ovae
 * @version 20150302
 */
public class InnerShip {

	private InnerShip(){}

	public ArrayList<BlockPos> calculateInnerShip(CustomGuiChat terminal){
		ArrayList<BlockPos> blockList = new ArrayList<BlockPos>();
		ArrayList<BlockPos> innerBlocks = new ArrayList<BlockPos>();
		BlockMap blockmap =  terminal.getChatRegisterEntity().getShip().getBlockMap();
		BlockPos maxPosition = blockmap.getMaxPos();
		BlockPos minPosition = blockmap.getMinPos();

		//Get a list of all blocks, witch are a part of the spaceship.
		for(int x = minPosition.getX(); x < maxPosition.getX(); x++){
			for(int y = minPosition.getY(); y < maxPosition.getY(); y++){
				for(int z = minPosition.getZ(); z < maxPosition.getZ(); z++){
					if(blockmap.contains(new BlockPos(x,y,z))){
						blockList.add(new BlockPos(x,y,z));
					}
				}
			}
		}

		/*
		 * Creates from the current block of the blockList and the next block of the list an vector.
		 * All blocks between the current and the next blocks, are going to be added to the innerBlocks list.
		 * If one of these block is already in the innerBlocks list, then it is not added to the list.
		 */
		for(int i=0; i<(blockList.size()-1); i++){
			BlockPos current = blockList.get(i);
			BlockPos next = blockList.get(i+1);
			try{
				int alpha = VectorUtil.calculateScalar(current, next);
				for(int x = current.getX(); x < next.getX(); x++){
					for(int y = current.getY(); y < next.getY(); y++){
						for(int z = current.getZ(); z < next.getZ(); z++){
							//Do stuff
						}
					}
				}
			}catch(NoScalarException n){
				break;
			}
			//Do stuff
		}

		return innerBlocks;
	}

}
