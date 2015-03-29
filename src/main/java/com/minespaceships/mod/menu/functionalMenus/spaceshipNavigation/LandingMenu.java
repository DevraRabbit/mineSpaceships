package com.minespaceships.mod.menu.functionalMenus.spaceshipNavigation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

import com.minespaceships.mod.CommandMessage;
import com.minespaceships.mod.MineSpaceships;
import com.minespaceships.mod.menu.FunctionalMenu;
import com.minespaceships.mod.menu.Menu;
import com.minespaceships.mod.menu.functionalMenus.energyMenus.TerminalUtil;
import com.minespaceships.mod.overhead.CustomGuiChat;
import com.minespaceships.mod.overhead.IMenuInterface;
import com.minespaceships.mod.spaceship.Shipyard;
import com.minespaceships.mod.spaceship.Spaceship;
import com.minespaceships.mod.spaceship.SpaceshipCommands;

/**
 * Lands the spaceship.
 * @author ovae.
 * @version 20150323.
 */
public class LandingMenu extends Menu implements FunctionalMenu{

	/**
	 * Creates a new landingMenu.
	 * @param name
	 */
	public LandingMenu() {
		super("land");
	}

	/**
	 * The functionality of the menu is activated by this method.
	 * @param command
	 * @param terminal
	 */
	@Override
	public String activate(String command, IMenuInterface terminal) {
		Spaceship ship = TerminalUtil.getShip(terminal);
		if(ship != null){
			int distToGround = ship.getDistanceToGround();
			ship.setTarget(ship.getOrigin().add(0, -distToGround, 0));
			return EnumChatFormatting.GREEN+"Landing!";
		}
		return "No Spaceship...";
	}

}
