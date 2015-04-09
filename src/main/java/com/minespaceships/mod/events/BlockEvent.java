package com.minespaceships.mod.events;

import java.util.List;

import org.apache.logging.log4j.core.filter.BurstFilter;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemCoal;
import net.minecraft.item.ItemGlassBottle;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.UseHoeEvent;
import net.minecraftforge.event.world.BlockEvent.HarvestDropsEvent;
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
import com.minespaceships.mod.spaceship.Shipyard.BlockChangeStatus;

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
		if(removeBlock(pos, world) == BlockChangeStatus.CHANGE){
			event.getPlayer().addChatComponentMessage(new ChatComponentText("Block removed"));
		} else if (removeBlock(pos, world) == BlockChangeStatus.SHIP_REMOVED){
			event.getPlayer().addChatComponentMessage(new ChatComponentText("Ship removed"));
		}
	}
	@SubscribeEvent
	public void onHarvestEvent(HarvestDropsEvent event){
		Block b = event.state.getBlock();
		if(b == Blocks.coal_block){
			event.drops.add(new ItemStack(Items.gunpowder, 10));
		} else if(b == Blocks.coal_ore){
			event.drops.add(new ItemStack(Items.coal, 10));
		}
	}
	public static BlockChangeStatus removeBlock(BlockPos pos, World world){
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
		if(placeBlock(pos, world) == BlockChangeStatus.CHANGE){
			event.player.addChatComponentMessage(new ChatComponentText("Block added"));
		}
	}
	public static BlockChangeStatus placeBlock(BlockPos pos, World world){
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
		if(placeBlock(pos, world) == BlockChangeStatus.CHANGE){
			event.player.addChatComponentMessage(new ChatComponentText("Block added"));
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
