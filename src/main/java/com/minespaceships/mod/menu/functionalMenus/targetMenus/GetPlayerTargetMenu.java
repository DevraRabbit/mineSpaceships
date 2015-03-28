package com.minespaceships.mod.menu.functionalMenus.targetMenus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.player.EntityPlayer;

import com.minespaceships.mod.menu.FunctionalMenu;
import com.minespaceships.mod.menu.Menu;
import com.minespaceships.mod.menu.functionalMenus.energyMenus.TerminalUtil;
import com.minespaceships.mod.overhead.IMenuInterface;
import com.minespaceships.mod.target.EntityTarget;
import com.minespaceships.mod.target.ITargetHolder;
import com.minespaceships.mod.target.PositionTarget;
import com.minespaceships.mod.target.Target;

public class GetPlayerTargetMenu extends Menu implements ITargetHolder{

	ITargetHolder targeter;
	public GetPlayerTargetMenu() {
		super("Target Player");
	}
	public GetPlayerTargetMenu(ITargetHolder holder){
		super("Target Player");
		targeter = holder;
	}

	@Override
	public Menu getMenu(IMenuInterface terminal) {
		GetPlayerTargetMenu list = new GetPlayerTargetMenu(targeter);
		List<EntityPlayer> players = terminal.getChatRegisterEntity().getWorld().getEntities(EntityPlayer.class, IEntitySelector.NOT_SPECTATING);
		ArrayList<EntityTarget> targets = new ArrayList<EntityTarget>();
		for(EntityPlayer e : players){
			targets.add(new EntityTarget(e));
		}
		PositionTarget pos = new PositionTarget(terminal.getChatRegisterEntity().getPos());
		Collections.sort(targets, new Target.distanceComparator(pos, terminal.getChatRegisterEntity().getWorld()));
		for(int i = 0; i < 10 && i < targets.size() &&  !targets.isEmpty(); i++){
			list.addSubMenu(new EntityTargetMenu(targets.get(i), terminal.getChatRegisterEntity().getWorld()));
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
