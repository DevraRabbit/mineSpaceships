package com.minespaceships.mod.events;

import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.world.BlockEvent.BreakEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import com.minespaceships.mod.spaceship.Shipyard;

// http://www.minecraftforge.net/wiki/Event_Reference#BlockEvent
public class BlockEvent {
	// http://www.minecraftforge.net/wiki/Event_Reference#BlockEvent

	// HarvestDropsEvent
	// http://www.minecraftforge.net/wiki/Event_Reference#HarvestDropsEvent
	//@SubscribeEvent
	//public void onHarvestDropsEvent(HarvestDropsEvent event) {
	//	
	//}
	
	// BreakEvent
	// http://www.minecraftforge.net/wiki/Event_Reference#BreakEvent
	@SubscribeEvent
	public void onBreakEvent(BreakEvent event) {
		World world = event.world;
		BlockPos pos = event.pos;
		
		Shipyard.getShipyard().blockBroken(pos, world);
	}
}
