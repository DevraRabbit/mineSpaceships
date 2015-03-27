package energyStrategySystem;

import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

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

	@Override
	public boolean getStatus(BlockPos pos, World world) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setStatus(boolean b, BlockPos pos, World world,
			boolean sendChange) {
		// TODO Auto-generated method stub
		
	}

	

}
