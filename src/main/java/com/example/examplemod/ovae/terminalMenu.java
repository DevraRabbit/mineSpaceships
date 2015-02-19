package com.example.examplemod.ovae;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;

public class terminalMenu {

	public static void onCommand(String command, EntityPlayer player){
		//define a very first command to see if it works.
				if(command.equals("hello")){
					//send something to the player to see if we get a feedback from our command.
					player.addChatComponentMessage(new ChatComponentText("I love you!"));
				}
				
				if(command.equals("what are you?")){
					player.addChatComponentMessage(new ChatComponentText("A cube."));
				}
				if(command.equals("rm Ex")){
					player.addExperience(-1000);
				}
				if(command.equals("add Ex")){
					player.addExperience(1000);
				}
				/*
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
	 * Creates a String with 20 new lines
	 * @return string
	 */
	private static String clear(){
		String s = "";
		for(int i=0; i<20; i++){
			s+='\n';
		}
		return s;
	}
	
	/**
	 * Terminal menu
	 */
	private static void menu(EntityPlayer player){
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
	private static void controlCmd(EntityPlayer player){
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
