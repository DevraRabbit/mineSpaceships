package energyStrategySystem;

public class EnergyConsumer {
	private int consumedEnergy;
	private int prior;
	
	public EnergyConsumer(int energy, int priority){
		if(priority<1||priority>3){
			throw new IllegalArgumentException ("priority must be 1, 2 or 3");
		}
		prior= priority;
		consumedEnergy=energy;
	}
	
	public int getEnergy(){
		return consumedEnergy;
	}
	
	public int getPriority(){
		return prior;
	}
	
	@Override
	public String toString(){
		return "Energy: " +consumedEnergy +'\n';
	}

}
