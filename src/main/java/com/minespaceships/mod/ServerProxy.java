package com.minespaceships.mod;

import com.minespaceships.mod.overhead.ChatRegisterEntity;
import com.minespaceships.mod.overhead.CustomGuiChat;
import com.minespaceships.mod.overhead.IMenuInterface;
import com.minespaceships.mod.overhead.ServerMenuConnection;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;

public class ServerProxy extends CommonProxy {
	@Override
    public void preInit(FMLPreInitializationEvent e) {
        super.preInit(e);
    	MineSpaceships.network.registerMessage(CommandMessage.HandlerServer.class, CommandMessage.class, 0, Side.SERVER);
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
	public IMenuInterface makeTerminal(EntityPlayer player,	ChatRegisterEntity entity) {
		ServerMenuConnection terminal = new ServerMenuConnection(player, entity);
		super.setupTerminal(player, entity, terminal);
		return terminal;
	}
}
