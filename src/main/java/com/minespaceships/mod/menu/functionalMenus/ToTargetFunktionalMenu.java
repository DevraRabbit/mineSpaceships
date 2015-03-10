package com.minespaceships.mod.menu.functionalMenus;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;

import com.minespaceships.mod.menu.FunctionalParamMenu;
import com.minespaceships.mod.menu.Menu;
import com.minespaceships.mod.overhead.CustomGuiChat;
import com.minespaceships.mod.spaceship.Spaceship;

import scala.reflect.api.Types.ThisTypeExtractor;

/**
 * A new functional menu, for the 'to target' functionality.
 * @author ovae.
 * @version 20150226
 */
public class ToTargetFunktionalMenu extends Menu implements FunctionalParamMenu{

	//array of all parameters.
	private double[] param;

	//The terminal to write in.
	private CustomGuiChat terminal;

	/**
	 * Creates new functional menu, for the 'to target' functionality.
	 * @param name
	 * @param terminal
	 */
	public ToTargetFunktionalMenu(String name, CustomGuiChat terminal) {
		super(name);
		if(terminal.equals(null)){
			throw new IllegalArgumentException("Terminal can not be null.");
		}
		this.terminal = terminal;
	}

	/**
	 * The functionality of the menu is activated by this method.
	 * @param command
	 */
	@Override
	public String activate(String command){
		if(command.equals(null)){
			throw new IllegalArgumentException("Command can not be null.");
		}
		if(command.trim().isEmpty()){
			throw new IllegalArgumentException("Command can not be empty.");
		}
		double x=0, y=0, z=0;
		try{
			
			command = command.replaceAll("\\s*", "");
			
			//Pattern pattern = Pattern.compile("([\\-\\+]?[0-9]*\\.[0-9]*);([\\-\\+]?[0-9]*\\.[0-9]*);([\\-\\+]?[0-9]*\\.[0-9]*)");
			Pattern pattern = Pattern.compile("([\\-\\+]?[0-9]*[\\.]?[0-9]+);[\\s*]?([\\-\\+]?[0-9]*[\\.]?[0-9]*);[\\s*]?([\\-\\+]?[0-9]*[\\.]?[0-9]*)");
			Matcher matcher = pattern.matcher(command);
			if(matcher.matches()){
				x = Double.valueOf(matcher.group(1));
				y = Double.valueOf(matcher.group(2));
				z = Double.valueOf(matcher.group(3));

				try{
					Spaceship ship = this.terminal.getChatRegisterEntity().getShip();
					//(double)x, (double)y, (double)z
					BlockPos position = new BlockPos(x, y, z);
					if(ship == null) {
						this.terminal.display("move: Please initialise the Spaceship first", true);
					}
					ship.setTarget(position);
					return ">> To target <<";
				}catch(Exception e){
					System.err.println("ship is broken");
				}
			}

		}catch(NullPointerException f){
			System.err.println("NullPointerException: regex failed");
		}catch(IllegalArgumentException g){
			System.err.println("IllegalArgumentException: regex failed");
		}

		return ">> To target failed <<";
	}

}
