package com.example.examplemod.overhead;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;

public class ChatRegisterEntity extends TileEntity {
	/**
	 * Activates the TileEntity and opens a custom chat to the player
	 * @param player
	 */
	public void Activate(EntityPlayer player){
		//check wether the player is our local player, so one cannot open a console for another player
		//on the server
		if(player.equals(Minecraft.getMinecraft().thePlayer)){		
			//open our console. 
			Minecraft.getMinecraft().displayGuiScreen(new CustomGuiChat(player, this));
		}
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
		//making a very first command to see wether it works.
		if(command.equals("hello")){
			//send something to the player to see wether we get a feedback from our command.
			player.addChatComponentMessage(new ChatComponentText("I love you!"));
		}
	}
}
