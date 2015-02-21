package com.minespaceships.mod.spaceship;

import com.minespaceships.util.BlockCopier;

import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class Turn {
    public static int LEFT = -1;
    public static int RIGHT = 1;

	public static void ninetyDeg(final World world, final BlockPos origin, final int leftRight) {
		if (world != null && origin != null && (leftRight == LEFT || leftRight == RIGHT)) {
			BlockPos source, target;
			for (int x = -10; x <= 10; x++) {
				for (int z = -10; z <= 10; z++) {
					source = new BlockPos(origin.getX() + x, origin.getY(), origin.getZ() + z);
					target = new BlockPos(origin.getX() - leftRight * z, origin.getY() + 1, origin.getZ() + leftRight * x);
					BlockCopier.copyBlock(world, source, target);
				}
			}
		}
	}
	
	public static void around(final World world, final BlockPos origin) {
		if (world != null && origin != null) {
			BlockPos source, target;
			for (int x = -10; x <= 10; x++) {
				for (int z = -10; z <= 10; z++) {
					source = new BlockPos(origin.getX() + x, origin.getY(), origin.getZ() + z);
					target = new BlockPos(origin.getX() - x, origin.getY() + 1, origin.getZ() - z);
					BlockCopier.copyBlock(world, source, target);
				}
			}
		}
	}
	
}
