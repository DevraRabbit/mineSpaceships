package com.minespaceships.mod.events;

import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.world.BlockEvent.BreakEvent;
import net.minecraftforge.event.world.BlockEvent.MultiPlaceEvent;
import net.minecraftforge.event.world.BlockEvent.PlaceEvent;
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
	public void onBreakEvent(final BreakEvent event) {
		if (event.isCanceled()) return;
		World world = event.world;
		BlockPos pos = event.pos;
		
		Shipyard.getShipyard().blockBroken(pos, world);
	}
	
	@SubscribeEvent
	public void onPlaceEvent(final PlaceEvent event) {
		if (event.isCanceled()) return;	
		World world = event.world;
		BlockPos pos = event.pos;
		
		Shipyard.getShipyard().blockPlaced(pos, world);
	}
	
	// see http://www.minecraftforge.net/forum/index.php?topic=24376.0
	@SubscribeEvent
	public void onMultiPlaceEvent(final MultiPlaceEvent event) {
		if (event.isCanceled()) return;
		World world = event.world;
		BlockPos pos = event.pos;

		Shipyard.getShipyard().blockPlaced(pos, world);
	}
}
