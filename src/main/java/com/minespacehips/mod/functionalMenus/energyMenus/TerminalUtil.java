package com.minespacehips.mod.functionalMenus.energyMenus;

import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

import com.minespaceships.mod.overhead.CustomGuiChat;
import com.minespaceships.mod.spaceship.Shipyard;
import com.minespaceships.mod.spaceship.Spaceship;

public class TerminalUtil {
	public static Spaceship getShip(CustomGuiChat terminal){
		World world = terminal.getChatRegisterEntity().getWorld();
		BlockPos pos = terminal.getChatRegisterEntity().getPos();
		return Shipyard.getShipyard(world).getShip(pos, world);
	}
}
