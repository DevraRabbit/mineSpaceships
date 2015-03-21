package com.minespaceships.mod.menu.functionalMenus;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;

import com.minespaceships.mod.menu.FunctionalParamMenu;
import com.minespaceships.mod.menu.Menu;
import com.minespaceships.mod.overhead.CustomGuiChat;
import com.minespaceships.mod.spaceship.Shipyard;
import com.minespaceships.mod.spaceship.Spaceship;
import com.minespaceships.mod.spaceship.SpaceshipCommands;

/**
 * Move the spaceship position up.
 * @author ovae.
 * @version 20150310.
 */
public class moveUpMenu extends Menu implements FunctionalParamMenu{
	
	public moveUpMenu(String name) {
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
		System.out.println(command);
		double x,y,z;
		Pattern pattern = Pattern.compile("\\d*");
		//Pattern pattern = Pattern.compile("\\d");
		Matcher matcher = pattern.matcher(command);
		if(matcher.matches()){
			x = terminal.getChatRegisterEntity().getPos().getX();
			y = terminal.getChatRegisterEntity().getPos().getY() +Double.parseDouble(command);
			z = terminal.getChatRegisterEntity().getPos().getZ();
			try{
				Spaceship ship = Shipyard.getShipyard(terminal.getChatRegisterEntity().getWorld()).getShip(terminal.getChatRegisterEntity().getPos(), terminal.getChatRegisterEntity().getWorld());
				//(double)x, (double)y, (double)z
				BlockPos position = new BlockPos(x, y, z);

				if(ship == null) {
					terminal.display("move: Please initialise the Spaceship first", true);
				}
				ship.move(position);

				return ">> To target <<";
			}catch(Exception e){
				System.err.println("ship is broken");
			}
		}
		return "move up not implemented yet!";
	}

}
