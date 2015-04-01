package com.minespaceships.mod;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.minecraft.client.Minecraft;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import com.minespaceships.mod.overhead.ChatRegisterEntity;
import com.minespaceships.mod.spaceship.Shipyard;
import com.minespaceships.mod.spaceship.Spaceship;

/**
 * @author ovae.
 * @version 20150331.
 */
public class RemoveHandler implements IMessageHandler<CommandMessage, IMessage>{

	@Override
	public IMessage onMessage(CommandMessage message, MessageContext ctx) {
		// TODO Auto-generated method stub
		Pattern pattern = Pattern.compile("([-+]?[0-9]+),([-+]?[0-9]+),([-+]?[0-9]+)");
		Matcher matcher = pattern.matcher(message.getText());

		if(matcher.matches()){
			long msgLong = Long.parseLong(matcher.group(1));
			BlockPos pos = BlockPos.fromLong(msgLong);
			long msgLong2 = Long.parseLong(matcher.group(2));
			BlockPos oldPos = BlockPos.fromLong(msgLong2);
			int dimension = Integer.parseInt(matcher.group(3));

			if(Minecraft.getMinecraft().theWorld.provider.getDimensionId() == dimension){
				World world = Minecraft.getMinecraft().theWorld;
				Spaceship ship = Shipyard.getShipyard(world).getShipByBlockMapOrigin(pos, world);
				if(ship != null){
					ship.canRemoveBlocks();
					ship.removeOldSpaceship();
				} else {
					ship = Shipyard.getShipyard(world).getShipByBlockMapOrigin(oldPos, world);
					ship.canBeRemoved();
				}
			}
		}
		return null; //For no response
	}

}
