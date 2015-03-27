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

public class DisplayShipStatsMenu extends Menu implements FunctionalMenu {

	public DisplayShipStatsMenu(String name) {
		super(name);
	}

	@Override
	public String activate(String command, IMenuInterface terminal) {
		Spaceship ship = TerminalUtil.getShip(terminal);
		if(ship != null){
			float weight = ShipInformation.getShipWeight(ship);
			float speed = ShipInformation.getShipSpeed(ship);
			float shields = ShipInformation.getShipShields(ship);
			float capacity = ship.getCapacity();
			int activeGenerators = ship.getActiveGeneratorsCount();
			int activeEngines = ship.getActiveEnginesCount();
			int activeShields = ship.getActiveShieldsCount();
			
			return "Weight: "+weight +" tons\n"+
					"Capacity: "+capacity+" EU ("+activeGenerators+" active generators)\n"+
					"Speed: "+speed +" EU ("+activeEngines+" active engines)\n"+
					"Shields: "+shields+" EU ("+activeShields+" active shields)\n";
		} else {
			return EnumChatFormatting.RED+"No Spaceship\n";
		}
	}

}
