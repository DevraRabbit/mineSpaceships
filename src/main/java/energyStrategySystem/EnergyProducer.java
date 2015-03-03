package energyStrategySystem;

public class EnergyProducer {
	private int producedEnergy;
	private boolean status;
	
	public EnergyProducer(int energy){
		
		
		producedEnergy=energy;
	
		
		
	}
	
	public void setStatus(boolean b){
		status=b;
	}
	
	public boolean getStatus(){
		return status;
	}
	
	
	public int getEnergy(){
		return producedEnergy;
	}
	
	@Override
	public String toString(){
		return "Energy: " +producedEnergy +'\n';
	}
	


}
