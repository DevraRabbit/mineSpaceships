package com.minespaceships.mod.spaceship;

public class ShipInformation {
	public static float speedFactor = 0.1f;
	public static float shieldFactor = 30;
	public static float strengthFactor = 1;
	
	public static float getShipSpeed(Spaceship ship){
		float hardness = getShipWeight(ship);
		if(hardness != 0){
			return (float)(ship.getActiveEnginesCount())/hardness;
		} else {
			return 0;
		}
	}
	public static float getShipShields(Spaceship ship){
		int blockCount = ship.getSize();
		if(blockCount != 0){
			return ((float)(ship.getActiveShieldsCount())/(float)(blockCount))*shieldFactor;
		} else {
			return 0;
		}
	}
	public static float getShipStrength(Spaceship ship){
		return ship.getActivePhaserCount()*strengthFactor;
	}
	public static float getShipWeight(Spaceship ship){
		return ship.getHardness();
	}
}
