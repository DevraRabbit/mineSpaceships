/**
 * 
 */
package com.minespaceships.mod.spaceship;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.minespaceships.mod.overhead.ChatRegisterEntity;

import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

/**
 * @author jannes
 *
 */
public class SpaceshipCommands {

	public static void init(String command, final WorldServer worldObj, final ChatRegisterEntity commandBlock, final EntityPlayer player, final Spaceship ship) {
		command = command.substring("init".length()).replaceAll("\\s", "");

		Pattern poffset = Pattern.compile("([\\-\\+]?[0-9]+);([\\-\\+]?[0-9]+);([\\-\\+]?[0-9]+)to([\\-\\+]?[0-9]+);([\\-\\+]?[0-9]+);([\\-\\+]?[0-9]+)");
		Matcher moffset = poffset.matcher(command);
		if(moffset.matches()) {
			BlockPos from = new BlockPos(Integer.valueOf(moffset.group(1)), Integer.valueOf(moffset.group(2)), Integer.valueOf(moffset.group(3)));
			BlockPos to = new BlockPos(Integer.valueOf(moffset.group(4)), Integer.valueOf(moffset.group(5)), Integer.valueOf(moffset.group(6)));
			((ChatRegisterEntity)worldObj.getTileEntity(commandBlock.getPos())).createShip(from, commandBlock.getPos(), to, worldObj);
			
			player.addChatComponentMessage(new ChatComponentText("Initialized Spaceship at [" + commandBlock.getPos().getX() + "; " + commandBlock.getPos().getY() + "; " + commandBlock.getPos().getZ() + "] from (" + moffset.group(1) + "; " + moffset.group(2) + "; " + moffset.group(3) + ") to (" + moffset.group(4) + "; " + moffset.group(5) + "; " + moffset.group(6) + ")"));
		} else {
			player.addChatComponentMessage(new ChatComponentText("init: Error processing intput"));
			player.addChatComponentMessage(new ChatComponentText("usage: init #;#;# to #;#;#"));
		}
	}
	
	public static void move(String command, final WorldServer worldObj, final ChatRegisterEntity commandBlock, final EntityPlayer player, Spaceship ship) {
		command = command.substring("move".length()).replaceAll("\\s", "");
		
		if((ChatRegisterEntity)worldObj.getTileEntity(commandBlock.getPos()) != null){
			ship = ((ChatRegisterEntity)worldObj.getTileEntity(commandBlock.getPos())).getShip();
		}
		if(ship == null) {
			player.addChatComponentMessage(new ChatComponentText("move: Please initialize the Spaceship first"));
			return;
		}

		//Process "move front, back, etc. commands depending on the direction the player looks"
		//Taken from http://www.minecraftforge.net/forum/index.php?topic=6514.0
		int playerRotation = MathHelper.floor_double((double)(player.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;

		if(command.startsWith("forward")) {
			command = command.substring("forward".length());
			command = processDirectionMoveCommand(command, playerRotation);
		} else if(command.startsWith("back")) {
			command = command.substring("back".length());
			command = processDirectionMoveCommand(command, (playerRotation + 2) % 4);
		} else if(command.startsWith("right")) {
			command = command.substring("right".length());
			command = processDirectionMoveCommand(command, (playerRotation + 1) % 4);
		} else if(command.startsWith("left")) {
			command = command.substring("left".length());
			command = processDirectionMoveCommand(command, (playerRotation + 3) % 4);
		} else if(command.startsWith("up")) {
			Pattern p = Pattern.compile("up([0-9]+)");
			Matcher m = p.matcher(command);
			
			if(m.matches()) {
				command = "0;" + m.group(1) + ";0";
			}
		} else if(command.startsWith("down")) {
			Pattern p = Pattern.compile("down([0-9]+)");
			Matcher m = p.matcher(command);
			
			if(m.matches()) {
				command = "0;-" + m.group(1) + ";0";
			}
		}
		
		Pattern poffset = Pattern.compile("([\\-\\+]?[0-9]+);([\\-\\+]?[0-9]+);([\\-\\+]?[0-9]+)");
		Matcher moffset = poffset.matcher(command);
		
		if(moffset.matches()) {
			BlockPos vec_move = new BlockPos(Integer.valueOf(moffset.group(1)), Integer.valueOf(moffset.group(2)), Integer.valueOf(moffset.group(3)));
			ship.moveTo(vec_move);
			commandBlock.setPos(commandBlock.getPos().add(vec_move));
			
			player.addChatComponentMessage(new ChatComponentText("Moved Spaceship by (" + moffset.group(1) + "; " + moffset.group(2) + "; " + moffset.group(3) + ")"));
		} else {
			player.addChatComponentMessage(new ChatComponentText("move: Error processing intput"));
			player.addChatComponentMessage(new ChatComponentText("usage: move #;#;#"));
		}
	}
		
	public static void status(final WorldServer worldObj, final ChatRegisterEntity commandBlock, final EntityPlayer player, Spaceship ship) {
		
		if((ChatRegisterEntity)worldObj.getTileEntity(commandBlock.getPos()) != null){
			ship = ((ChatRegisterEntity)worldObj.getTileEntity(commandBlock.getPos())).getShip();
		}
		if(ship == null) {
			player.addChatComponentMessage(new ChatComponentText("status: Not initialized"));
			return;
		}

		player.addChatComponentMessage(new ChatComponentText("status: Spaceship"));
		player.addChatComponentMessage(new ChatComponentText(ship.toString()));
		player.addChatComponentMessage(new ChatComponentText("status: TileEntity (remote)"));
		player.addChatComponentMessage(new ChatComponentText(((ChatRegisterEntity)worldObj.getTileEntity(commandBlock.getPos())).toString()));
	}
	
	private static String processDirectionMoveCommand(String command, int playerRotation) {
		Pattern p = Pattern.compile("[0-9]+");
		Matcher m = p.matcher(command);
		
		if(m.matches()) {
			switch (playerRotation) {
				case 0:
				command = "0;0;" + command;
				break;
				case 1:
				command = "-" + command + ";0;0";
				break;
				case 2:
				command = "0;0;-" + command;
				break;
				case 3:
				command = command + ";0;0";
				break;
			}
		}
		return command;
	}
}
