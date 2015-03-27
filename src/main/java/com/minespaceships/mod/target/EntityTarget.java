package com.minespaceships.mod.target;

import java.util.Comparator;

import net.minecraft.entity.Entity;
import net.minecraft.util.BlockPos;

public class EntityTarget extends Target {
	private Entity entity;
	public EntityTarget(Entity entity){
		this.setEntity(entity);
	}
	@Override
	public BlockPos getTarget() {
		return getEntity().getPosition();
	}
	public Entity getEntity() {
		return entity;
	}
	public void setEntity(Entity entity) {
		this.entity = entity;
	}	
}
