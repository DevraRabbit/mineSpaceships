package com.minespaceships.mod;

import com.minespaceships.mod.events.PlayerTracker;
import com.minespaceships.mod.menu.MenuDisplay;
import com.minespaceships.mod.menu.NoSpaceshipEntityMenu;
import com.minespaceships.mod.menu.SpaceshipMenu;
import com.minespaceships.mod.overhead.ChatRegisterEntity;
import com.minespaceships.mod.overhead.CustomGuiChat;
import com.minespaceships.mod.overhead.IMenuInterface;
import com.minespaceships.mod.spaceship.Shipyard;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;

public class ClientProxy extends CommonProxy {
	@Override
    public void preInit(FMLPreInitializationEvent e) {
        super.preInit(e);
    	MineSpaceships.network.registerMessage(CommandMessage.HandlerClient.class, CommandMessage.class, 0, Side.CLIENT);
    	MineSpaceships.blockChangeEvents.registerMessage(BlockHandlerClient.class, CommandMessage.class, 0, Side.CLIENT);
    }

    @Override
    public void init(FMLInitializationEvent e) {
        super.init(e);
    }

    @Override
    public void postInit(FMLPostInitializationEvent e) {
        super.postInit(e);
    }

	@Override
	public IMenuInterface makeTerminal(EntityPlayer player, ChatRegisterEntity entity) {
		CustomGuiChat gui = new CustomGuiChat(player, entity);
		super.setupTerminal(player, entity, gui);
		
		//open our console. 
		if(Minecraft.getMinecraft().thePlayer.equals(player)){Minecraft.getMinecraft().displayGuiScreen(gui);}

		if(Shipyard.getShipyard(gui.getChatRegisterEntity().getWorld()).getShip(gui.getChatRegisterEntity().getPos(), gui.getChatRegisterEntity().getWorld()) == null){
			entity.getNoSpaceshipMenu().displayMain(NoSpaceshipEntityMenu.getRootMenu(), gui);
		}else{
			//Print out the menu in the console.
			entity.getSpaceshipMenu().displayMain(SpaceshipMenu.getRootMenu(), gui);
		}
		return gui;
	}
}
