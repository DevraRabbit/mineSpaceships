package com.minespaceships.mod;

import net.minecraft.client.Minecraft;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
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

    public static class Handler implements IMessageHandler<CommandMessage, IMessage> {
        
        @Override
        public IMessage onMessage(CommandMessage message, MessageContext ctx) {
            System.out.println(String.format("Received %s from %s", message.text, ctx.getServerHandler().playerEntity.getDisplayName()));
            String[] parts = message.text.split(",");
            BlockPos pos = null;
            World world = null;
            try{
            	long posLong = Long.parseLong(parts[0]);
            	pos = BlockPos.fromLong(posLong);
            	int dimension = Integer.parseInt(parts[1]);
            	world = MinecraftServer.getServer().worldServerForDimension(dimension);
            } catch (Exception e) {
            	return null;
            }
            return null; // no response in this case
        }
    }
}