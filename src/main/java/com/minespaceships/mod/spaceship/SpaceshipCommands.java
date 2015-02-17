/**
 * 
 */
package com.minespaceships.mod.spaceship;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.minespaceships.mod.overhead.ChatRegisterEntity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;

/**
 * @author jannes
 *
 */
public class SpaceshipCommands {

	public static void move(String command, final World worldObj, final ChatRegisterEntity commandBlock, final EntityPlayer player, final Spaceship ship) {
		command = command.substring("move".length()).replaceAll("\\s", "");

		Pattern poffset = Pattern.compile("([\\-\\+]?[0-9]+);([\\-\\+]?[0-9]+);([\\-\\+]?[0-9]+)");
		Matcher moffset = poffset.matcher(command);
		if(moffset.matches()) {
			BlockPos vec_move = new BlockPos(Integer.valueOf(moffset.group(1)), Integer.valueOf(moffset.group(2)), Integer.valueOf(moffset.group(3)));
			ship.copyTo(vec_move);
			
			player.addChatComponentMessage(new ChatComponentText("Moving by " + moffset.group(1) + "; " + moffset.group(2) + "; " + moffset.group(3)));
		} else {
			player.addChatComponentMessage(new ChatComponentText("move: Error processing intput"));
			player.addChatComponentMessage(new ChatComponentText("usage: move #;#;#"));
		}
	}

	public static void init(String command, final World worldObj, final ChatRegisterEntity commandBlock, final EntityPlayer player, final Spaceship ship) {
		command = command.substring("init".length()).replaceAll("\\s", "");

		Pattern poffset = Pattern.compile("([\\-\\+]?[0-9]+);([\\-\\+]?[0-9]+);([\\-\\+]?[0-9]+)to([\\-\\+]?[0-9]+);([\\-\\+]?[0-9]+);([\\-\\+]?[0-9]+)");
		Matcher moffset = poffset.matcher(command);
		if(moffset.matches()) {
			BlockPos from = new BlockPos(Integer.valueOf(moffset.group(1)), Integer.valueOf(moffset.group(2)), Integer.valueOf(moffset.group(3)));
			BlockPos to = new BlockPos(Integer.valueOf(moffset.group(4)), Integer.valueOf(moffset.group(5)), Integer.valueOf(moffset.group(6)));
			commandBlock.setShip(new Spaceship(from, commandBlock.getPos(), to, worldObj));
			
			player.addChatComponentMessage(new ChatComponentText("Moving by " + moffset.group(1) + "; " + moffset.group(2) + "; " + moffset.group(3)));
		} else {
			player.addChatComponentMessage(new ChatComponentText("move: Error processing intput"));
			player.addChatComponentMessage(new ChatComponentText("usage: init #;#;# to #;#;#"));
		}
	}
}
