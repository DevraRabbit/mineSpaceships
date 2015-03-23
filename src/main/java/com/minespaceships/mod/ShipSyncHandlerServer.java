package com.minespaceships.mod;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

import com.minespaceships.mod.spaceship.Shipyard;

public class ShipSyncHandlerServer implements IMessageHandler<CommandMessage, IMessage>  {
	
    @Override
    public IMessage onMessage(CommandMessage message, MessageContext ctx) {
//    	Side side = FMLCommonHandler.instance().getEffectiveSide();
//    	int shipCount = Integer.parseInt(message.getText());
//    	if(shipCount != Shipyard.getShipyard().getShipCount()){
//	    	World world = ctx.getServerHandler().playerEntity.worldObj;
//	    	String ships = Shipyard.getShipyard().loadShips(world);
//			MineSpaceships.spaceshipNetwork.sendTo(new CommandMessage(ships), ctx.getServerHandler().playerEntity);
//    	}
    	
        return null;
    }
}
