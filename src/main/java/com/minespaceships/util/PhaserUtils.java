package com.minespaceships.util;

import java.util.List;

import com.minespaceships.mod.overhead.PhaserEffect;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFire;
import net.minecraft.block.BlockTNT;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.init.Blocks;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.Vec3;
import net.minecraft.util.Vec3i;
import net.minecraft.world.World;

public class PhaserUtils {

	public static void shoot(BlockPos source, BlockPos target, double strength, int maxrange, World world) {
		Vec3 direction = new Vec3(target.getX() - source.getX(), target.getY()
				- source.getY(), target.getZ() - source.getZ());
		shoot(source, direction, strength, maxrange, world);
	}

	public static void shoot(BlockPos source, Vec3 direction, double strength, int maxrange, World world) {
		
		BlockPos current;
		
		//Normalize vector, so no block is safe!11!!
		Vec3 ray = direction.normalize();

		while (strength > 0 && maxrange > 0) {
			Object[] nextHitInfo = getNextPhaserHit(ray);
			current = source.add((BlockPos) nextHitInfo[0]);
			ray = (Vec3) nextHitInfo[1];
			
			if (!world.isAirBlock(current) && !(world.getBlockState(current).getBlock() instanceof BlockFire)) {
				strength -= world.getBlockState(current).getBlock()
						.getBlockHardness(world, current);
				if (strength >= 0) {
					world.destroyBlock(current, false);
					world.setBlockState(current, Blocks.fire.getDefaultState());
				}
	            
	            if(world.isAirBlock(current.down()))
	            world.setBlockState(current.down(), Blocks.fire.getDefaultState());

	            if(world.isAirBlock(current.up()))
	            world.setBlockState(current.up(), Blocks.fire.getDefaultState());

	            if(world.isAirBlock(current.north()))
	            world.setBlockState(current.north(), Blocks.fire.getDefaultState());

	            if(world.isAirBlock(current.south()))
	            world.setBlockState(current.south(), Blocks.fire.getDefaultState());

	            if(world.isAirBlock(current.east()))
	            world.setBlockState(current.east(), Blocks.fire.getDefaultState());

	            if(world.isAirBlock(current.west()))
	            world.setBlockState(current.west(), Blocks.fire.getDefaultState());
			}
			
        	double d0 = 2.0D;
            List list = world.getEntitiesWithinAABBExcludingEntity(null, new AxisAlignedBB(current.getX() - d0, current.getY() - d0, current.getZ() - d0, current.getX() + d0, current.getY() + 6.0D + d0, current.getZ() + d0));

            for (int i = 0; i < list.size(); ++i)
            {
                Entity entity = (Entity)list.get(i);
                EntityLightningBolt killer = new EntityLightningBolt(world, current.getX(), current.getX(), current.getZ());
                
                if (!net.minecraftforge.event.ForgeEventFactory.onEntityStruckByLightning(entity, killer))
                    entity.onStruckByLightning(killer);
            }
            
            world.spawnParticle(EnumParticleTypes.FIREWORKS_SPARK, ray.xCoord, ray.yCoord, ray.zCoord, 0, 0, 0, 0);
            
			maxrange--;
		}
	}

	private static Object[] getNextPhaserHit(Vec3 ray) {
		double x, y, z;
		x = ray.xCoord;
		y = ray.yCoord;
		z = ray.zCoord;

		// finde nächste volle Blockposition auf jeder Achse

		double x_cur, y_cur, z_cur;
		x_cur = (x < 0 ? Math.ceil(x) : Math.floor(x));
		y_cur = (y < 0 ? Math.ceil(y) : Math.floor(y));
		z_cur = (z < 0 ? Math.ceil(z) : Math.floor(z));

		BlockPos block_hit = null;
		ray = scaleToNextBlock(ray);

		x = ray.xCoord;
		y = ray.yCoord;
		z = ray.zCoord;
		x_cur = (x < 0 ? Math.ceil(x) : Math.floor(x));
		y_cur = (y < 0 ? Math.ceil(y) : Math.floor(y));
		z_cur = (z < 0 ? Math.ceil(z) : Math.floor(z));
		block_hit = new BlockPos(x_cur, y_cur, z_cur);

		Object ret[] = { block_hit, ray };
		return ret;
	}

	private static Vec3 scale(Vec3 ray, double scale) {
		return new Vec3(ray.xCoord * scale, ray.yCoord * scale, ray.zCoord * scale);
	}
	
	private static Vec3 scaleToNextBlock(Vec3 ray) {
		double x, y, z;
		x = ray.xCoord;
		y = ray.yCoord;
		z = ray.zCoord;
		
		double x_next, y_next, z_next;
		x_next = (x > 0 ? Math.ceil(x) : Math.floor(x));
		y_next = (y > 0 ? Math.ceil(y) : Math.floor(y));
		z_next = (z > 0 ? Math.ceil(z) : Math.floor(z));
		
		if (x == x_next && x != 0 || y == y_next && y != 0 || z == z_next
				&& z != 0) {
			ray = scale(ray, 1.0001);
		}
		
		Vec3 x_scaled, y_scaled, z_scaled;
		x_scaled = scale(ray, x_next / x);
		y_scaled = scale(ray, y_next / y);
		z_scaled = scale(ray, z_next / z);
		
		double x_length, y_length, z_length;
		x_length = x_scaled.lengthVector();
		y_length = y_scaled.lengthVector();
		z_length = z_scaled.lengthVector();

		if(Double.isNaN(x_length)) x_length = Double.MAX_VALUE;
		if(Double.isNaN(y_length)) y_length = Double.MAX_VALUE;
		if(Double.isNaN(z_length)) z_length = Double.MAX_VALUE;
		
		if (isSmallestAbsolute(x_length, y_length, z_length)) {
			// auf X skalieren
			return x_scaled;
		} else if (isSmallestAbsolute(y_length, x_length, z_length)) {
			return y_scaled;
		} else if (isSmallestAbsolute(z_length, y_length, x_length)) {
			// auf Z skalieren
			return z_scaled;
		}
		
		return null;
	}

	private static boolean isSmallestAbsolute(double test, double contestant1,
			double contestant2) {
		return (Math.abs(test) == Math.min(Math.abs(test),
				Math.abs(contestant1)) && Math.abs(test) == Math.min(
				Math.abs(test), Math.abs(contestant2)));
	}
}
