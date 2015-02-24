package com.minespaceships.mod.events;

import net.minecraftforge.event.world.BlockEvent.BreakEvent;
import net.minecraftforge.event.world.BlockEvent.HarvestDropsEvent;

// http://www.minecraftforge.net/wiki/Event_Reference#BlockEvent
public class BlockEvent {
	// http://www.minecraftforge.net/wiki/Event_Reference#BlockEvent

	// HarvestDropsEvent
	// http://www.minecraftforge.net/wiki/Event_Reference#HarvestDropsEvent
	@ForgeSubscribe
	public void onHarvestDropsEvent(HarvestDropsEvent event) {
		
	}
	
	// BreakEvent
	// http://www.minecraftforge.net/wiki/Event_Reference#BreakEvent
	@ForgeSubscribe
	public void onBreakEvent(BreakEvent event) {
		
	}
}
