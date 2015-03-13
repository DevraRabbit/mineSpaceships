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
 * Move the spaceship position down.
 * @author ovae.
 * @version 20150312.
 */
public class moveDownMenu extends Menu implements FunctionalParamMenu{

	/**
	 * Creates a new moveDownMenu.
	 * @param name
	 */
	public moveDownMenu(String name) {
		super(name);
	}

	/**
	 * The functionality of the menu is activated by this method.
	 * @param command
	 * @param terminal
	 */
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
		Matcher matcher = pattern.matcher(command);
		if(matcher.matches()){
			x = terminal.getChatRegisterEntity().getPos().getX();
			y = terminal.getChatRegisterEntity().getPos().getY() - Double.parseDouble(command);
			z = terminal.getChatRegisterEntity().getPos().getZ();
			try{
				Spaceship ship = Shipyard.getShipyard().getShip(terminal.getChatRegisterEntity().getPos(), terminal.getChatRegisterEntity().getWorld());
				//(double)x, (double)y, (double)z
				BlockPos position = new BlockPos(x, y, z);

				if(ship == null) {
					terminal.display("move: Please initialise the Spaceship first", true);
				}
				ship.move(terminal.getChatRegisterEntity(), position);
				return ">> move down <<\nPress 'm' to get back.";
			}catch(Exception e){
				System.err.println("ship is broken");
			}
		}
		return "move down failed!";
	}

}
