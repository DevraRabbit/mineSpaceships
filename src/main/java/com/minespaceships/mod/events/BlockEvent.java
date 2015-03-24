package com.minespaceships.mod.events;

import java.util.List;

import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.UseHoeEvent;
import net.minecraftforge.event.world.ExplosionEvent;
import net.minecraftforge.event.world.BlockEvent.BreakEvent;
import net.minecraftforge.event.world.BlockEvent.MultiPlaceEvent;
import net.minecraftforge.event.world.BlockEvent.PlaceEvent;
import net.minecraftforge.fml.common.eventhandler.Cancelable;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.eventhandler.Event.HasResult;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.minespaceships.mod.CommandMessage;
import com.minespaceships.mod.MineSpaceships;
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
		sendBlockChange(pos, world, false);
		if(removeBlock(pos, world)){
			if(!world.isRemote){
				event.getPlayer().addChatComponentMessage(new ChatComponentText("Block removed"));
			}
		}
	}
	public static boolean removeBlock(BlockPos pos, World world){
		return Shipyard.getShipyard(world).removeBlock(pos, world);			
	}
	@SideOnly(Side.SERVER)
	public static void sendBlockChange(BlockPos pos, World world, boolean blockPlaced){
		MineSpaceships.blockChangeEvents.sendToAll(new CommandMessage(pos.toLong()+","+world.provider.getDimensionId()+","+blockPlaced));
	}
	
	/**
	 * Calls blockPlaced to add block to ships block list if placed
	 * block is next to a ship
	 */
	@SideOnly(Side.SERVER)
	@SubscribeEvent
	public void onPlaceEvent(final PlaceEvent event) {
		if (event.isCanceled()) return;	
		World world = event.world;
		BlockPos pos = event.pos;
		sendBlockChange(pos, world, true);
		if(placeBlock(pos, world)){
			if(!world.isRemote){
				event.player.addChatComponentMessage(new ChatComponentText("Block added"));
			}
		}
	}
	public static boolean placeBlock(BlockPos pos, World world){
		return Shipyard.getShipyard(world).placeBlock(pos, world);			
	}
	
	/**
	 * Calls blockPlaced to add block to ships block list if placed
	 * block is next to a ship
	 * see http://www.minecraftforge.net/forum/index.php?topic=24376.0
	 */
	@SideOnly(Side.SERVER)
	@SubscribeEvent
	public void onMultiPlaceEvent(final MultiPlaceEvent event) {
		if (event.isCanceled()) return;
		World world = event.world;
		BlockPos pos = event.pos;
		sendBlockChange(pos, world, true);
		if(placeBlock(pos, world)){
			if(!world.isRemote){
				event.player.addChatComponentMessage(new ChatComponentText("Block added"));
			} else {
				event.player.addChatComponentMessage(new ChatComponentText(EnumChatFormatting.DARK_AQUA+"Block added"));
			}
		}
	}
	
	/**
	 * Calls blockBroken for each block affected by explosion,
	 * to remove the blocks from ships block list if member of a ship
	 */
	@SideOnly(Side.SERVER)
	@SubscribeEvent
	public void onExplosionEvent(final ExplosionEvent.Detonate event) {
		if (event.isCanceled()) return;
		World world = event.world;
		List<BlockPos> posList = event.getAffectedBlocks();
		for(BlockPos pos: posList) {
			sendBlockChange(pos, world, false);
			removeBlock(pos, world);
		}
	}
	@SideOnly(Side.SERVER)
	@SubscribeEvent
	public void onUseHoeEvent(final UseHoeEvent event) {
		if (event.isCanceled()) return;
		World world = event.world;
		BlockPos pos = event.pos;
		if (!world.isRemote) {
			Shipyard.getShipyard(event.world).sendBlockInfo(pos,event.entityPlayer, world);
		}
	}
}
