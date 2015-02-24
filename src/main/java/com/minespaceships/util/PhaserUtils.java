package com.minespaceships.util;

import net.minecraft.util.BlockPos;
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
			//
			current = source.add((Vec3i) getNextPhaserHit(ray)[0]);
			ray = (Vec3) getNextPhaserHit(ray)[1];
			if (!world.isAirBlock(current)) {
				strength -= world.getBlockState(current).getBlock()
						.getBlockHardness(world, current);
				if (strength >= 0) {
					world.destroyBlock(current, false);
				}
			}
			maxrange--;
		}
	}

	private static Object[] getNextPhaserHit(Vec3 ray) {
		double x, y, z;
		x = ray.xCoord;
		y = ray.yCoord;
		z = ray.zCoord;

		// finde nächste volle Blockposition auf jeder Achse
		double x_next, y_next, z_next;

		x_next = (x > 0 ? Math.ceil(x) : Math.floor(x));
		y_next = (y > 0 ? Math.ceil(y) : Math.floor(y));
		z_next = (z > 0 ? Math.ceil(z) : Math.floor(z));

		if (x == x_next && x != 0 || y == y_next && y != 0 || z == z_next
				&& z != 0) {
			return getNextPhaserHit(scale(ray, 1.01));
		}

		double x_cur, y_cur, z_cur;
		x_cur = (x < 0 ? Math.ceil(x) : Math.floor(x));
		y_cur = (y < 0 ? Math.ceil(y) : Math.floor(y));
		z_cur = (z < 0 ? Math.ceil(z) : Math.floor(z));

		BlockPos block_hit = null;
		double scale = 1.0;

		if (isBiggestAbsolute(x - x_next, y - y_next, z - z_next)) {
			// auf X skalieren
			scale = x_next / x;

			block_hit = new BlockPos(x_next, y_cur, z_cur);
		} else if (isBiggestAbsolute(y - y_next, x - x_next, z - z_next)) {
			// auf Y skalieren
			scale = y_next / y;

			block_hit = new BlockPos(x_cur, y_next, z_cur);
		} else if (isBiggestAbsolute(z - z_next, y - y_next, x - x_next)) {
			// auf Z skalieren
			scale = z_next / z;

			block_hit = new BlockPos(x_cur, y_cur, z_next);
		}

		ray = scale(ray, scale);

		Object ret[] = { block_hit, ray };
		return ret;
	}

	private static Vec3 scale(Vec3 ray, double scale) {
		return new Vec3(ray.xCoord * scale, ray.yCoord * scale, ray.zCoord * scale);
	}

	private static boolean isBiggestAbsolute(double test, double contestant1,
			double contestant2) {
		return (Math.abs(test) == Math.max(Math.abs(test),
				Math.abs(contestant1)) && Math.abs(test) == Math.max(
				Math.abs(test), Math.abs(contestant2)));
	}
}
