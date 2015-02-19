package com.minespaceships.mod.overhead;

import java.util.regex.*;

import com.minespaceships.mod.spaceship.Spaceship;
import com.minespaceships.mod.spaceship.SpaceshipCommands;
import com.minespaceships.util.BlockCopier;
import com.minespaceships.util.Calculator;
import com.minespaceships.util.IMoveable;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.Vec3;
import net.minecraft.util.Vec3i;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;

public class ChatRegisterEntity extends TileEntity implements IMoveable{
	
	//Attributes
	private Spaceship ship;
	private WorldServer remoteWorld;	
	public ChatRegisterEntity() {
		remoteWorld = (WorldServer)MinecraftServer.getServer().getEntityWorld();
	}
	/**
	 * Activates the TileEntity and opens a custom chat to the player
	 * @param player
	 */
	public void Activate(EntityPlayer player){
		//check if the player is our local player, so one cannot open a console for another player
		//on the server
		if(player.equals(Minecraft.getMinecraft().thePlayer)){		
			//open our console. 
			Minecraft.getMinecraft().displayGuiScreen(new CustomGuiChat(player, (ChatRegisterEntity)remoteWorld.getTileEntity(pos)));
		}
	}
	public void setRemoteWorld(WorldServer world){
		remoteWorld = world;
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
		
		if(command.equals("hello")){
			//send something to the player to see if we get a feedback from our command.
			player.addChatComponentMessage(new ChatComponentText("I love you!"));
		//Define the 'calc' command, which parses a math expression
		} else if(command.startsWith("calc")) {
			Calculator.calc(command, player);
		} else if (command.startsWith("init")) {
			SpaceshipCommands.init(command, remoteWorld, this, player, ship);
		} else if (command.startsWith("move")) {
			SpaceshipCommands.move(command, remoteWorld, this, player, ship);
		} else if (command.equals("test1")) {
			SpaceshipCommands.init("init -4;-4;-4 to 4;4;4", remoteWorld, this, player, ship);
			SpaceshipCommands.move("move 0;15;0", remoteWorld, this, player, ship);
		} else if(command.equals("status")) {
			SpaceshipCommands.status(remoteWorld, this, player, ship);
		}
	}

	public void setShip(Spaceship ship) {
		this.ship = ship;
	}
	
	public Spaceship getShip() {
		return ship;
	}
	@Override
	public void moveInformation(IMoveable target) {
		if(target instanceof ChatRegisterEntity){
			ChatRegisterEntity targetEntity = (ChatRegisterEntity)target;
			targetEntity.ship = ship;
			targetEntity.remoteWorld = remoteWorld;
		}
	}
}
