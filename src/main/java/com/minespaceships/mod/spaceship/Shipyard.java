package com.minespaceships.mod.spaceship;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;
import java.util.Vector;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;
import net.minecraft.world.WorldSavedData;
import net.minecraft.world.storage.MapStorage;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.event.*;

import com.minespaceships.mod.CommandMessage;
import com.minespaceships.mod.MineSpaceships;
import com.minespaceships.mod.overhead.ChatRegisterEntity;

public class Shipyard {
	public static final String SPACESHIP_TAG = "//Spaceship";
	public static final String COMPOUND_KEY_BASE = "Shipyard";
	
	private Vector<Spaceship> ships;
	private World world;
	
	public enum BlockChangeStatus{
		NO_CHANGE, CHANGE, SHIP_REMOVED;
	}
	
	protected Shipyard(World world) {
		this.world = world;
		ships = new Vector<Spaceship>();
//		MapStorage storage = world.getPerWorldStorage();
//		saveCompound = (ShipyardSaveCompound) storage.loadData(ShipyardSaveCompound.class, COMPOUND_KEY);
//		if(saveCompound == null){
//			saveCompound = new ShipyardSaveCompound(COMPOUND_KEY);
//		}
//		saveCompound.setShipyard(this);
//		storage.setData(COMPOUND_KEY, saveCompound);
	}
	public String getCompoundKey(){
		return getCompoundKey(world.provider.getDimensionId());
	}
	public static String getCompoundKey(int dimension){
		return COMPOUND_KEY_BASE+dimension;
	}
	
	public World getWorld(){
		return world;
	}
	
	public static Shipyard getShipyard(World world){
		return AllShipyards.getShipyard(world);
	}
	
	public int getShipCount(){
		return ships.size();
	}
	
	public void update(){
		for(Spaceship ship : ships){
			ship.update();
		}
	}
	
	public void addShip(Spaceship ship){
		if(ship != null){
			if(!ships.contains(ship) && ship.getNavigatorCount() > 0){
				Iterator<Spaceship> shipIt = ships.iterator();
				while(shipIt.hasNext()){
					Spaceship nextShip = shipIt.next();
					if(nextShip.measuresEquals(ship)){
						shipIt.remove();
						System.out.println("Removed ship due to ship overloading");
					}					
				}
				ships.add(ship);
				System.out.println("Added ship to the Shipyard");
			}
		}
	}
	public void removeShip(Spaceship ship){
		ships.remove(ship);
	}
	
	public Spaceship getShip(BlockPos pos, World world){
		for(Spaceship ship : ships){
			if(ship.containsBlock(pos) && 
					ship.getWorld().provider.getDimensionId() == world.provider.getDimensionId()){
				return ship;
			}
		}
		return null;
	}	
	
	@Deprecated
	public void createShip(BlockPos minSpan, final BlockPos origin, final BlockPos maxSpan, World worldS){
		addShip(new Spaceship(minSpan, origin, maxSpan, worldS));
	}
	public void createShip(BlockPos initial, World worldS) throws Exception{
		addShip(new Spaceship(initial, worldS));
	}
	
	/**
	 * Called to handle actions on block broken
	 * @param pos BlockPos
	 * @param world World
	 */
	public BlockChangeStatus removeBlock(final BlockPos pos, final World world) {
		boolean hasRemoved = false;
		boolean removedShip = false;
		for (Iterator<Spaceship> it = ships.iterator(); it.hasNext();) {
			Spaceship ship = it.next();
			if (ship.getWorld() == world) {
				if (ship.containsBlock(pos)) {
					if(ship.removeBlock(pos)){
						it.remove();
						removedShip = true;
					}
					hasRemoved = true;
				}
			}
		}
		return hasRemoved ? (removedShip ? BlockChangeStatus.SHIP_REMOVED : BlockChangeStatus.CHANGE) : BlockChangeStatus.NO_CHANGE;
	}
	
	/**
	 * Called to handle actions on block placed
	 * @param pos BlockPos
	 * @param world World
	 */
	public BlockChangeStatus placeBlock(final BlockPos pos, final World world) {
		for (Spaceship ship: ships) {
			if (ship.getWorld() == world) {
				if (ship.isNeighboringBlock(pos)) {
					ship.addBlock(pos);
					return BlockChangeStatus.CHANGE;
				}
			}
		}	
		return BlockChangeStatus.NO_CHANGE;
	}
	
	/**
	 * Return debugging information to player
	 * @param pos BlockPos
	 * @param world World
	 */
	public void sendBlockInfo(final BlockPos pos, final EntityPlayer player, final World world) {
		if (ships.isEmpty()) {
			player.addChatComponentMessage(new ChatComponentText("false - no ships existing"));
			return;
		}
		
		for (Spaceship ship: ships) {
			if (ship.containsBlock(pos)) {
				player.addChatComponentMessage(new ChatComponentText("block part of \""+ship.toString()+"\""));
				return;
			}
		}
		
		player.addChatComponentMessage(new ChatComponentText("block not part of a ship"));
	}
	
	public void loadShips(HashMap<NBTTagCompound, String> compounds){
		Set<NBTTagCompound> keys = compounds.keySet();
		for(NBTTagCompound c : keys){
			loadShip(c, compounds.get(c));
		}
	}
	public void loadShip(NBTTagCompound compound, String key){
		try {
			addShip(new Spaceship(compound, key, world));
		} catch (Exception e) {
			System.out.println("Could not initialize ship!");
		}
	}
	
	public void clear(){
		ships.clear();
	}

//	public void readFromNBT(NBTTagCompound nbt) {
//		System.out.println("Loading Shipyard on World "+world.getWorldInfo().getWorldName());
//		String ships = nbt.getString(getCompoundKey(world.provider.getDimensionId()));
//		System.out.println("Loading ship :"+ ships.substring(0, 10) + "...");
//		loadShips(ships);
//	}
//
//	public void writeToNBT(NBTTagCompound nbt) {
//		String safe = safe();		
//		System.out.println("Saving "+safe.substring(0, 10) + "..."+" in Shipyard on World "+world.getWorldInfo().getWorldName());
//		nbt.setString(getCompoundKey(world.provider.getDimensionId()), safe);
//	}
}
