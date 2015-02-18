package com.minespaceships.util;

import net.minecraft.util.Vec3;

public class Vec3Op{
	public static Vec3 scale(Vec3 vec, double ratio){
		return new Vec3(vec.xCoord * ratio, vec.yCoord * ratio, vec.zCoord * ratio);
	}
}
