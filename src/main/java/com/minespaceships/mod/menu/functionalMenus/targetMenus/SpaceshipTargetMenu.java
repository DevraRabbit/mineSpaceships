package com.minespaceships.mod.menu.functionalMenus.targetMenus;

import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

import com.minespaceships.mod.menu.FunctionalMenu;
import com.minespaceships.mod.menu.Menu;
import com.minespaceships.mod.overhead.IMenuInterface;
import com.minespaceships.mod.target.EntityTarget;
import com.minespaceships.mod.target.ITargetHolder;
import com.minespaceships.mod.target.PositionTarget;

public class SpaceshipTargetMenu extends Menu implements FunctionalMenu {
	SpaceshipTarget target;
	public SpaceshipTargetMenu(SpaceshipTarget target, World world) {
		super(target.getTarget(world)+"");
		this.target = target;
	}
	@Override
	public String activate(String command, IMenuInterface terminal) {
		Menu mother = this.getMother();
		if(mother instanceof ITargetHolder){
			((ITargetHolder)mother).onTarget(target);
			return EnumChatFormatting.RED+"Locked "+target.getTarget(terminal.getChatRegisterEntity().getWorld())+".";
		}
		return "";
	}
}
