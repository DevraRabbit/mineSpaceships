package com.minespaceships.mod.target;

import java.util.Comparator;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class EntityTarget extends Target {
	private int entityID;
	public static final String entityKey = "entity";
	
	public EntityTarget(Entity entity){
		this.setEntity(entity);
	}
	public EntityTarget(NBTTagCompound c) {
		entityID = c.getInteger(entityKey);
	}
	@Override
	public BlockPos getTarget(World world) {
		Entity ent = getEntity(world);
		if(ent != null){
			return ent.getPosition();
		} else {
			System.out.println("Lost target");
			return null;
		}
	}
	public Entity getEntity(World world) {
		return world.getEntityByID(entityID);
	}
	public void setEntity(Entity entity) {
		this.entityID = entity.getEntityId();
	}
	@Override
	public void writeToNBT(NBTTagCompound c) {
		c.setInteger(entityKey, entityID);		
	}	
}
