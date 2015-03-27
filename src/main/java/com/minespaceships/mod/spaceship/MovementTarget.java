package com.minespaceships.mod.spaceship;

import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class MovementTarget {
	private BlockPos target;
	private int turn;
	private World world;
	public MovementTarget(BlockPos target, int turn, World world){
		this.setTarget(target);
		this.setTurn(turn);
		this.setWorld(world);
	}
	public BlockPos getTarget() {
		return target;
	}
	public void setTarget(BlockPos target) {
		this.target = target;
	}
	public World getWorld() {
		return world;
	}
	public void setWorld(World world) {
		this.world = world;
	}
	public int getTurn() {
		return turn;
	}
	public void setTurn(int turn) {
		this.turn = turn;
	}
}
