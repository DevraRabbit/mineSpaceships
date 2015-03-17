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
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.event.*;

import com.minespaceships.mod.MineSpaceships;
import com.minespaceships.mod.overhead.ChatRegisterEntity;

public class Shipyard {
	public static final String SPACESHIP_TAG = "//Spaceship";
	
	private Vector<Spaceship> ships;
	private static Shipyard singleton;
	
	protected Shipyard() {
		ships = new Vector<Spaceship>();
	}
	
	public static Shipyard getShipyard(){
		if(singleton == null){
			singleton = new Shipyard();
		}
		return singleton;
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
			}
		}
	}
	public void removeShip(Spaceship ship){
		ships.remove(ship);
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
	public void blockBroken(final BlockPos pos, final World world) {
		for (Iterator<Spaceship> it = ships.iterator(); it.hasNext();) {
			Spaceship ship = it.next();
			if (ship.getWorld() == world) {
				if (ship.containsBlock(pos)) {
					if(ship.removeBlock(pos)){it.remove();}
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
					Minecraft.getMinecraft().thePlayer.sendChatMessage("Block is added");
					ship.addBlock(pos);
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
	public void getBlockInfo(final BlockPos pos, final World world) {
		if (ships.isEmpty()) {
			Minecraft.getMinecraft().thePlayer.sendChatMessage("false - no ships existing");
			return;
		}
		
		for (Spaceship ship: ships) {
			if (ship.containsBlock(pos)) {
				Minecraft.getMinecraft().thePlayer.sendChatMessage("block part of \""+ship.toString()+"\"");
				return;
			}
		}
		
		Minecraft.getMinecraft().thePlayer.sendChatMessage("block not part of a ship");
	}
	
	
	@SubscribeEvent
	@SideOnly(Side.SERVER)
	public void safe(WorldEvent.Save event){
		NBTTagCompound compound = event.world.getWorldInfo().getNBTTagCompound();
		String shipsString = "";		
		for(Spaceship ship : ships){	
			if(ship.getWorld().provider.getDimensionId() == event.world.provider.getDimensionId()){
				shipsString+= ship.toData();
				shipsString+= SPACESHIP_TAG+"\n";
			}
		}
		compound.setString(SPACESHIP_TAG, shipsString);
	}
	@SubscribeEvent
	//@SideOnly(Side.SERVER)
	public void load(WorldEvent.Load event){
		ships.clear();		
		NBTTagCompound compound = event.world.getWorldInfo().getNBTTagCompound();
		String shipsString = compound.getString(SPACESHIP_TAG);
		Scanner scanner = null;
		try {
			scanner = new Scanner(shipsString);
		} catch (Exception e) {
			e.printStackTrace();
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
						addShip(new Spaceship(ship, event.world));
					} catch (Exception e){
						System.out.println("Could not initialize Ship");
					}
					ship = "";
				}
			}
			scanner.close();
		}		
	}
}
