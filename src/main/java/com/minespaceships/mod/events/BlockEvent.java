package com.minespaceships.mod.events;

import java.util.List;

import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.UseHoeEvent;
import net.minecraftforge.event.world.ExplosionEvent;
import net.minecraftforge.event.world.BlockEvent.BreakEvent;
import net.minecraftforge.event.world.BlockEvent.MultiPlaceEvent;
import net.minecraftforge.event.world.BlockEvent.PlaceEvent;
import net.minecraftforge.fml.common.eventhandler.Cancelable;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.eventhandler.Event.HasResult;

import com.minespaceships.mod.overhead.SpaceshipCommandEvent;
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
	// Minecraft.getMinecraft().player.sendMessage
	// BreakEvent
	// http://www.minecraftforge.net/wiki/Event_Reference#BreakEvent
	
	/**
	 * Calls blockBroken for placed block,
	 * to remove the block from ships block list if member of a ship
	 */
	@SubscribeEvent
	public void onBreakEvent(final BreakEvent event) {
		if (event.isCanceled()) return;
		World world = event.world;
		BlockPos pos = event.pos;
		
		Shipyard.getShipyard().blockBroken(pos, world);
	}
	
	/**
	 * Calls blockPlaced to add block to ships block list if placed
	 * block is next to a ship
	 */
	@SubscribeEvent
	public void onPlaceEvent(final PlaceEvent event) {
		if (event.isCanceled()) return;	
		World world = event.world;
		BlockPos pos = event.pos;
		
		Shipyard.getShipyard().blockPlaced(pos, world);
	}
	
	/**
	 * Calls blockPlaced to add block to ships block list if placed
	 * block is next to a ship
	 * see http://www.minecraftforge.net/forum/index.php?topic=24376.0
	 */
	@SubscribeEvent
	public void onMultiPlaceEvent(final MultiPlaceEvent event) {
		if (event.isCanceled()) return;
		World world = event.world;
		BlockPos pos = event.pos;

		Shipyard.getShipyard().blockPlaced(pos, world);
	}
	
	/**
	 * Calls blockBroken for each block affected by explosion,
	 * to remove the blocks from ships block list if member of a ship
	 */
	@SubscribeEvent
	public void onExplosionEvent(final ExplosionEvent.Detonate event) {
		if (event.isCanceled()) return;
		World world = event.world;
		List<BlockPos> posList = event.getAffectedBlocks();
		for(BlockPos pos: posList) {
			Shipyard.getShipyard().blockBroken(pos, world);
		}
	}
	
	@SubscribeEvent
	public void onUseHoeEvent(final UseHoeEvent event) {
		if (event.isCanceled()) return;
		World world = event.world;
		BlockPos pos = event.pos;
		if (world.isRemote) {
			Shipyard.getShipyard().getBlockInfo(pos, world);
		}
	}
	
	@SubscribeEvent
	public void onSpaceshipCommand(SpaceshipCommandEvent event){
		System.out.println(event.command);
	}
}
