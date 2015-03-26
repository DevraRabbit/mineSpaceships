package com.minespaceships.mod.spaceship;

public class ShipInformation {
	public static float speedFactor = 0.1f;
	public static float shieldFactor = 1;
	
	public static float getShipSpeed(Spaceship ship){
		float hardness = ship.getHardness();
		if(hardness != 0){
			return (float)(ship.getActiveEnginesCount())/hardness;
		} else {
			return 0;
		}
	}
	public static float getShipShields(Spaceship ship){
		int blockCount = ship.getSize();
		if(blockCount != 0){
			return ship.getActiveShieldsCount()/blockCount;
		} else {
			return 0;
		}
	}
}
