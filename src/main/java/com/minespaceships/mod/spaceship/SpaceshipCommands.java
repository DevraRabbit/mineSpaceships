/**
 * 
 */
package com.minespaceships.mod.spaceship;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.minespaceships.mod.overhead.ChatRegisterEntity;
import com.minespaceships.mod.overhead.CustomGuiChat;
import com.minespaceships.mod.overhead.IMenuInterface;
import com.minespaceships.util.PhaserUtils;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraft.world.World;

/**
 * @author jannes, ovae.
 * @version 20150313.
 */
public class SpaceshipCommands {
	//initialise the spaceship
	public static String initAuto ="init auto";
	//move the spaceship
	public static String moveForward = "move forward";
	public static String moveBack = "move back";
	public static String moveUp = "move up";
	public static String moveDown ="move down";
	public static String land ="land";
	public static String liftoff = "liftoff";
	//energy management
	public static String energy = "energy";
	public static String activate = "activate";
	public static String deactivate = "deactivate";	
	public static String phaser = "phaser";
	public static String shields = "shields";
	public static String engines = "engines";
	public static String balance = "balance";
	public static String shutdown = "shutdown";
	//spaceship functions
	public static String shieldDisable = "shield disable";
	public static String shieldActivate = "shiels activate";
	
	public enum EnergyType{
		phaser, shields, engines;
	}
	public enum EnergyCommandType{
		balance, shutdown;
	}

	//other commands
	public static String help = "help";

	public static void init(String command, final World worldObj, final ChatRegisterEntity commandBlock, final EntityPlayer player, final Spaceship ship) {
		if(command.equals("init auto")){
			try {
				ChatRegisterEntity ent = ((ChatRegisterEntity)worldObj.getTileEntity(commandBlock.getPos()));
				if(ent != null){
					Shipyard.getShipyard(worldObj).createShip(commandBlock.getPos(), worldObj);
				}
			} catch (Exception e) {
				e.printStackTrace();
				player.addChatComponentMessage(new ChatComponentText(e.toString()));
			}
			return;
		}
		command = command.substring("init".length()).replaceAll("\\s", "");

		Pattern poffset = Pattern.compile("([-+]?[0-9]+);([-+]?[0-9]+);([-+]?[0-9]+)to([-+]?[0-9]+);([-+]?[0-9]+);([-+]?[0-9]+)");
		Matcher moffset = poffset.matcher(command);
		if(moffset.matches()) {
			BlockPos from = new BlockPos(Integer.valueOf(moffset.group(1)), Integer.valueOf(moffset.group(2)), Integer.valueOf(moffset.group(3)));
			BlockPos to = new BlockPos(Integer.valueOf(moffset.group(4)), Integer.valueOf(moffset.group(5)), Integer.valueOf(moffset.group(6)));
			Shipyard.getShipyard(worldObj).createShip(from, commandBlock.getPos(), to, worldObj);
			
			player.addChatComponentMessage(new ChatComponentText("Initialized Spaceship at [" + commandBlock.getPos().getX() + "; " + commandBlock.getPos().getY() + "; " + commandBlock.getPos().getZ() + "] from (" + moffset.group(1) + "; " + moffset.group(2) + "; " + moffset.group(3) + ") to (" + moffset.group(4) + "; " + moffset.group(5) + "; " + moffset.group(6) + ")"));
		} else {
			player.addChatComponentMessage(new ChatComponentText("init: Error processing intput"));
			player.addChatComponentMessage(new ChatComponentText("usage: init #;#;# to #;#;# or try <init auto>"));
		}
	}
	
	public static void move(String command, final World worldObj, final ChatRegisterEntity commandBlock, final EntityPlayer player, Spaceship ship) {
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
		if(ship.getFacing()==EnumFacing.NORTH){
			playerRotation=2;
		} else if(ship.getFacing()==EnumFacing.SOUTH){
			playerRotation=0;
		} else if(ship.getFacing()==EnumFacing.EAST){
			playerRotation=3;
		} else if(ship.getFacing()==EnumFacing.WEST){
			playerRotation=1;
		}		
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
		
		Pattern poffset = Pattern.compile("([-+]?[0-9]+);([-+]?[0-9]+);([-+]?[0-9]+)");
		Matcher moffset = poffset.matcher(command);
		
		if(moffset.matches()) {
			BlockPos vec_move = new BlockPos(Integer.valueOf(moffset.group(1)), Integer.valueOf(moffset.group(2)), Integer.valueOf(moffset.group(3)));
			ship.setTarget(vec_move.add(ship.getOrigin()));
			//commandBlock.setPos(commandBlock.getPos().add(vec_move));
			
			player.addChatComponentMessage(new ChatComponentText("Moved Spaceship by (" + moffset.group(1) + "; " + moffset.group(2) + "; " + moffset.group(3) + ")"));
		} else {
			player.addChatComponentMessage(new ChatComponentText("move: Error processing intput"));
			player.addChatComponentMessage(new ChatComponentText("usage: move #;#;#"));
		}
	}
		
	public static void status(final World worldObj, final ChatRegisterEntity commandBlock, final EntityPlayer player, Spaceship ship) {
		
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
	
	public static String processDirectionMoveCommand(String command, int playerRotation) {
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
	
	public static void debug(String command, final ChatRegisterEntity commandBlock){
		if(command.equals("debug blockMap")){

			Shipyard.getShipyard(commandBlock.getWorld()).getShip(commandBlock.getPos(), commandBlock.getWorld()).debugMap();;

		}
	}
	public static void energy(String command, final World worldObj, final ChatRegisterEntity commandBlock, final EntityPlayer player, final Spaceship ship){
		if(command.startsWith(energy)){
			String[] parts = command.split(" ");
			boolean toActivate = false;
			if(parts[1].equals(activate)){
				toActivate = true;
			} else if (parts[1].equals(deactivate)){
				
			} else if(parts[1].equals(balance)){
				ship.balanceEnergy();
			} else if(parts[1].equals(shutdown)){
				ship.deactivateEverything();
			} else {
				return;
			}
			if(parts[2].equals(phaser)){
				if(toActivate){
					ship.activatePhasers();
				} else {
					ship.deactivatePhasers();
				}
			} else if(parts[2].equals(shields)){
				if(toActivate){
					ship.activateEngines();
				} else {
					ship.deactivateEngines();
				}
			} else if(parts[2].equals(engines)){
				if(toActivate){
					ship.activateEngines();
				} else {
					ship.deactivateEngines();
				}
			}
		}
	}

	public static void shoot(String command, final World worldObj, final ChatRegisterEntity commandBlock, final EntityPlayer player, Spaceship ship) {
		command = command.substring("shoot".length()).replaceAll("\\s", "");
	
		player.addChatComponentMessage(new ChatComponentText("IMMA FIRING MAH PHAZ0R!!"));
		
		Pattern poffset = Pattern.compile("([-+]?[0-9]*\\.*[0-9]+);([-+]?[0-9]*\\.*[0-9]+);([-+]?[0-9]*\\.*[0-9]+)");
		Matcher moffset = poffset.matcher(command);
		
		if(moffset.matches()) {
			Vec3 vec_dir = new Vec3(Double.valueOf(moffset.group(1)), Double.valueOf(moffset.group(2)), Double.valueOf(moffset.group(3)));
			
			PhaserUtils.shoot(commandBlock.getPos(), vec_dir, 10, 100, worldObj);
			player.addChatComponentMessage(new ChatComponentText("Fired Phaser in direction (" + moffset.group(1) + "; " + moffset.group(2) + "; " + moffset.group(3) + ")"));
		} else {
			player.addChatComponentMessage(new ChatComponentText("shoot: Error processing intput"));
			player.addChatComponentMessage(new ChatComponentText("usage: shoot #;#;#"));

		}
	}

	public static String help(final EntityPlayer player){
		String out="";
		out+=EnumChatFormatting.GOLD+" "+EnumChatFormatting.BOLD+"]--HELP--[\n\n"
		+"  "+EnumChatFormatting.YELLOW+"Menu structure:\n"
		+"    ]--Menuname (menu id)--[\n"
		+"        [position] sub menu name (sub menu id)\n"
		+'\n'
		+"  "+EnumChatFormatting.YELLOW+"Menu navigation:\n"
		+"   You can navigate in three different ways: \n"
		+"    -by the name of the Menu \n"
		+"    -by id and the number in brakets e.g. id2 \n"
		+"    -by the number for the submenu order.\n"
		+'\n'
		+"  "+EnumChatFormatting.YELLOW+"Menu colours:\n"
		+"    -"+EnumChatFormatting.GOLD+"Gold: header\n"
		+"    -"+EnumChatFormatting.WHITE+"White : normal menus\n"
		+"    -"+EnumChatFormatting.GREEN+"Green:  menus with a functinality\n"
		+"    -"+EnumChatFormatting.RED+"Red: error"
		+'\n'
		+EnumChatFormatting.RED+"\n   To get back in the parent menu, you can either\n"
		+EnumChatFormatting.RED+"    enter 'm','up' or 'parent'.";
		player.addChatComponentMessage(new ChatComponentText(out));
		return out;
	}
	public static String activateCommand(boolean toActivate, EnergyType type){
		String command = energy;
		if(toActivate){
			command += " "+activate;			
		} else {
			command += " "+deactivate;
		}
		switch(type){
		case phaser:
			command += " "+phaser;
			break;
		case engines:
			command += " "+engines;
		case shields:
			command += " "+shields;
		}
		return command;
	}
	public static String EnergyCommand(EnergyCommandType type){
		String command = energy;
		switch(type){
		case balance:
			command += " "+balance;
			break;
		case shutdown:
			command += " "+shutdown;
			break;
		}
		return command;
	}

	public static void land(final IMenuInterface terminal){
		/*
		try{
			double x,y,z;
			World world = terminal.getChatRegisterEntity().getWorld();
			Spaceship ship = Shipyard.getShipyard().getShip(terminal.getChatRegisterEntity().getPos(), terminal.getChatRegisterEntity().getWorld());
			if(ship == null) {
				//return "Please initialise the spaceship first";
			}
			x = terminal.getChatRegisterEntity().getPos().getX();
			y = terminal.getChatRegisterEntity().getPos().getY();
			z = terminal.getChatRegisterEntity().getPos().getZ();
			BlockPos minPos = ship.getMinPos();

			int height=0;
			boolean run = true;
			int posY= minPos.getY()-1;
			do{
				IBlockState current = world.getBlockState(new BlockPos(x,posY,z));
				if(current == Blocks.air.getDefaultState()){
					run = true;
				}else{
					run = false;
				}
				posY--;
				height++;
			}while(run);

			//(double)x, (double)y, (double)z
			BlockPos position = new BlockPos(x, y-height+2, z);
			ship.move(position);
			//return "land successful.\nPress 'm' to get back.";
		}catch(Exception e){
			//return "While trying to land an error occurred: "+e;
		}
		*/
	}

}
