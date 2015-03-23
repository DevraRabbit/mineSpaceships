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
	
	public EnergyStrategySystem (SpaceshipAssembler assembler, World world){
		this.assembler = assembler;
		this.world= world;
		refresh();
	}
	
	public float getEnergy(){
		ArrayList<BlockPos> energyPositions = assembler.getParts(IEnergyC.class);
		float energy=0;
		for(BlockPos p: energyPositions){
			Block block = world.getBlockState(p).getBlock();
			if(block instanceof IEnergyC){
				IEnergyC energyBlock=(IEnergyC) world.getBlockState(p).getBlock();
				if(energyBlock.getStatus(p, world)){
					energy+= energyBlock.getEnergy();
				}		
			} else {
				//TODO: implement reload Bug where all blocks are air
			}
		}
		return energy;		
	}
	
	public boolean canBeActivated(IEnergyC energyBlock){
		return getEnergy()+energyBlock.getEnergy()>=0;		
	}
	
	public void refresh(){
		float energy=getEnergy();
		if(energy<0){
			//for(int i=0;)
			//TODO: implement Block shutdown. Important! DO NOT SHUT DOWN OR CALCULATE WITH AIR BLOCKS! (regarding reloading)
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
	

