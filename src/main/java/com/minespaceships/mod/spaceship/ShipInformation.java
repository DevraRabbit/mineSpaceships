package com.minespaceships.mod.spaceship;

import net.minecraft.client.Minecraft;

public class ShipInformation {
	public static float speedFactor = 0.1f;
	public static float shieldFactor = 30;
	public static float strengthFactor = 0.5f;
	private static float meanHardness = 3.11f;
	private static float maxHardness = 100;
	private static float middlePoint = 0.05f;
	
	public static float getShipSpeed(Spaceship ship){
		float hardness = getShipWeight(ship);
		if(hardness != 0){
			return (float)(ship.getActiveEnginesCount())/hardness;
		} else {
			return 0;
		}
	}
	public static float getShipShields(Spaceship ship){
		return (float)(ship.getActiveShieldsCount())*strengthFactor;
	}
	public static float getShipStrength(Spaceship ship){
		return (float)(ship.getActivePhaserCount())*strengthFactor;
	}
	public static float getShipWeight(Spaceship ship){
		return ship.getHardness();
	}
}
