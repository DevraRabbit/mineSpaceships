package com.minespaceships.mod.spaceship;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Vector;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
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
	private String COMPOUND_KEY = "";
	
	private ShipyardSaveCompound saveCompound;
	
	private Vector<Spaceship> ships;
	private World world;
	
	protected Shipyard(World world) {
		COMPOUND_KEY = COMPOUND_KEY_BASE;// + world.provider.getDimensionId();
		this.world = world;
		ships = new Vector<Spaceship>();
		MapStorage storage = world.getPerWorldStorage();
		saveCompound = (ShipyardSaveCompound) storage.loadData(ShipyardSaveCompound.class, COMPOUND_KEY);
		if(saveCompound == null){
			saveCompound = new ShipyardSaveCompound(COMPOUND_KEY);
		}
		storage.setData(COMPOUND_KEY, saveCompound);
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
	
	public void addShip(Spaceship ship){
		if(ship != null){
			if(!ships.contains(ship) && ship.getNavigatorCount() > 0){
				Iterator<Spaceship> shipIt = ships.iterator();
				while(shipIt.hasNext()){
					Spaceship nextShip = shipIt.next();
					if(nextShip.measuresEquals(ship)){
						shipIt.remove();
					}
				}
				ships.add(ship);
				saveCompound.markDirty();
			}
		}
	}
	public void removeShip(Spaceship ship){
		ships.remove(ship);
		saveCompound.markDirty();
	}
	
	public Spaceship getShip(BlockPos pos, World world){
		for(Spaceship ship : ships){
			if(ship.containsBlock(pos) && ship.getWorld() == world){
				return ship;
			}
		}
		return null;
	}	
	
	@Deprecated
	public void createShip(BlockPos minSpan, final BlockPos origin, final BlockPos maxSpan, World worldS){
		new Spaceship(minSpan, origin, maxSpan, worldS);
	}
	public void createShip(BlockPos initial, World worldS) throws Exception{
		new Spaceship(initial, worldS);
	}
	
	/**
	 * Called to handle actions on block broken
	 * @param pos BlockPos
	 * @param world World
	 */
	public void blockRemoved(final BlockPos pos, final World world) {
		for (Iterator<Spaceship> it = ships.iterator(); it.hasNext();) {
			Spaceship ship = it.next();
			if (ship.getWorld() == world) {
				if (ship.containsBlock(pos)) {
					if(ship.removeBlock(pos)){it.remove();}
					saveCompound.markDirty();
				}
			}
		}
	}
	
	/**
	 * Called to handle actions on block placed
	 * @param pos BlockPos
	 * @param world World
	 */
	public void blockPlaced(final BlockPos pos, final World world) {
		if (ships.isEmpty()) return;
		
		for (Spaceship ship: ships) {
			if (ship.getWorld() == world) {
				if (ship.isNeighboringBlock(pos)) {
					ship.addBlock(pos);
					saveCompound.markDirty();
					break;
				}
			}
		}		
	}
	
	/**
	 * Return debugging information to player
	 * @param pos BlockPos
	 * @param world World
	 */
	public void getBlockInfo(final BlockPos pos, final EntityPlayer player, final World world) {
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
	
	public String safe(){
		String shipString = "";
		for(Spaceship ship : ships){				
			shipString += ship.toData();
			shipString += SPACESHIP_TAG+"\n";
		}
		return shipString;
	}
	
	public void load(String shipString){
		ships.clear();
		Scanner scanner = null;
		try {
			scanner = new Scanner(shipString);
		} catch (Exception e) {
			return;
		}
		if(scanner != null){
			String ship = "";
			while(scanner.hasNext()){
				String line = scanner.next();
				if(!line.equals(SPACESHIP_TAG)){
					ship += line + "\n";
				} else {
					try{
						addShip(new Spaceship(ship, world));
					} catch (Exception e){
						System.out.println("Could not initialize Ship");
					}
					ship = "";
				}
			}
			scanner.close();
		}	
	}
	
	public void readFromNBT(NBTTagCompound nbt) {
		Shipyard syard = Shipyard.getShipyard(world);
		System.out.println("Loading Shipyard on World "+world.getWorldInfo().getWorldName());
		String ships = nbt.getString(COMPOUND_KEY);
		load(ships);
	}

	public void writeToNBT(NBTTagCompound nbt) {
		System.out.println("Saving Shipyard on World "+world.getWorldInfo().getWorldName());
		nbt.setString(COMPOUND_KEY, safe());
		saveCompound.setDirty(false);
	}
}
