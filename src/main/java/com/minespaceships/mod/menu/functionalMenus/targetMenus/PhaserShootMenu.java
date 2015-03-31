package com.minespaceships.mod.menu.functionalMenus.targetMenus;

import net.minecraft.block.ITileEntityProvider;

import com.minespaceships.mod.menu.FunctionalMenu;
import com.minespaceships.mod.menu.Menu;
import com.minespaceships.mod.menu.functionalMenus.energyMenus.TerminalUtil;
import com.minespaceships.mod.overhead.IMenuInterface;
import com.minespaceships.mod.spaceship.Spaceship;
import com.minespaceships.mod.target.ITargetHolder;
import com.minespaceships.mod.target.Target;

public class PhaserShootMenu extends Menu {
	
	public PhaserShootMenu() {
		super("Shoot Phaser");
	}
	public PhaserShootMenu(Spaceship ship) {
		super("Shoot Phaser");
		if(ship != null){
			addSubMenu(new GetPlayerTargetMenu(new TargetHolder(ship)));
			addSubMenu(new GetShipTargetMenu(new TargetHolder(ship)));
		}
	}
	@Override
	public Menu getMenu(IMenuInterface terminal){
		return new PhaserShootMenu(TerminalUtil.getShip(terminal));		
	}

	public static class TargetHolder implements ITargetHolder{
		private Spaceship ship;
		public TargetHolder(Spaceship ship){
			this.ship = ship;
		}
		@Override
		public void onTarget(Target target) {
			ship.shootPhaserAt(target);
		}
		
	}
	
}
