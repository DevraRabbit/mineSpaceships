package com.minespaceships.mod;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.minespaceships.mod.overhead.ChatRegisterEntity;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.*;
import net.minecraftforge.fml.relauncher.Side;
import io.netty.buffer.*;

public class CommandMessage implements IMessage {
    
    private String text;

    public CommandMessage() { }

    public CommandMessage(String text) {
        this.text = text;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        text = ByteBufUtils.readUTF8String(buf); // this class is very useful in general for writing more complex objects
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeUTF8String(buf, text);
    }

    public static class HandlerServer implements IMessageHandler<CommandMessage, IMessage> {
        
        @Override
        public IMessage onMessage(CommandMessage message, MessageContext ctx) {
        	Side side = FMLCommonHandler.instance().getEffectiveSide();
            System.out.println(String.format("Received %s from %s", message.text, ctx.getServerHandler().playerEntity.getDisplayName()));
            
    		Pattern poffset = Pattern.compile("([-+]?[0-9]+),([-+]?[0-9]+),(.*)");
    		Matcher moffset = poffset.matcher(message.text);
            
            BlockPos pos = null;
            World world = null;
            String command = null;
            
            if(moffset.matches()) {
	            try{
	            	long posLong = Long.parseLong(moffset.group(1));
	            	pos = BlockPos.fromLong(posLong);
	            	
	            	int dimension = Integer.parseInt(moffset.group(2));
	            	world = MinecraftServer.getServer().worldServerForDimension(dimension);
	            	
	            	command = moffset.group(3);
	            	
	            	ChatRegisterEntity ent = ((ChatRegisterEntity)world.getTileEntity(pos));
	            	if(ent != null){
            			ent.executeCommand(command, ctx.getServerHandler().playerEntity);
	            	} else {
	            		int i = 0;
	            	}
	            	List players = ((WorldServer)world).playerEntities;
	            	for(Object o : players){
	            		if(o instanceof EntityPlayerMP){
	    	            	MineSpaceships.network.sendTo(message, (EntityPlayerMP)o);
	            		}
	            	}	
	            } catch (Exception e) {
	            	e.printStackTrace();
	            	return null;
	            }
            }
            return null; // no response in this case
        }
    }  
    
    public static class HandlerClient implements IMessageHandler<CommandMessage, IMessage> {
        
        @Override
        public IMessage onMessage(CommandMessage message, MessageContext ctx) {
        	Side side = FMLCommonHandler.instance().getEffectiveSide();
            System.out.println(String.format("Received %s from %s", message.text, Minecraft.getMinecraft().thePlayer.getDisplayName()));
            
    		Pattern poffset = Pattern.compile("([-+]?[0-9]+),([-+]?[0-9]+),(.*)");
    		Matcher moffset = poffset.matcher(message.text);
            
            BlockPos pos = null;
            World world = null;
            String command = null;
            
            if(moffset.matches()) {
	            try{
	            	long posLong = Long.parseLong(moffset.group(1));
	            	pos = BlockPos.fromLong(posLong);
	            	
	            	int dimension = Integer.parseInt(moffset.group(2));
	            	if(Minecraft.getMinecraft().theWorld.provider.getDimensionId() == dimension){		            	
		            	command = moffset.group(3);
		            	
		            	ChatRegisterEntity ent = ((ChatRegisterEntity)Minecraft.getMinecraft().theWorld.getTileEntity(pos));
		            	if(ent != null){
	            			ent.executeCommand(command, Minecraft.getMinecraft().thePlayer);
		            	} else {
		            		int i = 0;
		            	}
	            	}
	            } catch (Exception e) {
	            	e.printStackTrace();
	            	return null;
	            }
            }
            return null; // no response in this case
        }
    }    
}