package com.minespaceships.mod.menu.functionalMenus.targetMenus;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

import com.minespaceships.mod.menu.FunctionalMenu;
import com.minespaceships.mod.menu.Menu;
import com.minespaceships.mod.menu.functionalMenus.energyMenus.TerminalUtil;
import com.minespaceships.mod.overhead.IMenuInterface;
import com.minespaceships.mod.spaceship.Spaceship;
import com.minespaceships.mod.target.EntityTarget;
import com.minespaceships.mod.target.ITargetHolder;

public class EntityTargetMenu  extends Menu implements FunctionalMenu {
	int target;
	public EntityTargetMenu(){
		super("");
	}
	public EntityTargetMenu(EntityTarget entityTarget,World world) {
		super(entityTarget.getEntity(world).getName());
		this.target = entityTarget.getEntityID();
	}
	@Override
	public String activate(IMenuInterface terminal, String data) {
		Menu mother = this.getMother();
		Spaceship ship = TerminalUtil.getShip(terminal);
		int targetData = Integer.parseInt(data);
		if(ship != null){
			ship.shootPhaserAt(new EntityTarget(terminal.getChatRegisterEntity().getWorld().getEntityByID(targetData)));
		}
		return "";
	}
	@Override
	public String getData() {
		// TODO Auto-generated method stub
		return target+"";
	}

}
