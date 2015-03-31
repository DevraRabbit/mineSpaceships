package com.minespaceships.mod.spaceship;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.minecraft.client.Minecraft;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import com.minespaceships.mod.CommandMessage;
import com.minespaceships.mod.overhead.ChatRegisterEntity;

/**
 * @author ovae.
 * @version 20150331.
 */
public class RemoveHandler implements IMessageHandler<CommandMessage, IMessage>{

	@Override
	public IMessage onMessage(CommandMessage message, MessageContext ctx) {
		// TODO Auto-generated method stub
		Pattern pattern = Pattern.compile("([-+]?[0-9]+),([-+]?[0-9]+),(.*),(.*)");
		Matcher matcher = pattern.matcher(message.getText());

		if(matcher.matches()){
			long msgLong = Long.parseLong(matcher.group(1));
			BlockPos pos = BlockPos.fromLong(msgLong);
			int dimension = Integer.parseInt(matcher.group(2));

			if(Minecraft.getMinecraft().theWorld.provider.getDimensionId() == dimension){
				ChatRegisterEntity entity = ((ChatRegisterEntity)Minecraft.getMinecraft().theWorld.getTileEntity(pos));
				if(entity != null){
					World world = Minecraft.getMinecraft().theWorld;
					Spaceship ship = Shipyard.getShipyard(world).getShipByBlockMapOrigin(entity.getPos(), world);
					ship.removeOldSpaceship();
				}
			}
		}
		return null; //For no response
	}

}
