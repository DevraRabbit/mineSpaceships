package com.minespaceships.mod.WorldProviderSpace;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.world.Teleporter;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class TeleportToSpace {

	
	
	
	 private boolean isDead;
	 private World worldObj;
	 private int dimension;
//	 private final Teleporter worldTeleporter = new Teleporter(worldServer);
	 

	public void travelToSpace(int dimensionId,Entity entityIn)
	    {
	        if (!this.worldObj.isRemote && !this.isDead)
	        {
	            this.worldObj.theProfiler.startSection("changeDimension");
	            MinecraftServer minecraftserver = MinecraftServer.getServer();
	            int j = this.dimension;
	            WorldServer worldserver = minecraftserver.worldServerForDimension(j);
	            WorldServer worldserver1 = minecraftserver.worldServerForDimension(dimensionId);
	            this.dimension = dimensionId;

	            this.worldObj.removeEntity(entityIn);
	            this.isDead = false;
	            this.worldObj.theProfiler.startSection("reposition");
	           transferEntityToWorld(entityIn, j, worldserver, worldserver1, null);
	            this.worldObj.theProfiler.endStartSection("reloading");
	            Entity entity = EntityList.createEntityByName(EntityList.getEntityString(entityIn), worldserver1);
	            if (entity != null)
	            {
	                entity.copyDataFromOld(entityIn);
	                worldserver1.spawnEntityInWorld(entity);
	            }

	            this.isDead = true;
	            this.worldObj.theProfiler.endSection();
	            worldserver.resetUpdateEntityTick();
	            worldserver1.resetUpdateEntityTick();
	            this.worldObj.theProfiler.endSection();
	        }
	    }
	
	
	public void transferEntityToWorld(Entity entityIn, int p_82448_2_, WorldServer p_82448_3_, WorldServer p_82448_4_, net.minecraft.world.Teleporter teleporter)
    {
        net.minecraft.world.WorldProvider pOld = p_82448_3_.provider;
        net.minecraft.world.WorldProvider pNew = p_82448_4_.provider;
        double moveFactor = pOld.getMovementFactor() / pNew.getMovementFactor();
        double d0 = entityIn.posX * moveFactor;
        double d1 = entityIn.posZ * moveFactor;
        double d2 = 8.0D;
        float f = entityIn.rotationYaw;
        p_82448_3_.theProfiler.startSection("moving");        
        if (entityIn.dimension == 1)
        {
            BlockPos blockpos;

            if (p_82448_2_ == 1)
            {
                blockpos = p_82448_4_.getSpawnPoint();
            }
            else
            {
                blockpos = p_82448_4_.func_180504_m();
            }

            d0 = (double)blockpos.getX();
            entityIn.posY = (double)blockpos.getY();
            d1 = (double)blockpos.getZ();
            entityIn.setLocationAndAngles(d0, entityIn.posY, d1, 90.0F, 0.0F);

            if (entityIn.isEntityAlive())
            {
                p_82448_3_.updateEntityWithOptionalForce(entityIn, false);
            }
        }

        p_82448_3_.theProfiler.endSection();

        if (p_82448_2_ != 1)
        {
            p_82448_3_.theProfiler.startSection("placing");
            d0 = (double)MathHelper.clamp_int((int)d0, -29999872, 29999872);
            d1 = (double)MathHelper.clamp_int((int)d1, -29999872, 29999872);

            if (entityIn.isEntityAlive())
            {
                entityIn.setLocationAndAngles(d0, entityIn.posY, d1, entityIn.rotationYaw, entityIn.rotationPitch);
                teleporter.placeInPortal(entityIn, f);
                p_82448_4_.spawnEntityInWorld(entityIn);
                p_82448_4_.updateEntityWithOptionalForce(entityIn, false);
            }

            p_82448_3_.theProfiler.endSection();
        }

        entityIn.setWorld(p_82448_4_);
    }
}
