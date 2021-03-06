package com.minespaceships.mod.overhead;

import java.util.regex.*;

import com.example.examplemod.ovae.terminalMenu;
import com.minespaceships.mod.CommandMessage;
import com.minespaceships.mod.MineSpaceships;
import com.minespaceships.mod.events.PlayerTracker;
import com.minespaceships.mod.menu.FunctionalMenu;
import com.minespaceships.mod.menu.FunctionalParamMenu;
import com.minespaceships.mod.menu.Menu;
import com.minespaceships.mod.menu.SpaceshipMenu;
import com.minespaceships.mod.menu.MenuDisplay;
import com.minespaceships.mod.menu.functionalMenus.LoginMenu;
import com.minespaceships.mod.menu.functionalMenus.createShip.NoSpaceshipEntityMenu;
import com.minespaceships.mod.spaceship.AllShipyards;
import com.minespaceships.mod.spaceship.ISpaceshipPart;
import com.minespaceships.mod.spaceship.Shipyard;
import com.minespaceships.mod.spaceship.Spaceship;
import com.minespaceships.mod.spaceship.SpaceshipCommands;
import com.minespaceships.mod.spaceship.Turn;
import com.minespaceships.util.BlockCopier;
import com.minespaceships.util.Calculator;
import com.minespaceships.util.IMoveable;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.Vec3;
import net.minecraft.util.Vec3i;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * 
 * @author DevraRabbit ,jh0ker, ovae.
 * @version 20150320.
 */
public class ChatRegisterEntity extends TileEntity {
	private MenuDisplay spaceshipMenu;
	private MenuDisplay noSpaceshipMenu;
	private MenuDisplay loginMenu;

	private static String dimension = "dimension";
	private static String shipKey = "SpaceshipKey";
	private IMenuInterface terminal;

	public ChatRegisterEntity() {
		super();
	}
	public void setSpaceshipMenu(MenuDisplay display){
		spaceshipMenu = display;
	}
	public void setLoginMenu(MenuDisplay display){
		loginMenu = display;
	}
	public void setNoSpaceshipMenu(MenuDisplay display){
		noSpaceshipMenu = display;
	}
	public MenuDisplay getSpaceshipMenu(){
		return spaceshipMenu;
	}
	public MenuDisplay getLoginMenu(){
		return loginMenu;
	}
	public MenuDisplay getNoSpaceshipMenu(){
		return noSpaceshipMenu;
	}

	@Override
	public void setPos(BlockPos pos){
		super.setPos(pos);
	}

	@Override
	public void invalidate(){
		super.invalidate();
	}

	@Override
	public void writeToNBT(NBTTagCompound par1){
		Shipyard yard = Shipyard.getShipyard(worldObj);
		Spaceship ship = yard.getShip(pos, worldObj);
		if(ship != null){
			par1.setBoolean(shipKey, true);
			par1.setInteger(dimension, worldObj.provider.getDimensionId());
			System.out.println("Writing ship into NBT");
			ship.writeToNBT(par1, shipKey);
		} else {
			par1.setBoolean(shipKey, false);
		}
		super.writeToNBT(par1); 
		this.markDirty();
	}

	@Override
	public void readFromNBT(NBTTagCompound par1){
		int id = par1.getInteger(dimension);
		boolean hasShip = par1.getBoolean(shipKey);
		if(hasShip){
			System.out.println("Reading ship from NBT");
			AllShipyards.putData(id, par1, shipKey);
		}
		super.readFromNBT(par1);
		this.markDirty();
	}

	@SideOnly(Side.SERVER)
	@Override
	public Packet getDescriptionPacket(){
		NBTTagCompound syncData = new NBTTagCompound();
		this.writeToNBT(syncData);
		System.out.println("Send Packet!");
		return new S35PacketUpdateTileEntity(this.pos, 1, syncData);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt){
		System.out.println("Recieved Packet!");
		readFromNBT(pkt.getNbtCompound());
	}

	/**
	 * Activates the TileEntity and opens a custom chat to the player
	 * @param player
	 */
	public void Activate(EntityPlayer player){
		Side side = FMLCommonHandler.instance().getEffectiveSide();
		this.terminal = MineSpaceships.proxy.makeTerminal(player, this);
	}

	/**
	 * Executing the given command considering the player that sent it.
	 * @param command
	 * @param player
	 */
	public void onCommand(String command, EntityPlayer player){
		Side side = FMLCommonHandler.instance().getEffectiveSide();
		if(side == Side.CLIENT) {
			if(player != null){
				MineSpaceships.network.sendToServer(new CommandMessage(this.pos.toLong()+","+worldObj.provider.getDimensionId()+","+player.getUniqueID()+","+ command));
			}
		}
	}
	public void onCommandForced(String command){
		Side side = FMLCommonHandler.instance().getEffectiveSide();
		if(side == Side.CLIENT) {
			MineSpaceships.network.sendToServer(new CommandMessage(this.pos.toLong()+","+worldObj.provider.getDimensionId()+","+"0"+","+ command));
		}
	}

	public Spaceship getShip() {
		return Shipyard.getShipyard(worldObj).getShip(pos, worldObj);
	}

	/**
	 * Executes a command unrelated if Server or client side.
	 */
	public void executeCommand(String command, EntityPlayer player){
		Side side = FMLCommonHandler.instance().getEffectiveSide();

		if(terminal == null){
			terminal = MineSpaceships.proxy.makeTerminal(player, this);
		}

		/**This prevents the navigation in the normal menu, if no spaceship has 
		 * created or you are not logged in.
		 */
		if(command.equals("m") && !LoginMenu.getLoggedIn() ){
			return;
		}

		boolean isMenu = executeMenu(command);
		if(isMenu){ return;}
		else if(command.equals("hello")){
			//send something to the player to see if we get a feedback from our command.
			if(player != null)player.addChatComponentMessage(new ChatComponentText("I love you!"));
		//Define the 'calc' command, which parses a math expression
		} else if(command.startsWith("calc")) {
			Calculator.calc(command, player);
		} else if (command.startsWith("init")) {
			SpaceshipCommands.init(command, worldObj, this, player, getShip());
		} else if (command.startsWith("move")) {
			SpaceshipCommands.move(command, worldObj, this, player, getShip());
		} else if (command.equals("test1")) {
			SpaceshipCommands.init("init -4;-4;-4 to 4;4;4", worldObj, this, player, getShip());
			SpaceshipCommands.move("move 0;15;0", worldObj, this, player, getShip());
		} else if (command.startsWith("turn ")) {
			command = command.substring(4).trim();
			if (command.equals("left")) {
				Turn.ninetyDeg(worldObj, pos, Turn.LEFT);
			} else if (command.equals("right")) {
				Turn.ninetyDeg(worldObj, pos, Turn.RIGHT);
			} else if (command.equals("around")) {
				Turn.around(worldObj, pos);
			} else {
				if(player != null)player.addChatComponentMessage(new ChatComponentText("Invalid direction! Only left, right or around!"));
			}
		} else if(command.equals("status")) {
			SpaceshipCommands.status(worldObj, this, player, getShip());
		} else if(command.startsWith("shoot")) {
			SpaceshipCommands.shoot(command, worldObj, this, player, getShip());
		} else if(command.equals(SpaceshipCommands.help)){
			SpaceshipCommands.help(player);
		}
		else if(command.startsWith(SpaceshipCommands.energy)) {
			SpaceshipCommands.energy(command, worldObj, this, player, getShip());
		} else if(command.startsWith("debug")){
			SpaceshipCommands.debug(command, this);
		}else {
			terminalMenu.onCommand(command, player, this, this.terminal);
			//display the menu and make the menu commands (also works on Server)

			if(getShip() != null){
				spaceshipMenu.display(command, this.terminal, player);
			}else{
				noSpaceshipMenu.display(command, this.terminal, player);
				//loginMenu.display(command, this.terminal, player);
			}
			//loginMenu.display(command, this.terminal, player);
			//spaceshipMenu.display(command, this.terminal, player);			
		}
		//terminalMenu.onCommand(command, player, this, this.terminal);
		
	}

	public void sendFunctionalMenu(FunctionalMenu menu){
		String data = menu.getData();
		if(data != null){
			this.onCommandForced(menu.getClass().getName() + "#" + data);
		} else {
			this.onCommandForced(menu.getClass().getName() + "#" + "null");
		}
	}

	public void sendFunctionalParamMenu(FunctionalParamMenu menu, String parameters){
		String data = menu.getData();
		if(data != null){
			this.onCommandForced(menu.getClass().getName() +"#" +parameters+ "#" + data);
		} else {
			this.onCommandForced(menu.getClass().getName() +"#" +parameters+ "#" + "null");
		}
	}

	public boolean executeMenu(String menuCommand){
		String[] parts = menuCommand.split("#");
		Class c = null;
		try {
			c = Class.forName(parts[0]);
		} catch (ClassNotFoundException e) {
			return false;
		}
		if(FunctionalMenu.class.isAssignableFrom(c)){
			FunctionalMenu fmenu = null;
			try {
				fmenu = (FunctionalMenu) c.newInstance();
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
			fmenu.activate(terminal, parts[1]);
			return true;
		} else if(FunctionalParamMenu.class.isAssignableFrom(c)) {
			FunctionalParamMenu pmenu = null;
			try {
				pmenu = (FunctionalParamMenu) c.newInstance();
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
			pmenu.activate(parts[1], terminal, parts[2]);
			return true;
		}
		return false;
	}
}
