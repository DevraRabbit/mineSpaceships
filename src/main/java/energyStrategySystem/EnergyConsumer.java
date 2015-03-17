package energyStrategySystem;

public class EnergyConsumer implements IEnergyC {
	private int consumedEnergy;
	private int prior;
	private boolean status;
	
	public EnergyConsumer(int energy, int priority){
		if(priority<1||priority>3){
			throw new IllegalArgumentException ("priority must be 1, 2 or 3");
		}
		prior= priority;
		consumedEnergy=energy;
		//status=false;
	}
	
	public void setStatus(boolean b){
		status=b;
	}
	
	public boolean getStatus(){
		return status;
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
