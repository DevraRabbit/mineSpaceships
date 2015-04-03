package com.minespaceships.mod.menu.functionalMenus.spaceshipNavigation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;

import com.minespaceships.mod.menu.FunctionalParamMenu;
import com.minespaceships.mod.menu.Menu;
import com.minespaceships.mod.overhead.CustomGuiChat;
import com.minespaceships.mod.overhead.IMenuInterface;
import com.minespaceships.mod.spaceship.Spaceship;

import scala.reflect.api.Types.ThisTypeExtractor;

/**
 * A new functional menu, for the 'to target' functionality.
 * @author ovae.
 * @version 20150320.
 */
public class ToTargetFunktionalMenu extends Menu implements FunctionalParamMenu{

	//array of all parameters.
	private double[] param;

	/**
	 * Creates new functional menu, for the 'to target' functionality.
	 * @param name
	 */
	public ToTargetFunktionalMenu() {
		super("Move To..");
	}

	/**
	 * The functionality of the menu is activated by this method.
	 * @param command
	 * @param terminal
	 */
	@Override
	public String activate(String command, IMenuInterface terminal, String data){
		if(command.equals(null)){
			throw new IllegalArgumentException("Command can not be null.");
		}
		if(command.trim().isEmpty()){
			throw new IllegalArgumentException("Command can not be empty.");
		}
		double x=0, y=0, z=0;
		try{
			command = command.replaceAll("\\s*", "");
			Pattern pattern = Pattern.compile("([\\-\\+]?[0-9]*[\\.]?[0-9]+);[\\s*]?([\\-\\+]?[0-9]*[\\.]?[0-9]*);[\\s*]?([\\-\\+]?[0-9]*[\\.]?[0-9]*)");
			Matcher matcher = pattern.matcher(command);
			if(matcher.matches()){
				x = Double.valueOf(matcher.group(1));
				y = Double.valueOf(matcher.group(2));
				z = Double.valueOf(matcher.group(3));

				try{
					Spaceship ship = terminal.getChatRegisterEntity().getShip();
					//(double)x, (double)y, (double)z
					BlockPos position = new BlockPos(x, y, z);
					if(ship == null) {
						terminal.display("move: Please initialise the Spaceship first", terminal.getPlayerEntity(), true);
					}
					ship.move(position);
					return ">> to target <<\nPress 'm' to get back.";
				}catch(Exception e){
					System.err.println("ship is broken");
				}
			}
		}catch(NullPointerException f){
			System.err.println("NullPointerException: regex failed");
		}catch(IllegalArgumentException g){
			System.err.println("IllegalArgumentException: regex failed");
		}

		return "To target failed!";
	}

	@Override
	public String getData() {
		return null;
	}

}
