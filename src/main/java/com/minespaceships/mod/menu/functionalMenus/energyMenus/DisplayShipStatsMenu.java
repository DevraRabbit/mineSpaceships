package com.minespaceships.mod.menu.functionalMenus.energyMenus;

import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

import com.minespaceships.mod.menu.FunctionalMenu;
import com.minespaceships.mod.menu.Menu;
import com.minespaceships.mod.overhead.CustomGuiChat;
import com.minespaceships.mod.overhead.IMenuInterface;
import com.minespaceships.mod.spaceship.ShipInformation;
import com.minespaceships.mod.spaceship.Shipyard;
import com.minespaceships.mod.spaceship.Spaceship;

/**
 * 
 * @author ..., ovae.
 * @version 20150402.
 */
public class DisplayShipStatsMenu extends Menu implements FunctionalMenu {

	public DisplayShipStatsMenu() {
		super("Ship Stats");
	}

	@Override
	public String activate(IMenuInterface terminal, String data) {
		Spaceship ship = TerminalUtil.getShip(terminal);
		if(ship != null){
			float weight = ShipInformation.getShipWeight(ship);
			float speed = ShipInformation.getShipSpeed(ship);
			float shields = ShipInformation.getShipShields(ship);
			float capacity = ship.getCapacity();
			float strength = ShipInformation.getShipStrength(ship);
			int activeGenerators = ship.getActiveGeneratorsCount();
			int activeEngines = ship.getActiveEnginesCount();
			int activeShields = ship.getActiveShieldsCount();
			int activePhaser = ship.getActivePhaserCount();
			
			String out;
			out = "Weight: "+weight +" tons\n"+
					"Capacity: "+capacity+" EU ("+activeGenerators+" active generators)\n"+
					"Speed: "+speed +" EU ("+activeEngines+" active engines)\n"+
					"Shields: "+shields+" SU ("+activeShields+" active shields)\n"+
					"Strength: "+strength+" PU ("+activePhaser+" active phaser)\n";
			terminal.display(out, terminal.getPlayerEntity(), false);
			return out;
		} else {
			return EnumChatFormatting.RED+"No Spaceship\n";
		}
	}

	@Override
	public String getData() {
		return null;
	}

}
