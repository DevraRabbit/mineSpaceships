package energyStrategySystem;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;

import net.minecraft.block.Block;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

import com.minespaceships.mod.blocks.EnergyBlock;
import com.minespaceships.mod.blocks.EngineBlock;
import com.minespaceships.mod.blocks.PhaserBlock;
import com.minespaceships.mod.blocks.ShieldBlock;
import com.minespaceships.mod.menu.Menu;
import com.minespaceships.mod.spaceship.SpaceshipAssembler;

public class EnergyStrategySystem {
	private SpaceshipAssembler assembler;
	private World world;
	private ArrayList<ArrayList<Class>> priorityList;
	private boolean hasRefreshed = false;
	
	public EnergyStrategySystem (SpaceshipAssembler assembler, World world){
		this.assembler = assembler;
		this.world= world;
	}
	
	public float getEnergy(){
		return getEnergy(true);
	}
	
	public float getEnergy(boolean mindStatus){
		if(!hasRefreshed) {
			hasRefreshed = true;
			refresh();		
		}
		ArrayList<BlockPos> energyPositions = assembler.getParts(IEnergyC.class);
		//System.out.println("Recieved "+energyPositions.size()+" IEnergyC. "+ (energyPositions.size() > 0 ? energyPositions.get(0) : "No Positions found!"));
		float energy=0;
		for(BlockPos p: energyPositions){
			Block block = world.getBlockState(p).getBlock();
			if(block instanceof IEnergyC){
				IEnergyC energyBlock=(IEnergyC) world.getBlockState(p).getBlock();
				//unly ignore status when it is a producer as we want to know how much energy can be produced
				if(energyBlock.getStatus(p, world) || (!mindStatus && energyBlock.getEnergy() > 0)){
					energy+= energyBlock.getEnergy();
				}		
			} else {
				//TODO: implement reload Bug where all blocks are air
			}
		}
		//System.out.println("Calculated "+energy+" Energy");
		return energy;		
	}
	public boolean canBeActivated(IEnergyC energyBlock){
		//don't mind wether the energygenerators are on
		return getEnergy(false)+energyBlock.getEnergy()>=0;		
	}
	public void refresh(){
		refresh(false);
	}
	
	public void refresh(boolean autoactivate){
		float energy=getEnergy();
		if(energy != 0){
			ArrayList<BlockPos> energyPositions = assembler.getParts(IEnergyC.class);
			//activate producers
			for(BlockPos p: energyPositions){
				Block block = world.getBlockState(p).getBlock();
				if(block instanceof IEnergyC){
					IEnergyC energyBlock=(IEnergyC) world.getBlockState(p).getBlock();
					if(energyBlock.getEnergy() > 0){
						float nextEnergy = energyBlock.getStatus(p, world) ? energy - energyBlock.getEnergy() : energy + energyBlock.getEnergy();					
						if((nextEnergy > energy) && autoactivate){
							energyBlock.setStatus(!energyBlock.getStatus(p, world), p, world, false);
							energy = nextEnergy;
						}
					}
				} else {
					//TODO: implement reload Bug where all blocks are air
				}
			}
			// deactivate/activate consumers
			for(BlockPos p: energyPositions){
				Block block = world.getBlockState(p).getBlock();
				if(block instanceof IEnergyC){
					IEnergyC energyBlock=(IEnergyC) world.getBlockState(p).getBlock();
					if(energyBlock.getEnergy() < 0){
						boolean nextStatus = !energyBlock.getStatus(p, world);
						float nextEnergy = nextStatus ? energy + energyBlock.getEnergy() : energy - energyBlock.getEnergy();					
						if((Math.abs(nextEnergy) < Math.abs(energy))){
							if(autoactivate || !autoactivate && nextStatus == false){
								energyBlock.setStatus(nextStatus, p, world, false);
								energy = nextEnergy;
								System.out.println("Activated "+energyBlock.getClass().getName()+" to "+energy+" Energy");
							}
						}
					}
				} else {
					//TODO: implement reload Bug where all blocks are air
				}
			}
		}
	}
	
	public void setAggressiveFocus(){
		priorityList.clear();
		
		ArrayList<Class> firstPriority = new ArrayList<Class>();
		firstPriority.add(ShieldBlock.class);
		firstPriority.add(PhaserBlock.class);
		
		ArrayList<Class> secondPriority = new ArrayList<Class>();
		secondPriority.add(EngineBlock.class);
		
		ArrayList<Class> thirdPriority = new ArrayList<Class>();
		thirdPriority.add(EngineBlock.class);

		priorityList.add(firstPriority);
		priorityList.add(secondPriority);
		priorityList.add(thirdPriority);
		
	}
	
	private ArrayList<ArrayList<BlockPos>> createPriorityList (ArrayList<BlockPos> blockPosList){
		ArrayList<ArrayList<BlockPos>> posPriorityList = new ArrayList<ArrayList<BlockPos>>();
		for (ArrayList<Class> a: priorityList){
			posPriorityList.add(new ArrayList<BlockPos>());			
		}
	
		for(BlockPos p: blockPosList){
			Block block = world.getBlockState(p).getBlock();
			if(block instanceof IEnergyC){
				IEnergyC energyBlock=(IEnergyC) world.getBlockState(p).getBlock();
				for(ArrayList<Class> b: priorityList){
					for(Class c: b){
						if(c.isAssignableFrom(energyBlock.getClass())){
							//TODO: implement addition here
						}
					}
				}
			}
			
		}
		return posPriorityList;		
	}
	
}
	

