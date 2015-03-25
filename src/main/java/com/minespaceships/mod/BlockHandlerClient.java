package com.minespaceships.mod;

import io.netty.buffer.ByteBuf;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.minecraft.client.Minecraft;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

import com.minespaceships.mod.events.BlockEvent;
import com.minespaceships.mod.overhead.ChatRegisterEntity;
import com.minespaceships.mod.spaceship.Shipyard;

public class BlockHandlerClient implements IMessageHandler<CommandMessage, IMessage>  {
	
    @Override
    public IMessage onMessage(CommandMessage message, MessageContext ctx) {
    	String[] parts = message.getText().split(",");
    	try{
    		BlockPos pos = BlockPos.fromLong(Long.parseLong(parts[0]));
    		int id = Integer.parseInt(parts[1]);    		
    		boolean placed = Boolean.parseBoolean(parts[2]);
    		if(id == Minecraft.getMinecraft().theWorld.provider.getDimensionId()){
    			if(placed)  BlockEvent.placeBlock(pos, Minecraft.getMinecraft().theWorld);
    			if(!placed) BlockEvent.removeBlock(pos, Minecraft.getMinecraft().theWorld);
    		}
    		
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
        return null;
    }
}
