package com.minespaceships.mod;

import java.util.List;
import java.util.UUID;
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
import net.minecraftforge.fml.relauncher.SideOnly;
import io.netty.buffer.*;

public class CommandMessage implements IMessage {
    
    private String text;

    public CommandMessage() { }

    public CommandMessage(String text) {
        this.setText(text);
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        setText(ByteBufUtils.readUTF8String(buf));
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeUTF8String(buf, getText());
    }

    public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	@SideOnly(Side.SERVER)
	public static class HandlerServer implements IMessageHandler<CommandMessage, IMessage> {
        
        @Override
        public IMessage onMessage(CommandMessage message, MessageContext ctx) {
        	Side side = FMLCommonHandler.instance().getEffectiveSide();
            System.out.println(String.format("Received %s from %s", message.getText(), ctx.getServerHandler().playerEntity.getDisplayName()));
            
    		Pattern poffset = Pattern.compile("([-+]?[0-9]+),([-+]?[0-9]+),(.*),(.*)");
    		Matcher moffset = poffset.matcher(message.getText());
            
            BlockPos pos = null;
            World world = null;
            UUID uuid = null;
            String command = null;
            
            if(moffset.matches()) {
	            try{
	            	long posLong = Long.parseLong(moffset.group(1));
	            	pos = BlockPos.fromLong(posLong);
	            	
	            	int dimension = Integer.parseInt(moffset.group(2));
	            	world = MinecraftServer.getServer().worldServerForDimension(dimension);
	            	
	            	String uuidString = moffset.group(3);
	            	uuid = UUID.fromString(uuidString);
	            	
	            	command = moffset.group(4);
	            	//send it first so not everyone has to wait for the Server
	            	List players = ((WorldServer)world).playerEntities;
	            	for(Object o : players){
	            		if(o instanceof EntityPlayerMP){
	    	            	MineSpaceships.network.sendTo(message, (EntityPlayerMP)o);
	            		}
	            	}	
	            	
	            	ChatRegisterEntity ent = ((ChatRegisterEntity)world.getTileEntity(pos));
	            	if(ent != null){
            			ent.executeCommand(command, ctx.getServerHandler().playerEntity, true);
	            	} else {
	            		int i = 0;
	            	}	            	
	            } catch (Exception e) {
	            	e.printStackTrace();
	            	return null;
	            }
            }
            return null; // no response in this case
        }
    }  
	@SideOnly(Side.CLIENT)
    public static class HandlerClient implements IMessageHandler<CommandMessage, IMessage> {
        
        @Override
        public IMessage onMessage(CommandMessage message, MessageContext ctx) {
        	Side side = FMLCommonHandler.instance().getEffectiveSide();
            System.out.println(String.format("Received %s from %s", message.getText(), Minecraft.getMinecraft().thePlayer.getDisplayName()));
            
    		Pattern poffset = Pattern.compile("([-+]?[0-9]+),([-+]?[0-9]+),(.*),(.*)");
    		Matcher moffset = poffset.matcher(message.getText());
            
            BlockPos pos = null;
            World world = null;
            UUID uuid = null;
            String command = null;
            
            Minecraft.getMinecraft().thePlayer.sendChatMessage("recieved: "+message.getText());
            
            if(moffset.matches()) {
	            try{
	            	long posLong = Long.parseLong(moffset.group(1));
	            	pos = BlockPos.fromLong(posLong);
	            	
	            	int dimension = Integer.parseInt(moffset.group(2));
	            	if(Minecraft.getMinecraft().theWorld.provider.getDimensionId() == dimension){
	            		String uuidString = moffset.group(3);
		            	uuid = UUID.fromString(uuidString);
		            	
		            	command = moffset.group(4);
		            	
		            	ChatRegisterEntity ent = ((ChatRegisterEntity)Minecraft.getMinecraft().theWorld.getTileEntity(pos));
		            	if(ent != null){
		            		EntityPlayer p = Minecraft.getMinecraft().thePlayer;
		            		if(p.getUniqueID().equals(uuid)){
		            			ent.executeCommand(command, Minecraft.getMinecraft().thePlayer, true);
		            		} else {
		            			ent.executeCommand(command, null, true);
		            		}
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