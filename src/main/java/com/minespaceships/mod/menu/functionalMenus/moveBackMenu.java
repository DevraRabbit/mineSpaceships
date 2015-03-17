package com.minespaceships.mod.menu.functionalMenus;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.minecraft.util.BlockPos;
import net.minecraftforge.fml.relauncher.Side;

import com.minespaceships.mod.CommandMessage;
import com.minespaceships.mod.MineSpaceships;
import com.minespaceships.mod.menu.FunctionalParamMenu;
import com.minespaceships.mod.menu.Menu;
import com.minespaceships.mod.overhead.CustomGuiChat;
import com.minespaceships.mod.spaceship.Shipyard;
import com.minespaceships.mod.spaceship.Spaceship;
import com.minespaceships.mod.spaceship.SpaceshipCommands;

/**
 * Move the spaceship position back.
 * @author ovae.
 * @version 20150312.
 */
public class moveBackMenu extends Menu implements FunctionalParamMenu{

	/**
	 * Creates a new moveBackMenu.
	 * @param name
	 */
	public moveBackMenu(String name) {
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
		//command = "move back "+command;
		//MineSpaceships.network.sendToServer(new CommandMessage(terminal.getChatRegisterEntity().getPos().toLong()+","+terminal.getChatRegisterEntity().getWorld().provider.getDimensionId()+","+ command));
		terminal.getChatRegisterEntity().onCommand("move back "+command, terminal.getPlayerEntity());
		return "move back failed!";
	}

}
