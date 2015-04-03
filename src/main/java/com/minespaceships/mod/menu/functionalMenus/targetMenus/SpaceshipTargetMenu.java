package com.minespaceships.mod.menu.functionalMenus.targetMenus;

import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumChatFormatting;
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
import com.minespaceships.mod.target.SpaceshipTarget;

public class SpaceshipTargetMenu extends Menu implements FunctionalMenu {

	SpaceshipTarget target;

	public SpaceshipTargetMenu(){
		super("placeholder");
	}

	public SpaceshipTargetMenu(SpaceshipTarget target, World world) {
		super(target.getTarget(world)+"");
		this.target = target;
	}

	@Override
	public String activate(IMenuInterface terminal, String data) {
		Menu mother = this.getMother();
		World world = terminal.getChatRegisterEntity().getWorld();
		BlockPos basePos = BlockPos.fromLong(Long.parseLong(data));
		Spaceship targetShip = Shipyard.getShipyard(world).getShipByBlockMapOrigin(basePos, world);
		Spaceship ship = TerminalUtil.getShip(terminal);
		if(ship != null && targetShip !=null){
			ship.shootPhaserAt(new SpaceshipTarget(basePos, targetShip));
			terminal.display("shoot at" +basePos, terminal.getPlayerEntity(), false);
			return "";
		}
		terminal.display("miss", terminal.getPlayerEntity(), false);
		return "";
	}

	@Override
	public String getData() {
		return target.getBasePos().toLong()+"";
	}
}
