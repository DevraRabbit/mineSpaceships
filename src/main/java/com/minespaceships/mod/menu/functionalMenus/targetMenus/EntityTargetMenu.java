package com.minespaceships.mod.menu.functionalMenus.targetMenus;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

import com.minespaceships.mod.menu.FunctionalMenu;
import com.minespaceships.mod.menu.Menu;
import com.minespaceships.mod.overhead.IMenuInterface;
import com.minespaceships.mod.target.EntityTarget;
import com.minespaceships.mod.target.ITargetHolder;

public class EntityTargetMenu  extends Menu implements FunctionalMenu {
	EntityTarget target;
	public EntityTargetMenu(EntityTarget entityTarget,World world) {
		super(entityTarget.getEntity(world).getName());
		this.target = entityTarget;
	}
	@Override
	public String activate(String command, IMenuInterface terminal) {
		Menu mother = this.getMother();
		if(mother instanceof ITargetHolder){
			((ITargetHolder)mother).onTarget(target);
			return EnumChatFormatting.RED+"Locked "+target.getEntity(terminal.getChatRegisterEntity().getWorld()).getName()+".";
		}
		return "";
	}

}
