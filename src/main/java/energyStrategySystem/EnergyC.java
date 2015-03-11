package energyStrategySystem;

public interface EnergyC {
	
	
	/**
	 * 
	 * @return Energy
	 */
	int getEnergy();
	
	/**
	 *
	 * @return Priority 1, 2 or 3
	 */
	int getPriority();
	
	/**
	 * 
	 * @return Status, false wenn ausgeschaltet.
	 */
	boolean getStatus();
	
	void setStatus(boolean b);

}
