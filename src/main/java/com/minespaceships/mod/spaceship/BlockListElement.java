package com.minespaceships.mod.spaceship;

import net.minecraft.util.BlockPos;

public class BlockListElement {
	private BlockListElement smaller;
	private BlockListElement bigger;
	private BlockPos pos;
	
	public BlockListElement(BlockPos pos){
		this.pos = pos;
	}
	public void put(BlockPos sortPos){
		int isSmaller = isSmallerThan(sortPos);
		switch(isSmaller){
		case 0:
			if(bigger != null){
				bigger.put(sortPos);
			} else {
				bigger = new BlockListElement(sortPos);
			}
		case 1:
			if(smaller != null){
				smaller.put(sortPos);
			} else {
				smaller = new BlockListElement(sortPos);
			}			
		}
	}
	public boolean contains(BlockPos sortPos){
		int isSmaller = isSmallerThan(sortPos);
		switch(isSmaller){
		case 0:
			if(bigger != null){
				return bigger.contains(sortPos);
			} else {
				return false;
			}
		case 1:
			if(smaller != null){
				return smaller.contains(sortPos);
			} else {
				return false;
			}	
		case 2:
			return true;
		default:
			return false;	
		}
	}
	public void delete(BlockPos pos, BlockListElement previous, boolean isBigger){
		int isSmaller = isSmallerThan(pos);
		switch(isSmaller){
		case 0:
			if(bigger != null){
				bigger.delete(pos, this, true);
			} 
		case 1:
			if(smaller != null){
				smaller.delete(pos, this, false);
			}	
		case 2:
			if(bigger == null && smaller == null){
				if(isBigger){previous.bigger = null;}
				else{previous.smaller = null;}
			}
			if(bigger != null && smaller != null){
				if(bigger.smaller != null){
					this.pos = bigger.smaller.pos;
					bigger.smaller.delete(pos, bigger, false);
				} else if (smaller.bigger != null){
					
				}
			}
		}
	}
	/**	 * 
	 * @param sortPos
	 * @return 0 if false, 1 if true, 2 if equal
	 */
	public int isSmallerThan(BlockPos sortPos){
		if(sortPos.getX() < pos.getX()){
			return 1;
		} else if (sortPos.getX() > pos.getX()) {
			return 0;
		} else {
			if(sortPos.getY() < pos.getY()){
				return 1;
			} else if (sortPos.getY() > pos.getY()) {
				return 0;
			} else {
				if(sortPos.getZ() < pos.getZ()){
					return 1;
				} else if (sortPos.getZ() > pos.getZ()) {
					return 0;
				} else {
					return 2;
				}
			}
		}
	}
}
