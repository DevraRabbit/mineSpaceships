package energyStrategySystem;

import com.minespaceships.mod.spaceship.ISpaceshipPart;

import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public interface IEnergyC extends ISpaceshipPart {
	
	
	/**
	 * 
	 * @return Energy
	 */
	int getEnergy();
	
	/**
	 * 
	 * @return Status, false wenn ausgeschaltet.
	 */
	boolean getStatus(BlockPos pos, World world);
	
	void setStatus(boolean b, BlockPos pos, World world);

}
