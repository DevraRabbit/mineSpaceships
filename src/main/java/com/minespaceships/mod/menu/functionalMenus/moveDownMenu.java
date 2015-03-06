package com.minespaceships.mod.menu.functionalMenus;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;

import com.minespaceships.mod.menu.FunctionalParamMenu;
import com.minespaceships.mod.menu.Menu;
import com.minespaceships.mod.overhead.CustomGuiChat;
import com.minespaceships.mod.spaceship.Spaceship;
import com.minespaceships.mod.spaceship.SpaceshipCommands;

/**
 * 
 * @author ovae.
 * @version 20150226
 */
public class moveDownMenu extends Menu implements FunctionalParamMenu{

	public moveDownMenu(String name) {
		super(name);
	}

	@Override
	public String activate(String command, CustomGuiChat terminal) {
		if(command.trim().isEmpty()){
			return "command can not be empty.";
		}
		if(command.equals(null)){
			return "command can not be null.";
		}
		/*
		int playerRotation = MathHelper.floor_double((double)(terminal.mc.thePlayer.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
		command = SpaceshipCommands.processDirectionMoveCommand(command, playerRotation);
		*/
		double x,y,z;
		Pattern pattern = Pattern.compile("\\d*");
		//Pattern pattern = Pattern.compile("\\d");
		Matcher matcher = pattern.matcher(command);
		if(matcher.matches()){
			x = terminal.getChatRegisterEntity().getPos().getX();
			y = terminal.getChatRegisterEntity().getPos().getY() - Double.parseDouble(command);
			z = terminal.getChatRegisterEntity().getPos().getZ();
			try{
				Spaceship ship = terminal.getChatRegisterEntity().getShip();
				//(double)x, (double)y, (double)z
				BlockPos position = new BlockPos(x, y, z);

				if(ship == null) {
					terminal.display("move: Please initialise the Spaceship first", true);
				}
				ship.setTarget(position);

				return ">> To target <<";
			}catch(Exception e){
				System.err.println("ship is broken");
			}
		}
		return "move down not implemented yet!";
	}

}
