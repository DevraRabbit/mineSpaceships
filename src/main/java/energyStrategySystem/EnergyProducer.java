package energyStrategySystem;

public class EnergyProducer {
	private int producedEnergy;
	
	public EnergyProducer(int energy){
		
		
		producedEnergy=energy;
	
		
		
	}
	
	public int getEnergy(){
		return producedEnergy;
	}
	
	@Override
	public String toString(){
		return "Energy: " +producedEnergy +'\n';
	}
	


}
