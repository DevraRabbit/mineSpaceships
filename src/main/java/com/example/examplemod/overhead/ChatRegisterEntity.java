package com.example.examplemod.overhead;

import net.minecraft.block.Block;
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
		//check if the player is our local player, so one cannot open a console for another player
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
		
		//define a very first command to see if it works.
		if(command.equals("hello")){
			//send something to the player to see if we get a feedback from our command.
			player.addChatComponentMessage(new ChatComponentText("I love you!"));
		}
		/*
		if(command.equals("what are you?")){
			player.addChatComponentMessage(new ChatComponentText("A cube."));
		}
		if(command.equals("rm Ex")){
			player.addExperience(1000);
		}
		if(command.equals("add Ex")){
			player.addExperience(1000);
		}
		if(command.equals("jump")){
			player.jump();
		}
		if(command.equals("info")){
			player.addChatComponentMessage(new ChatComponentText(player.toString()));
		}
		if(command.equals("cmds") || command.equals("")){
			player.addChatComponentMessage(new ChatComponentText("Menu:\n"));
			player.addChatComponentMessage(new ChatComponentText("Commands:{\n\t rm Ex\n\t add Ex\n\t jump\n\t info\n\t cmds\n}"));
		}
		
		if(command.equals("cmd")){
			player.addChatComponentMessage(new ChatComponentText(clear()));
			submenuCmd(player);
		}
		*/
		
		if(command.equals("Menu") || command.equals("menu") ||
		   command.equals("") || command.equals("up")){
			menu(player);
			
		}
		if(command.equals("control")){
			controlCmd(player);
		}
		
	}
	
	/**
	 * Erstellt einen String aus Zeilenumbrüchen.
	 * @return String aus Zeilenumbrüchen
	 */
	private String clear(){
		String s = "";
		for(int i=0; i<20; i++){
			s+='\n';
		}
		return s;
	}
	
	/**
	 * Terminal menu
	 */
	private void menu(EntityPlayer player){
		player.addChatComponentMessage(new ChatComponentText(clear()));
		String s = "";
		s+="Menu\n";
		s+="  control    [Raumschifsteuerung]\n";
		s+="  armour     [Waffensysteme]\n";
		s+="  protection [Abwehrmichanismen]\n";
		player.addChatComponentMessage(new ChatComponentText(s));
	}
	
	/**
	 * 
	 */
	private void controlCmd(EntityPlayer player){
		player.addChatComponentMessage(new ChatComponentText(clear()));
		String s = "";
		s+="Control commands:\n";
		s+="  accelerate\n";
		s+="  brake\n";
		s+="  turn right\n";
		s+="  turn left\n";
		s+="  teleport to xyz\n";
		s+="  up [goto main menu]";
		player.addChatComponentMessage(new ChatComponentText(s));
		
	}
}
