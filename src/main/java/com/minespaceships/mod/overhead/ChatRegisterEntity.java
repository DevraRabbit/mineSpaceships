package com.minespaceships.mod.overhead;

import java.util.regex.*;

import com.example.examplemod.own.terminalMenu;

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
		//Define the 'calc' command, which parses a math expression
		} else if(command.startsWith("calc")) {
			//prepare the math expression
			command = command.substring(4).trim(); 
			try {
				player.addChatComponentMessage(new ChatComponentText(command + " = " + solve(command.replaceAll("\\s", ""))));
			} catch (Exception ex) {
				player.addChatComponentMessage(new ChatComponentText("Error processing intput"));
			}
		}
		terminalMenu.onCommand(command, player);
	}

	
	//Attributes for math parser
	private Pattern brackets = Pattern.compile("\\(.*?\\)");
	private Pattern times = Pattern.compile("(\\-?[0-9\\.]+)\\*(\\-?[0-9\\.]+)");
	private Pattern div = Pattern.compile("(\\-?[0-9\\.]+)/(\\-?[0-9\\.]+)");
	private Pattern plus = Pattern.compile("(\\-?[0-9\\.]+)\\+(\\-?[0-9\\.]+)");
	private Pattern minus = Pattern.compile("(\\-?[0-9\\.]+)\\-(\\-?[0-9\\.]+)");
	private Pattern done = Pattern.compile("\\-?[0-9\\.]+");
	private Matcher mb, mt, md, mp, mm, mdone;
	
	//Recursively parses a String containing a math expression
	//Supported are brackets, +-*/, negative numbers and doubles 
	private String solve(String expr) throws Exception {
		mb = brackets.matcher(expr);
		mt = times.matcher(expr);
		md = div.matcher(expr);
		mp = plus.matcher(expr);
		mm = minus.matcher(expr);
		mdone = done.matcher(expr);
		
		if(mdone.matches()) return expr;
		
		if(mb.find()) {
			Matcher m = mb;
			String before = expr.substring(0, m.start());
			String inner = expr.substring(m.start() + 1, m.end() - 1);
			String after = expr.substring(m.end());			
			return solve(before + solve(inner) + after);
		} else if (mt.find()) {
			Matcher m = mt;
			String before = expr.substring(0, m.start());
			
			String first = expr.substring(m.start(1), m.end(1));
			String second = expr.substring(m.start(2), m.end(2));
			
			String inner = String.valueOf(Double.valueOf(first) * Double.valueOf(second));
			
			String after = expr.substring(m.end());
			
			return solve(before + inner + after);
		} else if (md.find()) {
			Matcher m = md;
			String before = expr.substring(0, m.start());
			
			String first = expr.substring(m.start(1), m.end(1));
			String second = expr.substring(m.start(2), m.end(2));
			
			String inner = String.valueOf(Double.valueOf(first) / Double.valueOf(second));
			
			String after = expr.substring(m.end());
			
			return solve(before + inner + after);
		} else if (mp.find()) {
			Matcher m = mp;
			String before = expr.substring(0, m.start());
			
			String first = expr.substring(m.start(1), m.end(1));
			String second = expr.substring(m.start(2), m.end(2));

			String inner = String.valueOf(Double.valueOf(first) + Double.valueOf(second));
			
			String after = expr.substring(m.end());
			
			return solve(before + inner + after);
		} else if (mm.find()) {
			Matcher m = mm;
			String before = expr.substring(0, m.start());
			
			String first = expr.substring(m.start(1), m.end(1));
			String second = expr.substring(m.start(2), m.end(2));
			
			String inner = String.valueOf(Double.valueOf(first) - Double.valueOf(second));
			
			String after = expr.substring(m.end());
			
			return solve(before + inner + after);
		}
		return "Error";
	}
}
