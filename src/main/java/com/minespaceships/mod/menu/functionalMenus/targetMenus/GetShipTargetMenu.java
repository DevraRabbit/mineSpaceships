package com.minespaceships.mod.menu.functionalMenus.targetMenus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

import com.minespaceships.mod.menu.FunctionalMenu;
import com.minespaceships.mod.menu.Menu;
import com.minespaceships.mod.menu.functionalMenus.energyMenus.TerminalUtil;
import com.minespaceships.mod.overhead.IMenuInterface;
import com.minespaceships.mod.spaceship.Shipyard;
import com.minespaceships.mod.spaceship.Spaceship;
import com.minespaceships.mod.target.EntityTarget;
import com.minespaceships.mod.target.ITargetHolder;
import com.minespaceships.mod.target.PositionTarget;
import com.minespaceships.mod.target.Target;

public class GetShipTargetMenu extends Menu implements ITargetHolder{

	ITargetHolder targeter;
	public GetShipTargetMenu() {
		super("Target Spaceship");
	}
	public GetShipTargetMenu(ITargetHolder holder){
		super("Target Spaceship");
		targeter = holder;
	}

	@Override
	public Menu getMenu(IMenuInterface terminal) {
		GetShipTargetMenu list = new GetShipTargetMenu(targeter);
		World world = terminal.getChatRegisterEntity().getWorld();
		List<Spaceship> ships = Shipyard.getShipyard(world).getShipList();
		ArrayList<SpaceshipTarget> targets = new ArrayList<SpaceshipTarget>();
		Spaceship ship = Shipyard.getShipyard(world).getShip(terminal.getChatRegisterEntity().getPos(), world);
		for(Spaceship s : ships){
			if(s != ship){
				targets.add(new SpaceshipTarget(s.getOrigin()));
			}
		}
		SpaceshipTarget pos = new SpaceshipTarget(terminal.getChatRegisterEntity().getPos());
		Collections.sort(targets, new Target.distanceComparator(pos, terminal.getChatRegisterEntity().getWorld()));
		for(int i = 0; i < 10 && i < targets.size() &&  !targets.isEmpty(); i++){
			list.addSubMenu(new SpaceshipTargetMenu(targets.get(i), terminal.getChatRegisterEntity().getWorld()));
		}
		return list;
	}

	@Override
	public void onTarget(Target target) {
		if(targeter != null){
			targeter.onTarget(target);
		}
	}
	
	
	
}
