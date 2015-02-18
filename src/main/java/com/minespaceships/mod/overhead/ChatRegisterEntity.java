package com.minespaceships.mod.overhead;

import java.util.regex.*;

import com.minespaceships.mod.spaceship.Spaceship;
import com.minespaceships.mod.spaceship.SpaceshipCommands;
import com.minespaceships.util.BlockCopier;
import com.minespaceships.util.Calculator;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.Vec3;
import net.minecraft.util.Vec3i;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;

public class ChatRegisterEntity extends TileEntity {
	
	//Attributes
	private Spaceship ship;
	private WorldServer remoteWorld;
	private WorldClient clientWorld;
	/**
	 * Activates the TileEntity and opens a custom chat to the player
	 * @param player
	 */
	public void Activate(EntityPlayer player){
		//check if the player is our local player, so one cannot open a console for another player
		//on the server
		if(player.equals(Minecraft.getMinecraft().thePlayer)){		
			//open our console. 
			Minecraft.getMinecraft().displayGuiScreen(new CustomGuiChat(player, this));
		}
	}
	public void setRemoteWorld(WorldServer world){
		remoteWorld = world;
	}
	public void setClientWorld(WorldClient world){
		clientWorld = world;
	}
	/**
	 * Executes the given command, regardless who committed it.
	 * @param command
	 */
	public void onCommand(String command){
		
	}
	/**
	 * Executing the given command considering the player that sent it.
	 * @param command
	 * @param player
	 */
	public void onCommand(String command, EntityPlayer player){
		//define a very first command to see if it works.
		if(command.equals("hello")){
			//send something to the player to see if we get a feedback from our command.
			player.addChatComponentMessage(new ChatComponentText("I love you!"));
		//Define the 'calc' command, which parses a math expression
		} else if(command.startsWith("calc")) {
			Calculator.calc(command, player);
		} else if (command.startsWith("init")) {
			SpaceshipCommands.init(command, clientWorld, remoteWorld, this, player, ship);
		} else if (command.startsWith("move")) {
			SpaceshipCommands.move(command, remoteWorld, this, player, ship);
		} else if (command.equals("test1")) {
			SpaceshipCommands.init("init -5;-5;-5 to 5;5;5", clientWorld, remoteWorld, this, player, ship);
			SpaceshipCommands.move("move 0;20;0", remoteWorld, this, player, ship);
		}
	}

	public void setShip(Spaceship ship) {
		this.ship = ship;
	}
	
	public Spaceship getShip() {
		return ship;
	}
}
