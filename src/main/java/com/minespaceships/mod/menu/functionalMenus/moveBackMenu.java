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
 * Move the spaceship position back.
 * @author ovae.
 * @version 20150310.
 */
public class moveBackMenu extends Menu implements FunctionalParamMenu{

	public moveBackMenu(String name) {
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

		double x,y,z;
		Pattern pattern = Pattern.compile("([\\-\\+]?[0-9]*[\\.]?[0-9]+);[\\s*]?([\\-\\+]?[0-9]*[\\.]?[0-9]*);[\\s*]?([\\-\\+]?[0-9]*[\\.]?[0-9]*)");
		Matcher matcher = pattern.matcher(command);
		if(matcher.matches()){
			x = Double.valueOf(matcher.group(1));
			y = Double.valueOf(matcher.group(2));
			z = Double.valueOf(matcher.group(3));

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
		return "move back not implemented yet!";
	}

}
